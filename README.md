# SoccerNow: Gestão de Jogos de Futsal

## Relatório de Projeto - Equipa 33
 - Bruno Faustino fc59784 C1
 - Guilherme Dias fc57163 C2
 - João Rebelo    fc60522 C3

<a href="http://localhost:8080/swagger-ui/index.html#">http://localhost:8080/swagger-ui/index.html# </a>

## Contexto
Apresentamos este relatório para efeitos de análise do raciocínio por detrás do desenvolvimento da aplicação SoccerNow para gestao de jogos e equipas de futsal.
Pretende-se que esta aplicação cumpra os requisitos apresentados no setup do projeto (enunciado) e também permita a sua futura evolução (planeando para a mudança).
Recomenda-se que a leitura deste relatório seja feita tendo em mente os documentos apresentados em conjunto, nomeadamente o modelo de domínio e o diagrama de classes.

## Arquitetura em Camadas
  - controllers -> mapeiam os endpoints REST, recebidos do Swagger, necessários para satisfazer os casos de uso, para o handler adequado para tratar dele;
  - handlers -> realizam a verificação do input e usam um objeto Mapper para traduzir entre DTO e entidade de forma a comunicar entre o controller e o repositório;
  - repositories -> herdam os métodos de um JpaRepository (e mais alguns obtidos por query derivation) para aceder à BD;
  - entities -> contêm os nossos models com a lógica da aplicação, que são mapeados
  pelo Hibernate para a BD;
  - dtos -> encapsulam os atributos necessários das entidades para troca de informação entre a camada de apresentação e da lógica de negócio;
  - mappers -> responsáveis pelo mapeamento entre uma dada entidade e um DTO, usados pelos handlers.

## Decisões de Mapeamento

### C1 (Utilizadores)
  - Utilizador é uma classe abstrata que permite especialização em Jogador ou Arbitro. O Arbitro tem o seu certificado embebido, o que implica que, no futuro, a sua evolução levaria a um enorme desperdício de espaço na BD se fosse utilizado Single Table, pois contamos que existe um número muito maior de Jogadores do que de Arbitros. Dado que não temos operações que precisem de tratar com os Utilizadores (só com as especializações), então seria mais eficiente ter toda a informação sobre o Jogador e toda a informação sobre o Arbitro nas suas respetivas tabelas, logo optámos por utilizar Table Per Class em vez de Joined Table;

  - Para controlar a concorrência no acesso aos dados, Utilizador tem um atributo Long anotado com @Version, que é verificado e atualizado automaticamente pela BD durante operações de escrita. Ora se tivermos por exemplo, um GET que retorna todos os jogadores de uma equipa numa transação (e trabalha sobre eles mais tarde) enquanto temos noutra transação um UPDATE de algum jogador dessa equipa, a primeira transação estaria a utilizar valores desatualizados, logo não devia conseguir realizar o commit. Daí termos os métodos de leitura em JogadorRepository e ArbitroRepository anotados com @Lock de tipo OPTIMISTIC_FORCE_INCREMENT para a BD incrementar também a versão com a leitura e assim a primeira transação lançar uma exceção e executar o rollback quando tenta realizar o commit;

  - Os métodos do JogadorHandler e do ArbitroHandler estão anotados com @Transactional para serem considerados como um única transação pelo JPA, evitando conflitos internos de concorrência;

  - A posição preferida de um Jogador é representada por um enum. Como em teoria não se espera que os tipos possíveis de posições de futsal se alterem (isso implicaria as regras do desporto mudarem), então não há problema em guardar o enum como inteiro em vez de string, e é mais eficiente, logo a posição está anotada com @Enumerated(EnumType.ORDINAL);

  - Como referido, o certificado do Arbitro está embedded, pois é uma entidade fraca, só tem significado dentro da entidade a que está associada. Se um Arbitro é removido, então o seu certificado também deveria ser removido.

### C2 (Equipas)
  - TODO

### C3 (Jogos)
  - TODO

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
  - TODO

### C3 (Jogos)
  - TODO

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
  - Seja equipasVitorias um mapa de chaves id de Equipa e valores correspondentes ao número de vitórias que obtiveram;
  - Para cada jogo recebido, percorrer a lista de golos em stats;
  - Contar o número de golos em que equipa é um dado id e contar em que equipa é o outro id do jogo;
  - Colocar o id da equipa que teve a maior contagem em equipasVitorias a 1 se não existir ou incrementar o valor já existiente se já existir;
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
