# SoccerNow: Gestão de Jogos de Futsal

## Relatório de Projeto - Equipa 33
 - Bruno Faustino fc59784 C1
 - Guilherme Dias fc57163 C2
 - João Rebelo    fc60522 C3

<a href="http://localhost:8080/swagger-ui/index.html#">http://localhost:8080/swagger-ui/index.html# </a>

## Contexto
Apresentamos este relatório para efeitos de análise do raciocínio por detrás do desenvolvimento da aplicação SoccerNow para gestão de jogos e equipas de futsal.
Pretende-se que esta aplicação cumpra os requisitos apresentados no enunciado do projeto e também permita a sua futura evolução (planeando para a mudança).
Recomenda-se que a leitura deste relatório seja feita tendo em mente os documentos apresentados em conjunto, nomeadamente o modelo de domínio e o diagrama de classes.

## Arquitetura em Camadas
  - Controllers -> Mapeiam os endpoints REST, recebidos da interface Swagger, necessários para satisfazer os casos de uso. Sabem o handler que satisfará melhor cada pedido.
  - Handlers -> Efetuam a validação do input e usam um objeto Mapper para traduzir entre DTO e Entidade comunicando entre o controller e os repositórios.
  - Repositories -> Herdam os métodos de um JpaRepository (e mais alguns obtidos por query derivation) para aceder à Base de Dados.
  - Entities -> Contêm os Models com a lógica da aplicação. São mapeados pelo Hibernate para a Base de Dados.
  - DTOs -> Encapsulam os atributos necessários das Entidades para troca de informação entre a camada de apresentação e da lógica de negócio.
  - Mappers -> Responsáveis pelo mapeamento entre uma dada Entidade e um DTO, usados pelos handlers.

## Decisões de Mapeamento

### C1 (Utilizadores)
  - Utilizador é uma classe abstrata que permite especialização em Jogador ou Arbitro. O Arbitro tem o seu certificado embebido, o que implica que, no futuro, a sua evolução levaria a um enorme desperdício de espaço na BD se fosse utilizado Single Table, pois contamos que existe um número muito maior de Jogadores do que de Arbitros. Dado que não temos operações que precisem de tratar com os Utilizadores (só com as especializações), então seria mais eficiente ter toda a informação sobre o Jogador e toda a informação sobre o Arbitro nas suas respetivas tabelas, logo optámos por utilizar Table Per Class em vez de Joined Table;

  - Para controlar a concorrência no acesso aos dados, Utilizador tem um atributo Long anotado com @Version, que é verificado e atualizado automaticamente pela BD durante operações de escrita. Ora se tivermos por exemplo, um GET que retorna todos os jogadores de uma equipa numa transação (e trabalha sobre eles mais tarde) enquanto temos noutra transação um UPDATE de algum jogador dessa equipa, a primeira transação estaria a utilizar valores desatualizados, logo não devia conseguir realizar o commit. Daí termos os métodos de leitura em JogadorRepository e ArbitroRepository anotados com @Lock de tipo OPTIMISTIC_FORCE_INCREMENT para a BD incrementar também a versão com a leitura e assim a primeira transação lançar uma exceção e executar o rollback quando tenta realizar o commit;

  - Os métodos do JogadorHandler e do ArbitroHandler estão anotados com @Transactional para serem considerados como um única transação pelo JPA, evitando conflitos internos de concorrência;

  - A posição preferida de um Jogador é representada por um enum. Como em teoria não se espera que os tipos possíveis de posições de futsal se alterem (isso implicaria as regras do desporto mudarem), então não há problema em guardar o enum como inteiro em vez de string, e é mais eficiente, logo a posição está anotada com @Enumerated(EnumType.ORDINAL);

  - Como referido, o certificado do Arbitro está embedded, pois é uma entidade fraca, só tem significado dentro da entidade a que está associada. Se um Arbitro é removido, então o seu certificado também deveria ser removido.

### C2 (Equipas)
  - Equipa contém o seu Histórico de Jogos fazendo a ponte entre a tabela das Equipas e dos Jogos com um @ManyToMany (um jogo tem duas equipas associadas). Como uma equipa não necessita de ter participado em jogos, este atributo pode ser nulo.

  - Equipa também tem uma lista de jogadores que representa o seu plantel. Sendo que os jogadores podem estar em várias equipas, segue-se a mesma lógica @ManyToMany entre Equipa e Jogador como anteriormente. Uma Equipa pode também não ter nenhum Jogador associado à mesma.

  - **Conquista** tem uma relação _obrigatória_ (nullable = false) tanto com Equipa como com Campeonato. Trata-se em ambos os casos de uma relação @ManyToOne, uma equipa pode ter várias conquistas, tal como um campeonato (tem pelo menos três conquistas (pódio)).

### C3 (Jogos)
  - Estatistica: A filosofia que tomámos no mapeamento dos Jogos e das Estatísticas foi de minimizar o espaço ocupado na base de dados. Por isso, evitámos ter informação repetida em várias tabelas, o que não só minimiza o espaço ocupado, como reduz o número de comunicações com a base de dados para fazer as operações mais simples de adicionar, alterar e remover entidades. Após refletir sobre o modelo de domínio tendo isto em mente, concluímos por isso que o indicado seria que a entidade de Estatísticas (e todos os seus derivados) fosse efémera, gerada apenas mediante pedidos (queries) específicos. Desse modo, a única coisa que teria persistência seriam golos e cartões (e possivelmente outros eventos de jogo futuros, como faltas ou fora-de-jogo por exemplo). Para gerar a estatística de um jogador (por exemplo), questionam-se os repositories de golos e cartões sobre todos os golos e cartoes que envolvem esse jogador.

  - Em Golo, Cartao, e outros EventosDeJogo, para que este tipo de persistência seja eficiente, usámos a annotation @OneToOne e @JoinColumn na maioria dos atributos de Golo e Cartão, para que ficasse guardada apenas a foreign key relativa ao marcador ou ao árbitro, evitando assim a criação de uma tabela auxiliar, e evitando a duplicação de dados (caso ficasse Embedded por exemplo). Deste modo, alterações às entidades de Jogador ou Arbitro não têm de ser custosamente refletidas nos cartões e golos que os referem.

  - Relativamente à criação de Golos e Cartões, é natural que a sua inserção na base de dados possa ser feita apenas no contexto de um jogo (afinal de contas, não faria qualquer sentido para um jogador marcar um golo sem ter sido durante um jogo...). Por isso, evitámos criar um controller dedicado aos EventosDeJogo. Estes devem ser criados recorrendo ao JogoController, que é o único que oferece endpoints para usar o EstatisticasHandler de maneira a guardar novos golos e cartoes.

  - Jogo tambem faz uso de @OneToOne e @JoinColumn para incluir como parametro apenas o necessario. A maior dificuldade prendeu-se com a necessidade de ter 2 entidades embedded com o mesmo tipo, nomeadamente 2 seleções (sendo Selecao o conjunto de 5 jogadores de uma equipa que é chamado a jogo). Esta entidade é demasiado volátil/efémera para merecer persistência por si só em base de dados, pelo que se optou por simplesmente fazer um @Embeddable que guardasse a equipa e os jogadores participantes. Tendo em conta que cada Seleçao pode ter apenas 5 membros, um capitao e a equipa respetiva, o objetivo era ter na tabela de Jogo (7*2)=14 colunas, cada uma contendo a ForeignKey necessária. A solução encontrada para isto nao foi ideal, mas tivemos de criar uma classe SelecaoDois, em tudo semelhante à primeira, mas com nomes de colunas dedicados usando o atributo name= da anotação de @JoinColumn responsavel por manter a foreign key.

## Guarantias do Sistema sobre a Lógica de Negócio e Decisões Tomadas

### C1 (Utilizadores)
  - Utilizadores diferentes nunca têm o mesmo nif, mesmo entre Jogador e Arbitro;

  - O nome dos Utilizadores tem de estar preenchido com algum caracter;

  - A posição preferida do Jogador só pode ser uma de 5 tipos possíveis;

  - Temos controllers, handlers, repositories e endpoints específicos para Jogador e para Arbitro porque estas entidades diferem logicamente uma da outra por desempenharem papéis diferentes e, embora a implementação dos endpoints para ambos seja bastante idêntica para estes casos de uso, podemos ter no futuro vários endpoints que são específicos só de uma entidade, logo, sem esta divisão, ficariam todos acoplados nas mesmas classes;

  - Os golos e cartões são guardados pelos seus próprios repositórios num handler dedicado para estatísticas para melhor divisão de responsabilidades e é o Jogo que é responsável por adicionar os golos e cartões decorrentes de um jogo, que estão associados com o Jogador e/ou Arbitro respetivo;

  - A remoção de um Jogador também remove todos os seus golos e cartões da BD, mas nada acontece na remoção de um árbitro, pois os golos e cartões são entidades que pertencem logicamente a um Jogador;

  - Como não parece ser possível ter uma entidade embedded a null, a entidade Certificado tem um atributo booleano que distingue os Arbitros com e sem Certificado.

### C2 (Equipas)
  - Todas as equipas têm funções de validação do EquipaHandler que falam diretamente com EquipaController têm validadores associados.  Estes validadores são facilmente extensíveis para futuras restrições que possam a vir ser necessárias.

  - Garante-se que uma Equipa não possa ser removida se tem no seu Histórico-de-Jogos jogos com Data depois do dia em que se está a tentar apagar a mesma.

  - Garante-se que numa atualização (Put Equipa) todos os jogos pré-existentes que são enviados pelo Cliente através do Dto contenham tanto os jogos existentes assim como os jogos a adicionar. No que toca à remoção de jogos, apenas é permitida remoção de jogos agendados.

  - Para **Conquista** decidiu-se que apenas Conquista tem relação direta tanto com Campeonato assim como para com Equipa e _não vice versa_. Ainda assim, não há endpoints diretos para a mesma, é preciso passar primeiro por EquipaController ou pêlo CampeonatoController. Esta decisão advém do facto de se se verificar desnecessário um endpoint específico para conquista, para além de que a ideia de uma conquista está fortemente ligada tanto a uma equipa específica e ao campeonato no qual essa equipa ganhou a conquista. Ainda assim, esta decisão é facilmente revertível. Neste momento quem chama o ConquistaHandler é tanto o EquipaController e futuramente quando implementado, CampeonatoController.

  - Conquista não foi totalmente implementada. São criadas tabelas, a sua entidade e handler existem, porém o handler não foi implementado e não se faz nenhuma operação com repositórios ou mappers.

### C3 (Jogos)
  - Para que o resultado e o vencedor de um jogo possa ser conhecido sem consultas demasiado complexas, optou-se por criar um atributo Placar para facilitar essa consulta.

  - Garante-se que atualizações de jogo que envolvam alterar as suas estatísticas (aka. golos e cartoes) apenas podem ser feitas quando o estado de um jogo passa de qualquer outro estado para "TERMINADO". Pretende-se desse modo simular que apenas é possível Registar o resultado de um jogo na ocasião em que este termina.

  - Jogo não está completamente implementado. Tendo em conta a complexidade da classe, seriam necessários multiplos endpoints para convenientemente consultar e configurar cada um dos seus parâmetros. No seu estado atual, as consultas são possíveis, mas morosas. A arquitetura implementada permite a expansão necessária para incorporar estes endpoints, com alterações apenas nos handlers e controllers (assumindo também dtos mais específicos).

  - o desacoplamento de Estatísticas permite a flexibilidade desejada. Apesar disso, na sua implementação corrente, Estatísticas apresenta-se praticamente como um Dto, apesar de servir algumas das classes da camada de negócio.

  - São feitas poucas verificações ao corpo de um pedido que pretenda fazer POST ou PUT de um jogo.

  - Efetivamente, o metodo de finalizar Jogo (registar um resultado) nao está funcional.

## Funcionalidades Esperadas

### Quais as equipas que possuem menos de 5 jogadores?
  - Executar GET /api/equipas para receber todas as equipas da BD;
  - Filtrar as equipas recebidas em que o número de elementos da sua lista jogadores é menor que 5.

### Em média, quantos golos os jogadores com o nome “X” marcam por jogo?
  - Executar GET /api/jogadores para receber todos os jogadores da BD;
  - Filtrar os jogadores pelo nome para termos apenas os que têm nome "X";
  - Sejam contador, golosMarcados e jogos 3 inteiros e jogosUnicos um Set de inteiros;
  - Para cada jogador recebido, percorrer a lista de golos em estatisticas;
  - Para cada um desses golos, incrementar golosMarcados e adicionar o id do jogo a jogosUnicos;
  - No final de um jogador, somar golosMarcados/jogosUnicos.size() a contador e reinicializar golosMarcados e jogosUnicos;
  - Após percorrer todos os jogadores, a média pretendida é contador/número de jogadores recebidos.

### Quais as equipas que recebem mais cartões (amarelos e vermelhos)?
  - Executar GET /api/jogos para obter todos os jogos da BD;
  - Seja equipasCartoes um mapa de chaves id de Equipa e valores correspondentes ao número de cartões que obtiveram;
  - Para cada jogo recebido, percorrer a lista de cartões em stats;
  - Para cada cartão, colocar o id da equipa em equipasCartoes a 1 se não existir ou incrementar o valor já existiente se já existir;
  - Após terminar, ordenar equipasCartoes por ordem decrescente do valor e escolher os primeiros ids de equipa;
  - Se for necessário mais informação sobre as equipas, executar GET /api/equipas/{id}, onde id é um dos ids de equipa obtidos.

### Quais as equipas com o maior número de vitórias?
  - Executar GET /api/jogos para obter todos os jogos da BD;
  - Filtrar os jogos para obter apenas aqueles que estao terminados (jogoDto.getEstadoDeJogo()).
  - Seja equipasVitorias um mapa de chaves id de Equipa e valores correspondentes ao número de vitórias que obtiveram;
  - Para cada jogo recebido, procurar o valor correspondente à chave da equipaVencedora e incrementar em +1.
  - Após terminar, ordenar equipasVitorias por ordem decrescente do valor e escolher os primeiros ids de equipa;
  - Se for necessário mais informação sobre as equipas, executar GET /api/equipas/{id}, onde id é um dos ids de equipa obtidos.

### Qual o árbitro que oficiou o maior número de jogos?
  - Executar GET /api/jogos para receber todos os jogos da BD;
  - Seja arbJogos um mapa de chaves Arbitro e valores correspondentes ao número de jogos que oficiaram;
  - Para cada jogo, buscar o primeiro elemento da lista equipaDeArbitros (que corresponde ao árbitro que oficiou) e colocar em arbJogos com valor a 1 se não existir ou colocar em arbJogos com o valor já lá existente incrementado caso contrário;
  - Obter o Arbitro com maior valor em arbJogos;

### Quais os jogadores que receberam mais cartões vermelhos?
  - Executar GET /api/jogadores para receber todos os jogadores da BD;
  - Para cada jogador, filtrar a lista de cartões (dentro das suas estatisticas) para termos apenas os cartões com Cor vermelha;
  - Implementar um Comparator que considera que um jogador é maior que outro se, dentro das estatisticas, o número de cartões (já filtrados) é maior;
  - Ordenar todos os jogadores de acordo com esse Comparator e escolher os maiores.

## Dificuldades e Limitações do Projeto
  - Não conseguimos implementar o controller para iniciar a BD com alguns exemplos a tempo da data de entrega o que dificultou o nosso debug do projeto;
  - Devido a esta dificuldade, não houve tempo para fazer debug do endpoint de finalizar jogo, pelo que essa parte ficou incompleta.
  - Existem vários detalhes onde gostaríamos de ter tido mais tempo para refletir como o estado de outras entidades após o delete de uma dada entidade, a implementação de vários testes e a gestão da concorrência.
