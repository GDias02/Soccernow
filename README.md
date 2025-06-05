# SoccerNow: Gestão de Jogos de Futsal

## Relatório de Projeto - Equipa 33
 - Bruno Faustino fc59784 C1
 - Guilherme Dias fc57163 C2
 - João Rebelo    fc60522 C3

<a href="http://localhost:8080/swagger-ui/index.html#">http://localhost:8080/swagger-ui/index.html# </a>
<a href="http://localhost:8080/">http://localhost:8080/ </a>

## Contexto
Apresentamos este relatório para efeitos de análise do raciocínio por detrás do desenvolvimento da aplicação SoccerNow para gestão de jogos e equipas de futsal.
Pretende-se que esta aplicação cumpra os requisitos apresentados no enunciado do projeto e também permita a sua futura evolução (planeando para a mudança).
Recomenda-se que a leitura deste relatório seja feita tendo em mente os documentos apresentados na primeira fase de desenvolvimento, nomeadamente o modelo de domínio e o diagrama de classes. Apesar disso, é de notar que existem algumas diferenças arquiteturais importantes entre esses documentos e o estado final de desenvolvimento - estas serão apontadas quando for apropriado.

## Arquitetura em Camadas

  ### SoccerNow (Backend)
  - Controllers -> Mapeiam os endpoints REST, necessários para satisfazer os casos de uso e algumas funcionalidades extra. Sabem o handler que satisfará melhor cada pedido.
  - Handlers -> Efetuam a validação do input e usam um objeto Mapper para traduzir entre DTO e Entidade comunicando entre o controller e os repositórios.
  - Repositories -> Herdam os métodos de um JpaRepository, alguns obtidos por query derivation, e alguns customizados para aceder à Base de Dados.
  - Entities -> Contêm os Models com a lógica da aplicação. São mapeados pelo Hibernate para a Base de Dados.
  - DTOs -> Encapsulam os atributos necessários das Entidades para troca de informação entre a camada de apresentação e da lógica de negócio. Por isso estão presentes em duplicado, na aplicação em JavaFX, e no backend soccernow.
  - Mappers -> Responsáveis pelo mapeamento entre uma dada Entidade e um DTO, usados pelos handlers.

## Decisões de Arquitetura

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
  - Estatistica: São persistidos apenas os Golos e os Cartões, que são registados pelo Controlador de Jogos. Para gerar a estatística de um jogador (por exemplo), questionam-se os repositories de golos e cartões sobre todos os golos e cartoes que envolvem esse jogador.

  - Em Golo, Cartao, e outros EventosDeJogo, para que este tipo de persistência seja eficiente, usámos a annotation @OneToOne e @JoinColumn na maioria dos atributos de Golo e Cartão, para que ficasse guardada apenas a foreign key relativa ao marcador ou ao árbitro, evitando assim a criação de uma tabela auxiliar, e evitando a duplicação de dados (caso ficasse Embedded por exemplo). Deste modo, alterações às entidades de Jogador ou Arbitro não têm de ser custosamente refletidas nos cartões e golos que os referem.

  - Relativamente à criação de Golos e Cartões, é natural que a sua inserção na base de dados possa ser feita apenas no contexto de um jogo (afinal de contas, não faria qualquer sentido para um jogador marcar um golo sem ter sido durante um jogo...). Por isso, evitámos criar um controller dedicado aos EventosDeJogo. Estes devem ser criados recorrendo ao JogoController, que é o único que oferece endpoints para usar o EstatisticasHandler de maneira a guardar novos golos e cartoes.

  - Para superar as dificuldades da primeira fase, optou-se por criar uma tabela intermédia entre Jogo e Jogadores (a tabela de Selecao) assim como entre Jogo e Arbitros (lista_arbitros), usando @OneToMany e @ManyToMany respetivamente.

## Guarantias Adicionais (face à primeira fase) sobre a Lógica de Negócio

### C1 (Utilizadores)


### C2 (Equipas)


### C3 (Jogos)
  - Garante-se que atualizações de jogo que envolvam alterar as suas estatísticas (aka. golos e cartoes) apenas podem ser feitas quando o estado de um jogo passa de qualquer outro estado para "TERMINADO". Pretende-se desse modo simular que apenas é possível Registar o resultado de um jogo na ocasião em que este termina.

  - Foram adicionadas funcionalidades que permitem que a maioria das regras do negócio sejam cumpridas de modo que não sejam admitidos jogos inválidos na aplicação: Um local, um jogador e um árbitro não podem ter nenhum jogo marcado na imediação (2 horas para trás ou 2 horas para a frente) do início de um novo jogo que inclua esse local, jogador ou árbitro. Só é possível registar o resultado de um jogo (e desse modo, persistir os golos e cartões respetivos) que não esteja TERMINADO, e quando é feito esse resultado, o jogo fica TERMINADO.

  - Nesta fase, foram criados alguns testes para algumas das funcionalidades dos Jogos.

## Desenho da Interface Web

### Arquitetura para Thymeleaf (Server-side rendering)
  - Thymeleaf Controllers -> Controladores do modelo MVC. Uma vez que se encontram no backend, usam os Handlers diretamente para socorrer aos pedidos dos utilizadores, preenchendo os Models com o conteúdo apropriado.
  - Resources -> Onde se encontram as Views do modelo MVC. Cada view é um ficheiro .html que é servido pelo thymeleaf e preenchido com o Modelo adequado.

### C1 (Decisões de Login e Resultado de Jogo)
  - Login:
  - Registar resultado de um jogo:

### Decisões de Buscas
#### C1 (Jogadores & Jogos)
  -
#### C2 (Equipas & Campeonatos)
  -

## Desenho da interface JavaFX

  ### Arquitetura para JavaFX (Model-View-Controller)
  - Api -> Mapeiam os pedidos dos controllers para os endpoints REST do backend gerando pedidos HTTP.
  - Controllers -> Controladores do modelo MVC. Um controlador para cada View apresentada, gerindo os inputs do utilizador com os pedidos à Api.
  - DTOs -> Servem simultaneamente como Model do modelo MVC, e também como objeto de transferência com o backend, por serem cópias dos DTOs aí usados.
  - Resources/Views -> Views do modelo MVC. Pretendem definir a estrutura da interface gráfica, a ser preenchida pela informação contida dos DTOs, de acordo com o que está definido do Controller apropriado.

  ### C1 (Decisões de Login)
    -
  ### Decisões de Desenho
  #### C1 (Utilizadores)
    - Registar utilizadores:
    - Consultar, Remover ou Atualizar utilizador:
  #### C2 (Equipas)
    - Criar equipas:
    - Consultar, Remover ou Atualizar equipa:
  #### C3 (Jogos)
    - Criar jogos:
    - Registar resultado de um jogo:
  #### C2 (Campeonatos)
    - Criar campeonatos:
    - Consultar, Remover ou Atualizar campeonato:
    - Cancelar jogo de campeonato:

## Dificuldades e Limitações do Projeto
  - Não foi possível implementar o soft-delete da maioria das entidades.
  - Não foi possível tratar do acesso concorrente.
  Ambas estas limitações seriam relativamente simples de corrigir, mas escasseou o tempo para o fazer convenientemente.
