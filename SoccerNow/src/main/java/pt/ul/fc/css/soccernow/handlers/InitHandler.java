package pt.ul.fc.css.soccernow.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaArbitroDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.mappers.equipas.EquipaMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.LocalMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroPostMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repositories.CartaoRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.GoloRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;
import pt.ul.fc.css.soccernow.repositories.LocalRepository;
import pt.ul.fc.css.soccernow.repositories.SelecaoRepository;

@Service
public class InitHandler {

  @Autowired private JogadorHandler jogadorHandler;

  @Autowired private ArbitroHandler arbitroHandler;

  @Autowired private EquipaHandler equipaHandler;

  @Autowired private JogoHandler jogoHandler;

  @Autowired private EstatisticasHandler estatisticasHandler;

  @Autowired private JogadorRepository jogadorRepository;

  @Autowired private ArbitroRepository arbitroRepository;

  @Autowired private EquipaRepository equipaRepository;

  @Autowired private SelecaoRepository selecaoRepository;

  @Autowired private JogoRepository jogoRepository;

  @Autowired private CampeonatoRepository campeonatoRepository;

  @Autowired private GoloRepository goloRepository;

  @Autowired private CartaoRepository cartaoRepository;

  @Autowired private LocalRepository localRepository;

  @Transactional
  public void init() {
    fillJogos();
  }

  @Transactional
  public void assassinate() {
    deleteAll();
  }

  @Transactional
  private void deleteAll() {
    goloRepository.deleteAll();
    cartaoRepository.deleteAll();
    jogoRepository.deleteAll();
    equipaRepository.deleteAll();
    arbitroRepository.deleteAll();
    jogadorRepository.deleteAll();
    localRepository.deleteAll();
  }

  /**
   * Preenche a base de dados com dados de teste. - B Registo de utilizadores :check: - E Criação de
   * equipas :check:
   */
  @Transactional
  private void fillJogos() {
    LocalDto l1 =
        new LocalDto(
            null,
            "Estadio1",
            "Prop1",
            10,
            new MoradaDto("1111-111", "Rua 1", "Localidade1", "Cidade1", "Estado1", "Pais1"));
    LocalDto l2 =
        new LocalDto(
            null,
            "Estadio2",
            "Prop2",
            20,
            new MoradaDto("2222-222", "Rua 2", "Localidade2", "Cidade2", "Estado2", "Pais2"));
    LocalDto l3 =
        new LocalDto(
            null,
            "Estadio3",
            "Prop3",
            30,
            new MoradaDto("3333-333", "Rua 3", "Localidade3", "Cidade3", "Estado3", "Pais3"));

    // Convert LocalDto to Local and save them
    Local local1 = LocalMapper.dtoToLocal(l1);
    Local local2 = LocalMapper.dtoToLocal(l2);
    Local local3 = LocalMapper.dtoToLocal(l3);

    localRepository.save(local1);
    localRepository.save(local2);
    localRepository.save(local3);
    localRepository.flush();

    LocalDateTime d1 = LocalDateTime.parse("2025-01-01T01:01:01");
    LocalDateTime d2 = LocalDateTime.parse("2025-02-02T02:02:02");
    LocalDateTime d3 = LocalDateTime.parse("2025-03-03T03:03:03");

    EstatisticaJogadorDto emptyEstatisticaJogadorDto = new EstatisticaJogadorDto();
    emptyEstatisticaJogadorDto.setGolos(Set.of());
    emptyEstatisticaJogadorDto.setCartoes(Set.of());

    EstatisticaArbitroDto emptyEstatisticaArbitroDto = new EstatisticaArbitroDto();
    emptyEstatisticaArbitroDto.setCartoes(Set.of());

    UtilizadorDto u0 = new UtilizadorDto(null, 111111110, "007", "900000000");
    UtilizadorDto u1 = new UtilizadorDto(null, 111111111, "Ana", "911111111");
    UtilizadorDto u2 = new UtilizadorDto(null, 122222222, "Bernardo", "922222222");
    UtilizadorDto u3 = new UtilizadorDto(null, 133333333, "Carla", "933333333");
    UtilizadorDto u4 = new UtilizadorDto(null, 144444444, "Daniel", "944444444");
    UtilizadorDto u5 = new UtilizadorDto(null, 155555555, "Ermelinda", "955555555");
    UtilizadorDto u6 = new UtilizadorDto(null, 166666666, "Fernando", "966666666");
    UtilizadorDto u7 = new UtilizadorDto(null, 177777777, "Gertrudes", "977777777");
    UtilizadorDto u8 = new UtilizadorDto(null, 188888888, "Helder", "988888888");
    UtilizadorDto u9 = new UtilizadorDto(null, 199999999, "Idalina", "999999999");
    UtilizadorDto u10 = new UtilizadorDto(null, 101010101, "Jorge", "901010101");
    UtilizadorDto u11 = new UtilizadorDto(null, 202020202, "Leonor", "902020202");
    UtilizadorDto u12 = new UtilizadorDto(null, 303030303, "Miguel", "903030303");
    ArbitroPostDto a1 = new ArbitroPostDto(u10, new CertificadoDto(true));
    ArbitroPostDto a2 = new ArbitroPostDto(u11, new CertificadoDto(true));
    ArbitroPostDto a3 = new ArbitroPostDto(u12, new CertificadoDto(true));

    JogadorDto j11 = new JogadorDto(u0, Posicao.GUARDA_REDES, emptyEstatisticaJogadorDto);
    JogadorDto j12 = new JogadorDto(u1, Posicao.FIXO, emptyEstatisticaJogadorDto);
    JogadorDto j13 = new JogadorDto(u2, Posicao.ALA_ESQUERDA, emptyEstatisticaJogadorDto);
    JogadorDto j14 = new JogadorDto(u3, Posicao.ALA_DIREITA, emptyEstatisticaJogadorDto);
    JogadorDto j15 = new JogadorDto(u4, Posicao.PIVO, emptyEstatisticaJogadorDto);
    JogadorDto j21 = new JogadorDto(u5, Posicao.GUARDA_REDES, emptyEstatisticaJogadorDto);
    JogadorDto j22 = new JogadorDto(u6, Posicao.FIXO, emptyEstatisticaJogadorDto);
    JogadorDto j23 = new JogadorDto(u7, Posicao.ALA_ESQUERDA, emptyEstatisticaJogadorDto);
    JogadorDto j24 = new JogadorDto(u8, Posicao.ALA_DIREITA, emptyEstatisticaJogadorDto);
    JogadorDto j25 = new JogadorDto(u9, Posicao.PIVO, emptyEstatisticaJogadorDto);

    /* -- Saving Jogadores */
    List<JogadorDto> jogadoresDto = new ArrayList<>();
    jogadoresDto.add(j11);
    jogadoresDto.add(j12);
    jogadoresDto.add(j13);
    jogadoresDto.add(j14);
    jogadoresDto.add(j15);
    jogadoresDto.add(j21);
    jogadoresDto.add(j22);
    jogadoresDto.add(j23);
    jogadoresDto.add(j24);
    jogadoresDto.add(j25);
    List<Jogador> jogadores =
        jogadoresDto.stream().map(JogadorMapper::dtoToJogador).collect(Collectors.toList());
    jogadorRepository.saveAllAndFlush(jogadores);

    /* -- Saving Arbitros */
    List<ArbitroPostDto> arbitrosDto = new ArrayList<>();
    arbitrosDto.add(a1);
    arbitrosDto.add(a2);
    arbitrosDto.add(a3);
    List<Arbitro> arbitros =
        arbitrosDto.stream().map(ArbitroPostMapper::dtoToArbitro).collect(Collectors.toList());
    arbitroRepository.saveAllAndFlush(arbitros);
    List<ArbitroPostDto> savedArbitrosDto =
        arbitroRepository.findAll().stream()
            .map(ArbitroPostMapper::arbitroToDto)
            .collect(Collectors.toList());
    List<Arbitro> savedArbitros = arbitroRepository.findAll();

    /* Predictable id's for these users */
    Long j1id = 1L;
    Long j2id = 2L;
    Long j3id = 3L;
    Long j4id = 4L;
    Long j5id = 5L;
    Long j6id = 6L;
    Long j7id = 7L;
    Long j8id = 8L;
    Long j9id = 9L;
    Long j10id = 10L;

    Long a1id = 11L;
    Long a2id = 12L;
    Long a3id = 13L;

    List<Long> lista1 = new ArrayList<>();
    lista1.add(j1id);
    lista1.add(j2id);
    lista1.add(j3id);
    lista1.add(j4id);
    lista1.add(j5id);
    List<Long> lista2 = new ArrayList<>();
    lista2.add(j6id);
    lista2.add(j7id);
    lista2.add(j8id);
    lista2.add(j9id);
    lista2.add(j10id);
    List<Long> lista3 = new ArrayList<>();
    lista3.add(j1id);
    lista3.add(j2id);
    lista3.add(j3id);

    EquipaDto e1 = new EquipaDto(null, "Abelhudos", lista1);
    EquipaDto e2 = new EquipaDto(null, "Bananas", lista2);
    EquipaDto e3 = new EquipaDto(null, "Coscuvilheiros", lista3);
    EquipaDto e4 = new EquipaDto(null, "Dromedarios", lista1); // Usa lista 1

    /** Saving Equipas with their respective Jogadores */
    List<EquipaDto> equipasDto = new ArrayList<>();
    equipasDto.add(e1);
    equipasDto.add(e2);
    equipasDto.add(e3);
    equipasDto.add(e4);
    List<Equipa> equipas =
        EquipaMapper.manyDtosToEquipas(equipasDto, jogadorRepository, jogoRepository);
    equipaRepository.saveAllAndFlush(equipas);

    /** Predictable id's for these Equipas */
    Long e1id = 1L;
    Long e2id = 2L;
    Long e3id = 3L;

    Map<Posicao, Long> s1j = new EnumMap<>(Posicao.class);
    for (int i = 0; i < 5; i++) {
      s1j.put(Posicao.values()[i], lista1.get(i));
    }
    Map<Posicao, Long> s2j = new EnumMap<>(Posicao.class);
    for (int i = 0; i < 5; i++) {
      s2j.put(Posicao.values()[i], lista2.get(i));
    }

    SelecaoDto s1 = new SelecaoDto();
    s1.setJogadores(s1j);
    s1.setEquipa(e1id);
    s1.setCapitao(j1id);
    SelecaoDto s2 = new SelecaoDto();
    s2.setJogadores(s2j);
    s2.setEquipa(e2id);
    s2.setCapitao(j6id);

    // To make sure abitros have been saved
    JogoDto j1 =
        new JogoDto(
            null,
            EstadoDeJogo.AGENDADO,
            LocalMapper.localToDto(local1),
            d1,
            s1,
            s2,
            savedArbitrosDto);
    JogoDto j2 =
        new JogoDto(
            null,
            EstadoDeJogo.AGENDADO,
            LocalMapper.localToDto(local2),
            d2,
            s1,
            s2,
            savedArbitrosDto);
    JogoDto j3 =
        new JogoDto(
            null,
            EstadoDeJogo.AGENDADO,
            LocalMapper.localToDto(local3),
            d3,
            s1,
            s2,
            savedArbitrosDto);
    JogoDto j4 =
        new JogoDto(
            null,
            EstadoDeJogo.AGENDADO,
            LocalMapper.localToDto(local1),
            d1,
            s1,
            s2,
            savedArbitrosDto); // Usa o mesmo local que o jogo 1

    /** Saving Jogos with their respective Equipas and Arbitros */
    List<JogoDto> jogosDto = new ArrayList<>();
    jogosDto.add(j1);
    jogosDto.add(j2);
    jogosDto.add(j3);
    jogosDto.add(j4);

    JogoDto teste1 = jogoHandler.createJogo(j1);

    System.out.println(teste1);
    // jogoRepository.save(teste1);
    /*
    List<Jogo> jogos =
        jogosDto.stream()
            .map(
                jDto ->
                    JogoMapper.createDtoToJogo(
                        jDto,
                        arbitroRepository,
                        selecaoRepository,
                        equipaRepository,
                        campeonatoRepository))
            .collect(Collectors.toList());
    jogoRepository.saveAllAndFlush(jogos);
    */
    /** Predictable id's for these Jogos */
    Long jogo1id = 1L;
    Long jogo2id = 2L;
    Long jogo3id = 3L;
    Long jogo4id = 4L;

    /** ALTERATION OF DATA */
    /*
    Jogo jogo1 = jogoRepository.findById(jogo1id).get();
    Jogador jogador1 = jogadorRepository.findById(j1id).get();
    */
    /** Occurrence and persistence of jogo1 */
    /*
    GoloDto g1 = new GoloDto();
    g1.setQuando(LocalDateTime.parse("2025-01-01T01:01:01"));
    g1.setJogo(jogo1id);
    g1.setMarcador(j1id);
    g1.setEquipa(e1id);
    GoloDto g2 = new GoloDto();
    g2.setQuando(LocalDateTime.parse("2025-01-01T01:01:01"));
    g2.setJogo(jogo1id);
    g2.setMarcador(j6id);
    g2.setEquipa(e2id);
    CartaoDto c1 = new CartaoDto();
    c1.setQuando(d3);
    c1.setCor(Cor.AMARELO);
    c1.setJogo(jogo1id);
    c1.setAtribuidoA(j1id);
    c1.setArbitro(a1id);
    c1.setEquipa(e1id);

    Set<GoloDto> golosJogo1 = Set.of(g1, g2);
    Set<CartaoDto> cartoesJogo1 = Set.of();

    EstatisticaJogoDto estatisticaJogoDto = new EstatisticaJogoDto();
    estatisticaJogoDto.setGolos(golosJogo1);
    estatisticaJogoDto.setCartoes(cartoesJogo1);

    EstatisticaJogadorDto estatisticaJogadorDto = new EstatisticaJogadorDto();
    estatisticaJogadorDto.setGolos(Set.of(g1));
    estatisticaJogadorDto.setCartoes(Set.of(c1));

    */
    /** Persistencia das alterações no Jogo */
    /*
    JogoDto jogo1Dto = JogoMapper.jogoToDto(jogo1);
    jogo1Dto.setStats(estatisticaJogoDto);
    estatisticasHandler.updateEstatisticaJogo(jogo1Dto);
    */
    /** Persistencia das alterações no Jogador */
    /*
    JogadorDto jogador1Dto = JogadorMapper.jogadorToDto(jogador1);
    jogador1Dto.setEstatisticas(estatisticaJogadorDto);
    estatisticasHandler.updateEstatisticaJogador(jogador1Dto);
    */
  }
}
