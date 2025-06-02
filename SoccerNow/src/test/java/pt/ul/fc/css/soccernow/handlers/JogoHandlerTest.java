package pt.ul.fc.css.soccernow.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.SoccerNowApplication;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cor;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.exceptions.jogos.AtualizarJogoException;
import pt.ul.fc.css.soccernow.exceptions.jogos.CriarJogoException;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroPostMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

@SpringBootTest(classes = SoccerNowApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@Order(3)
public class JogoHandlerTest {

  private final LocalDto localTeste =
      new LocalDto(
          0L,
          "Estadio Teste",
          "Proprietario Teste",
          999,
          new MoradaDto("Cidade Teste", "Estado Teste", "Pais Teste"));
  private final LocalDto localNovoTeste =
      new LocalDto(
          0L,
          "Estadio2 Teste",
          "Proprietario2 Teste",
          111,
          new MoradaDto("Cidade2 Teste", "Estado2 Teste", "Pais2 Teste"));
  private final LocalDateTime dataTeste = LocalDateTime.of(2025, 01, 01, 8, 0, 0);
  private final UtilizadorDto u0 = new UtilizadorDto(null, 111111110, "007", "900000000");
  private final UtilizadorDto u1 = new UtilizadorDto(null, 111111111, "Ana", "911111111");
  private final UtilizadorDto u2 = new UtilizadorDto(null, 122222222, "Bernardo", "922222222");
  private final UtilizadorDto u3 = new UtilizadorDto(null, 133333333, "Carla", "933333333");
  private final UtilizadorDto u4 = new UtilizadorDto(null, 144444444, "Daniel", "944444444");
  private final UtilizadorDto u5 = new UtilizadorDto(null, 155555555, "Ermelinda", "955555555");
  private final UtilizadorDto u6 = new UtilizadorDto(null, 166666666, "Fernando", "966666666");
  private final UtilizadorDto u7 = new UtilizadorDto(null, 177777777, "Gertrudes", "977777777");
  private final UtilizadorDto u8 = new UtilizadorDto(null, 188888888, "Helder", "988888888");
  private final UtilizadorDto u9 = new UtilizadorDto(null, 199999999, "Idalina", "999999999");
  private final UtilizadorDto u10 = new UtilizadorDto(null, 101010101, "Jorge", "901010101");
  private final UtilizadorDto u11 = new UtilizadorDto(null, 202020202, "Leonor", "902020202");
  private final UtilizadorDto udup0 =
      new UtilizadorDto(null, 211111110, "Duplicado007", "900000000");
  private final UtilizadorDto udup1 =
      new UtilizadorDto(null, 211111111, "DuplicadoAna", "911111111");
  private final UtilizadorDto udup2 =
      new UtilizadorDto(null, 222222220, "DuplicadoBernardo", "922222222");
  private final UtilizadorDto udup3 =
      new UtilizadorDto(null, 233333333, "DuplicadoCarla", "933333333");
  private final UtilizadorDto udup4 =
      new UtilizadorDto(null, 244444444, "DuplicadoDaniel", "944444444");
  private final UtilizadorDto udup5 =
      new UtilizadorDto(null, 255555555, "DuplicadoErmelinda", "955555555");
  private final UtilizadorDto udup6 =
      new UtilizadorDto(null, 266666666, "DuplicadoFernando", "966666666");
  private final UtilizadorDto udup7 =
      new UtilizadorDto(null, 277777777, "DuplicadoGertrudes", "977777777");
  private final UtilizadorDto udup8 =
      new UtilizadorDto(null, 288888888, "DuplicadoHelder", "988888888");
  private final UtilizadorDto udup9 =
      new UtilizadorDto(null, 299999999, "DuplicadoIdalina", "999999999");
  private final UtilizadorDto udup10 =
      new UtilizadorDto(null, 303030303, "DuplicadoJorge", "901010101");
  private final UtilizadorDto udup11 =
      new UtilizadorDto(null, 404040404, "DuplicadoLeonor", "902020202");
  private final ArbitroPostDto a1 = new ArbitroPostDto(u10, new CertificadoDto(true));
  private final ArbitroPostDto a2 = new ArbitroPostDto(u11, new CertificadoDto(false));
  private final ArbitroPostDto adup1 = new ArbitroPostDto(udup10, new CertificadoDto(true));
  private final ArbitroPostDto adup2 = new ArbitroPostDto(udup11, new CertificadoDto(false));
  private final JogadorPostDto j11 = new JogadorPostDto(u0, Posicao.GUARDA_REDES);
  private final JogadorPostDto j12 = new JogadorPostDto(u1, Posicao.FIXO);
  private final JogadorPostDto j13 = new JogadorPostDto(u2, Posicao.ALA_ESQUERDA);
  private final JogadorPostDto j14 = new JogadorPostDto(u3, Posicao.ALA_DIREITA);
  private final JogadorPostDto j15 = new JogadorPostDto(u4, Posicao.PIVO);
  private final JogadorPostDto j21 = new JogadorPostDto(u5, Posicao.GUARDA_REDES);
  private final JogadorPostDto j22 = new JogadorPostDto(u6, Posicao.FIXO);
  private final JogadorPostDto j23 = new JogadorPostDto(u7, Posicao.ALA_ESQUERDA);
  private final JogadorPostDto j24 = new JogadorPostDto(u8, Posicao.ALA_DIREITA);
  private final JogadorPostDto j25 = new JogadorPostDto(u9, Posicao.PIVO);
  private final JogadorPostDto jdup11 = new JogadorPostDto(udup0, Posicao.GUARDA_REDES);
  private final JogadorPostDto jdup12 = new JogadorPostDto(udup1, Posicao.FIXO);
  private final JogadorPostDto jdup13 = new JogadorPostDto(udup2, Posicao.ALA_ESQUERDA);
  private final JogadorPostDto jdup14 = new JogadorPostDto(udup3, Posicao.ALA_DIREITA);
  private final JogadorPostDto jdup15 = new JogadorPostDto(udup4, Posicao.PIVO);
  private final JogadorPostDto jdup21 = new JogadorPostDto(udup5, Posicao.GUARDA_REDES);
  private final JogadorPostDto jdup22 = new JogadorPostDto(udup6, Posicao.FIXO);
  private final JogadorPostDto jdup23 = new JogadorPostDto(udup7, Posicao.ALA_ESQUERDA);
  private final JogadorPostDto jdup24 = new JogadorPostDto(udup8, Posicao.ALA_DIREITA);
  private final JogadorPostDto jdup25 = new JogadorPostDto(udup9, Posicao.PIVO);

  @Autowired private JogoHandler jogoHandler;
  @Autowired private ArbitroHandler arbitroHandler;
  @Autowired private JogadorHandler jogadorHandler;
  @Autowired private EquipaHandler equipaHandler;

  @Autowired private JogadorRepository jogadorRepository;
  @Autowired private ArbitroRepository arbitroRepository;

  @Test
  @Order(1)
  @Rollback(true)
  @DisplayName("Create Jogo requer um Local")
  @Transactional
  public void testCreateJogoLocal() {
    JogoDto jogoDto = new JogoDto();

    List<Long> jogadores = createSamplesJogadores();
    Long e1 = createSampleEquipa1(jogadores);
    Long e2 = createSampleEquipa2(jogadores);

    List<ArbitroPostDto> arbitros = createSamplesArbitros();
    jogoDto.setEquipaDeArbitros(arbitros);

    SelecaoDto s1 = createSampleSelecao1(e1, jogadores.get(0), jogadores);
    SelecaoDto s2 = createSampleSelecao2(e2, jogadores.get(5), jogadores);

    jogoDto.setS1(s1);
    jogoDto.setS2(s2);

    // jogoDto.setLocalDto(localTeste);
    jogoDto.setDiaEHora(dataTeste);
    Exception exception =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());
  }

  @Test
  @Order(2)
  @Rollback(false)
  @DisplayName("Create Jogo cria um jogo com os dados fornecidos.")
  @Transactional
  public void testCreateJogo1() {
    JogoDto jogoDto = new JogoDto();

    List<Long> jogadores = createSamplesJogadores();
    Long e1 = createSampleEquipa1(jogadores);
    Long e2 = createSampleEquipa2(jogadores);

    List<ArbitroPostDto> arbitros = createSamplesArbitros();
    jogoDto.setEquipaDeArbitros(arbitros);

    SelecaoDto s1 = createSampleSelecao1(e1, jogadores.get(0), jogadores);
    SelecaoDto s2 = createSampleSelecao2(e2, jogadores.get(5), jogadores);

    jogoDto.setS1(s1);
    jogoDto.setS2(s2);

    jogoDto.setLocalDto(localTeste);
    jogoDto.setDiaEHora(dataTeste);
    JogoDto response = jogoHandler.createJogo(jogoDto);

    assertEquals(jogoDto, response);
  }

  @Test
  @Order(3)
  @Rollback(true)
  @DisplayName("Create Jogo at the same time and place as another fails.")
  @Transactional
  public void testCreateJogo2() {

    JogoDto jogoDto = new JogoDto();

    List<Long> jogadores = createSamplesJogadoresDuplicados();
    Long e1 = createSampleEquipa1(jogadores);
    Long e2 = createSampleEquipa2(jogadores);

    List<ArbitroPostDto> arbitros = createSamplesArbitrosDuplicados();
    jogoDto.setEquipaDeArbitros(arbitros);

    SelecaoDto s1 = createSampleSelecao1(e1, jogadores.get(0), jogadores);
    SelecaoDto s2 = createSampleSelecao2(e2, jogadores.get(5), jogadores);

    jogoDto.setS1(s1);
    jogoDto.setS2(s2);

    JogoDto oldJogo = jogoHandler.getJogo(1L);
    LocalDto oldLocal = oldJogo.getLocalDto();
    jogoDto.setLocalDto(oldLocal);
    jogoDto.setDiaEHora(dataTeste);

    Exception exception =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());
  }

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName("Create Jogo with attached campeonato")
  @Transactional
  public void testCreateJogoCampeonato() {}

  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo so pode ser feito sobre um Jogo que nao esteja não 'TERMINADO'")
  @Transactional
  public void testRegistarResultadoJogo1() {}

  @Test
  @Order(6)
  @Rollback(false)
  @DisplayName("Registar Resultado de Jogo gera um Jogo 'TERMINADO'")
  @Transactional
  public void testRegistarResultadoJogo2() {}

  @Test
  @Order(7)
  @Rollback(false)
  @DisplayName("Criar Jogo requer pelo menos um Arbitro no Jogo")
  @Transactional
  public void testRegistarResultadoJogo3() {}

  @Test
  @Order(8)
  @Rollback(false)
  @DisplayName("Criar Jogo requer 10 jogadores, 5 em cada selecao")
  @Transactional
  public void testRegistarResultadoJogo4() {}

  @Test
  @Order(9)
  @Rollback(false)
  @DisplayName(
      "Criar Jogo requer que nenhum jogador esteja simultaneamente em ambas as" + " selecoes.")
  @Transactional
  public void testRegistarResultadoJogo5() {}

  @Test
  @Order(10)
  @Rollback(true)
  @DisplayName(
      "Criar Jogo requer que nenhum jogador esteja simultaneamente noutro jogo que"
          + " ocorre na mesma altura.")
  @Transactional
  public void testRegistarResultadoJogo6() {
    JogoDto jogoDto = new JogoDto();

    List<Long> jogadores = createSamplesJogadoresDuplicados();
    Long jogadorOcupado =
        assertDoesNotThrow(
            () -> jogadorHandler.verificarJogador(199999999).getUtilizador().getId());

    jogadores.remove(7);
    jogadores.add(7, jogadorOcupado);

    Long e1 = createSampleEquipa1(jogadores);
    Long e2 = createSampleEquipa2(jogadores);

    List<ArbitroPostDto> arbitros = createSamplesArbitrosDuplicados();
    jogoDto.setEquipaDeArbitros(arbitros);

    SelecaoDto s1 = createSampleSelecao1(e1, jogadores.get(0), jogadores);
    SelecaoDto s2 = createSampleSelecao2(e2, jogadores.get(5), jogadores);

    jogoDto.setS1(s1);
    jogoDto.setS2(s2);

    jogoDto.setLocalDto(localNovoTeste);
    jogoDto.setDiaEHora(dataTeste);

    Exception exception =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());
  }

  @Test
  @Order(11)
  @Rollback(true)
  @DisplayName(
      "Criar Jogo requer que nenhum arbitro esteja simultaneamente noutro jogo que"
          + " ocorre no mesmo dia.")
  @Transactional
  public void testRegistarResultadoJogo7() {
    JogoDto jogoDto = new JogoDto();

    List<Long> jogadores = createSamplesJogadoresDuplicados();
    Long e1 = createSampleEquipa1(jogadores);
    Long e2 = createSampleEquipa2(jogadores);
    SelecaoDto s1 = createSampleSelecao1(e1, jogadores.get(0), jogadores);
    SelecaoDto s2 = createSampleSelecao2(e2, jogadores.get(5), jogadores);
    jogoDto.setS1(s1);
    jogoDto.setS2(s2);
    jogoDto.setLocalDto(localNovoTeste);
    jogoDto.setDiaEHora(dataTeste);

    List<ArbitroPostDto> arbitros = createSamplesArbitrosDuplicados();
    jogoDto.setEquipaDeArbitros(arbitros);

    ArbitroDto arbitroOcupado =
        assertDoesNotThrow(() -> arbitroHandler.verificarArbitro(101010101));

    arbitros.remove(0);
    arbitros.add(0, new ArbitroPostDto(arbitroOcupado.getUtilizador(), arbitroOcupado.getCertificado()));

    Exception exception =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());
  }

  @Test
  @Order(12)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo de Campeonato requer que Arbitro Principal tenha Certificado")
  @Transactional
  public void testRegistarJogoCampeonatoInvalido() {}

  @Test
  @Order(13)
  @Rollback(true)
  @DisplayName("Cartao so pode ser registado no mesmo dia e após o inicio do Jogo")
  @Transactional
  public void testRegistarCartaoInvalido() {
    JogoDto jogoAcabado = new JogoDto();
    jogoAcabado.setId(1L);
    jogoAcabado.setEstadoDeJogo(EstadoDeJogo.TERMINADO);

    Long marcador =
        assertDoesNotThrow(
            () -> jogadorHandler.verificarJogador(111111110).getUtilizador().getId());
    GoloDto g = new GoloDto(dataTeste.plusMinutes(30), 1L, marcador, 1L);
    Set<GoloDto> golos = new HashSet<>();
    golos.add(g);

    Long arbitro =
        assertDoesNotThrow(
            () -> arbitroHandler.verificarArbitro(101010101).getUtilizador().getId());
    CartaoDto c = new CartaoDto(dataTeste.plusHours(5), 1L, marcador, arbitro, 1L, Cor.AMARELO);
    Set<CartaoDto> cartoes = new HashSet<>();
    cartoes.add(c);

    EstatisticaJogoDto ejdto = new EstatisticaJogoDto();
    ejdto.setGolos(golos);
    ejdto.setCartoes(cartoes);
    jogoAcabado.setStats(ejdto);
    Exception exception =
        assertThrows(
            AtualizarJogoException.class,
            () -> {
              jogoHandler.registarResultadoDeJogo(jogoAcabado);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());
  }

  @Test
  @Order(14)
  @Rollback(true)
  @DisplayName("Golo nao pode ser registado num jogo TERMINADO")
  @Transactional
  public void testRegistarGoloInvalido() {
    JogoDto jogoAcabado = new JogoDto();
    jogoAcabado.setId(1L);
    jogoAcabado.setEstadoDeJogo(EstadoDeJogo.TERMINADO);

    Set<GoloDto> golos = new HashSet<>();
    Set<CartaoDto> cartoes = new HashSet<>();

    EstatisticaJogoDto ejdto = new EstatisticaJogoDto();
    ejdto.setGolos(golos);
    ejdto.setCartoes(cartoes);
    jogoAcabado.setStats(ejdto);
    JogoDto terminado = jogoHandler.registarResultadoDeJogo(jogoAcabado);

    Long marcador =
        assertDoesNotThrow(
            () -> jogadorHandler.verificarJogador(111111110).getUtilizador().getId());
    GoloDto g = new GoloDto(dataTeste.plusMinutes(30), 1L, marcador, 1L);
    golos.add(g);
    ejdto.setGolos(golos);
    terminado.setStats(ejdto);

    Exception exception =
        assertThrows(
            AtualizarJogoException.class,
            () -> {
              jogoHandler.registarResultadoDeJogo(terminado);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());
  }

  @Test
  @Order(15)
  @Rollback(false)
  @DisplayName("Equipa vencedora eh a que marcou mais golos.")
  @Transactional
  public void testRegistarResultadoJogo9() {
    JogoDto jogoDB = jogoHandler.getJogo(1L);
    Long e1 = jogoDB.getS1().getEquipa();
    Long marcador = jogoDB.getS1().getAlaDireita();

    JogoDto jogoAcabado = new JogoDto();
    jogoAcabado.setId(1L);
    jogoAcabado.setEstadoDeJogo(EstadoDeJogo.TERMINADO);

    GoloDto g = new GoloDto(dataTeste.plusMinutes(30), 1L, marcador, e1);
    Set<GoloDto> golos = new HashSet<>();
    golos.add(g);

    Long arbitro =
        assertDoesNotThrow(
            () -> arbitroHandler.verificarArbitro(101010101).getUtilizador().getId());
    CartaoDto c = new CartaoDto(dataTeste.plusMinutes(15), 1L, marcador, arbitro, e1, Cor.AMARELO);
    Set<CartaoDto> cartoes = new HashSet<>();
    cartoes.add(c);

    EstatisticaJogoDto ejdto = new EstatisticaJogoDto();
    ejdto.setGolos(golos);
    ejdto.setCartoes(cartoes);
    jogoAcabado.setStats(ejdto);

    JogoDto resultado = jogoHandler.registarResultadoDeJogo(jogoAcabado);
    assertEquals(e1, resultado.getEquipaVencedora());
  }

  private List<Long> createSamplesJogadores() {
    List<JogadorDto> jogadoresDto = new ArrayList<>();
    jogadoresDto.add(new JogadorDto(j11));
    jogadoresDto.add(new JogadorDto(j12));
    jogadoresDto.add(new JogadorDto(j13));
    jogadoresDto.add(new JogadorDto(j14));
    jogadoresDto.add(new JogadorDto(j15));
    jogadoresDto.add(new JogadorDto(j21));
    jogadoresDto.add(new JogadorDto(j22));
    jogadoresDto.add(new JogadorDto(j23));
    jogadoresDto.add(new JogadorDto(j24));
    jogadoresDto.add(new JogadorDto(j25));
    List<Jogador> jogadores =
        jogadoresDto.stream().map(JogadorMapper::dtoToJogador).collect(Collectors.toList());
    List<Jogador> saved = jogadorRepository.saveAllAndFlush(jogadores);
    return saved.stream().map(j -> j.getId()).collect(Collectors.toList());
  }

  private List<Long> createSamplesJogadoresDuplicados() {
    List<JogadorDto> jogadoresDto = new ArrayList<>();
    jogadoresDto.add(new JogadorDto(jdup11));
    jogadoresDto.add(new JogadorDto(jdup12));
    jogadoresDto.add(new JogadorDto(jdup13));
    jogadoresDto.add(new JogadorDto(jdup14));
    jogadoresDto.add(new JogadorDto(jdup15));
    jogadoresDto.add(new JogadorDto(jdup21));
    jogadoresDto.add(new JogadorDto(jdup22));
    jogadoresDto.add(new JogadorDto(jdup23));
    jogadoresDto.add(new JogadorDto(jdup24));
    jogadoresDto.add(new JogadorDto(jdup25));
    List<Jogador> jogadores =
        jogadoresDto.stream().map(JogadorMapper::dtoToJogador).collect(Collectors.toList());
    List<Jogador> saved = jogadorRepository.saveAllAndFlush(jogadores);
    return saved.stream().map(j -> j.getId()).collect(Collectors.toList());
  }

  private SelecaoDto createSampleSelecao1(Long equipa, Long cap, List<Long> jogadores) {
    Map<Posicao, Long> s1j = new EnumMap<>(Posicao.class);
    for (int i = 0; i < 5; i++) {
      s1j.put(Posicao.values()[i], jogadores.get(i));
    }
    SelecaoDto s1 = new SelecaoDto(equipa, cap);
    s1.setJogadores(s1j);
    return s1;
  }

  private SelecaoDto createSampleSelecao2(Long equipa, Long cap, List<Long> jogadores) {
    Map<Posicao, Long> s1j = new EnumMap<>(Posicao.class);
    for (int i = 0; i < 5; i++) {
      s1j.put(Posicao.values()[i], jogadores.get(i + 5));
    }
    SelecaoDto s1 = new SelecaoDto(equipa, cap);
    s1.setJogadores(s1j);
    return s1;
  }

  private List<ArbitroPostDto> createSamplesArbitros() {
    List<ArbitroPostDto> arbitrosDto = new ArrayList<>();
    arbitrosDto.add(a1);
    arbitrosDto.add(a2);
    List<Arbitro> arbitros =
        arbitrosDto.stream().map(ArbitroPostMapper::dtoToArbitro).collect(Collectors.toList());
    List<Arbitro> saved = arbitroRepository.saveAllAndFlush(arbitros);
    return saved.stream().map(ArbitroPostMapper::arbitroToDto).collect(Collectors.toList());
  }

  private List<ArbitroPostDto> createSamplesArbitrosDuplicados() {
    List<ArbitroPostDto> arbitrosDto = new ArrayList<>();
    arbitrosDto.add(adup1);
    arbitrosDto.add(adup2);
    List<Arbitro> arbitros =
        arbitrosDto.stream().map(ArbitroPostMapper::dtoToArbitro).collect(Collectors.toList());
    List<Arbitro> saved = arbitroRepository.saveAllAndFlush(arbitros);
    return saved.stream().map(ArbitroPostMapper::arbitroToDto).collect(Collectors.toList());
  }

  private Long createSampleEquipa1(List<Long> jogadores) {
    EquipaDto e1Dto = new EquipaDto(null, "Abelhudos", jogadores);
    return equipaHandler.registarEquipa(e1Dto).getId();
  }

  private Long createSampleEquipa2(List<Long> jogadores) {
    EquipaDto e1Dto = new EquipaDto(null, "Bananas", jogadores);
    return equipaHandler.registarEquipa(e1Dto).getId();
  }
}
