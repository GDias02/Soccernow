package pt.ul.fc.css.soccernow.handlers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.QJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.exceptions.jogos.AtualizarJogoException;
import pt.ul.fc.css.soccernow.exceptions.jogos.CriarJogoException;
import pt.ul.fc.css.soccernow.exceptions.jogos.JogoLocalException;
import pt.ul.fc.css.soccernow.exceptions.jogos.SelecaoException;
import pt.ul.fc.css.soccernow.mappers.jogos.CartaoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.GoloMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.JogoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.LocalMapper;
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
public class JogoHandler implements IJogoHandler {

  @Autowired private JogoRepository jogoRepository;

  @Autowired private GoloRepository goloRepository;

  @Autowired private LocalRepository localRepository;

  @Autowired private CartaoRepository cartaoRepository;

  @Autowired private EquipaRepository equipaRepository;

  @Autowired private SelecaoRepository selecaoRepository;

  @Autowired private JogadorRepository jogadorRepository;

  @Autowired private ArbitroRepository arbitroRepository;

  @Autowired private CampeonatoRepository campeonatoRepository;

  @Autowired private EstatisticasHandler estatisticasHandler;

  @PersistenceContext private EntityManager entityManager;

  @Transactional
  public JogoDto getJogo(long id) {
    Optional<Jogo> jogoOptional = jogoRepository.findById(id);
    if (jogoOptional.isEmpty()) {
      return null;
    }
    JogoDto res = JogoMapper.jogoToDto(jogoOptional.get());
    EstatisticaJogoDto stat =
        EstatisticaMapper.estatisticaJogoToDto(estatisticasHandler.criarEstatisticaJogo(res));
    res.setStats(stat);
    return res;
  }

  @Transactional
  public JogoDto saveJogo(Jogo jogo) {
    return JogoMapper.jogoToDto(jogoRepository.save(jogo));
  }

  @Transactional
  public Boolean localDisponivel(JogoDto jogodto) {
    LocalDto localdto = jogodto.getLocalDto();
    if (localdto == null) return false;
    Local local = getLocalFromRepository(localdto);
    if (local == null) return true;
    LocalDateTime diaEHora = jogodto.getDiaEHora();
    LocalDateTime start = diaEHora.minus(2, ChronoUnit.HOURS);
    LocalDateTime end = diaEHora.plus(2, ChronoUnit.HOURS);
    return !jogoRepository.existsLocalAtSameIntervalContainedIn(local.getId(), start, end);
  }

  @Transactional
  public Boolean arbitrosDisponiveis(JogoDto jogodto) {
    LocalDateTime diaEHora = jogodto.getDiaEHora();
    LocalDateTime start = diaEHora.minus(2, ChronoUnit.HOURS);
    LocalDateTime end = diaEHora.plus(2, ChronoUnit.HOURS);
    List<Arbitro> arbitros = getArbitroFromDto(jogodto.getEquipaDeArbitros());
    List<Jogo> result =
        jogoRepository.existsArbitroOcupadoAtSameIntervalContainedIn(start, end, arbitros);
    return result.size() == 0;
  }

  @Transactional
  public Boolean jogadoresDisponiveis(JogoDto jogodto) {
    LocalDateTime diaEHora = jogodto.getDiaEHora();
    LocalDateTime start = diaEHora.minus(2, ChronoUnit.HOURS);
    LocalDateTime end = diaEHora.plus(2, ChronoUnit.HOURS);
    SelecaoDto s1dto = jogodto.getS1();
    SelecaoDto s2dto = jogodto.getS2();
    Set<Long> jogs = new HashSet<>();
    if (s1dto != null) {
      jogs.addAll(s1dto.getJogadoresIds());
    }
    if (s2dto != null) {
      jogs.addAll(s2dto.getJogadoresIds());
    }
    if (!jogs.isEmpty()) {
      return !selecaoRepository.existsJogoAtSameIntervalContainedIn(start, end, jogs);
    } else {
      return true;
    }
  }

  @Transactional(rollbackFor = CriarJogoException.class)
  public JogoDto createJogo(JogoDto jogodto) throws CriarJogoException, JogoLocalException {
    if (jogodto == null)
      throw new CriarJogoException(
          "JogoDto é null. É preciso passar informação para criar um novo jogo.");
    if (jogodto.getId() != null)
      throw new CriarJogoException(
          "Para criar um novo jogo, não pode ser passado um id de um jogo existente.");
    if (jogodto.getLocalDto() == null)
      throw new CriarJogoException(
          "JogoDto não tem um Local definido. É preciso passar um local existente ou a informação"
              + " de um local onde o jogo irá decorrer.");
    if (jogodto.getDiaEHora() == null)
      throw new CriarJogoException("O novo jogo não tem dia ou hora definidos");
    if (jogodto.getS1() == null)
      throw new CriarJogoException("O novo jogo não tem a selecao 1 definida.");
    if (jogodto.getS2() == null)
      throw new CriarJogoException("O novo jogo não tem a selecao 2 definida.");
    if (jogodto.getEquipaDeArbitros() == null || jogodto.getEquipaDeArbitros().isEmpty())
      throw new CriarJogoException("O novo jogo não tem equipa de árbitros.");

    LocalDto localdto = jogodto.getLocalDto();
    Local local = createLocal(localdto);

    LocalDateTime diaEHora = jogodto.getDiaEHora();
    LocalDateTime start = diaEHora.minus(2, ChronoUnit.HOURS);
    LocalDateTime end = diaEHora.plus(2, ChronoUnit.HOURS);

    if (jogoRepository.existsLocalAtSameIntervalContainedIn(local.getId(), start, end))
      throw new CriarJogoException("Ja existe um jogo marcado nesse local dentro desse periodo.");

    List<Arbitro> arbitros = getArbitroFromDto(jogodto.getEquipaDeArbitros());
    List<Jogo> result =
        jogoRepository.existsArbitroOcupadoAtSameIntervalContainedIn(start, end, arbitros);

    if (result.size() > 0) {
      throw new CriarJogoException(
          "Algum dos árbitros já tem um jogo marcado dentro desse periodo.");
    }

    SelecaoDto s1dto = jogodto.getS1();
    SelecaoDto s2dto = jogodto.getS2();

    Set<Long> jogs1 = s1dto.getJogadoresIds();
    Set<Long> jogs2 = s2dto.getJogadoresIds();

    if (selecaoRepository.existsJogoAtSameIntervalContainedIn(start, end, jogs1)) {
      throw new CriarJogoException(
          "Algum dos jogadores da Selecao 1 já tem um jogo marcado dentro desse periodo.");
    }
    if (selecaoRepository.existsJogoAtSameIntervalContainedIn(start, end, jogs2)) {
      throw new CriarJogoException(
          "Algum dos jogadores da Selecao 2 já tem um jogo marcado dentro desse periodo.");
    }

    Equipa e1 = getEquipaFromSelecao(s1dto);
    Equipa e2 = getEquipaFromSelecao(s2dto);

    Jogador c1 = getJogadorFromSelecao(s1dto.getCapitao(), e1);
    Jogador c2 = getJogadorFromSelecao(s2dto.getCapitao(), e2);
    Map<Posicao, Jogador> s1jogadores = new EnumMap<>(Posicao.class);
    Map<Posicao, Jogador> s2jogadores = new EnumMap<>(Posicao.class);
    s1dto.getJogadores().forEach((k, v) -> s1jogadores.put(k, getJogadorFromSelecao(v, e1)));
    s2dto.getJogadores().forEach((k, v) -> s2jogadores.put(k, getJogadorFromSelecao(v, e2)));

    Selecao s1 = new Selecao(e1, c1, s1jogadores);
    Selecao s2 = new Selecao(e2, c2, s2jogadores);

    Jogo jogo = JogoMapper.createDtoToJogo(jogodto, campeonatoRepository);

    jogo.setLocal(local);
    jogo.setEquipaDeArbitros(arbitros);
    jogo.setS1(s1);
    jogo.setS2(s2);

    Jogo savedJogo = jogoRepository.saveAndFlush(jogo);
    jogodto.setId(savedJogo.getId());
    return jogodto;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SelecaoException.class)
  public Equipa getEquipaFromSelecao(SelecaoDto selecaodto) throws SelecaoException {
    if (selecaodto.getEquipa() != null && selecaodto.getEquipa() > 0) {
      Optional<Equipa> maybeEquipa = equipaRepository.findById(selecaodto.getEquipa());
      if (maybeEquipa.isPresent()) {
        return maybeEquipa.get();
      } else {
        throw new SelecaoException("Foi fornecido um id de uma equipa que nao existe.");
      }
    } else {
      throw new SelecaoException("Falta definir a equipa da seleção!");
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SelecaoException.class)
  public Jogador getJogadorFromSelecao(Long jogadorId, Equipa e) throws SelecaoException {
    if (jogadorId != null && jogadorId > 0) {
      Jogador j = e.getJogador(jogadorId);
      if (j == null) throw new SelecaoException("O jogador nao pertence à equipa desta seleção");
      return j;
    } else {
      throw new SelecaoException("Falta definir um jogador da seleção!");
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SelecaoException.class)
  public List<Arbitro> getArbitroFromDto(List<ArbitroPostDto> arbitrosDto) throws SelecaoException {
    try {
      List<Arbitro> arbitros =
          arbitrosDto.stream()
              .map(a -> arbitroRepository.getReferenceById(a.getUtilizador().getId()))
              .collect(Collectors.toList());
      return arbitros;
    } catch (EntityNotFoundException e) {
      throw new CriarJogoException("Pelo menos um dos árbitros indicados não está registado.");
    }
  }

  @Transactional(rollbackFor = JogoLocalException.class)
  public Local createLocal(LocalDto localdto) throws JogoLocalException {
    Local local = getLocalFromRepository(localdto);
    if (local != null) return local;
    Local novolocal = LocalMapper.dtoToLocal(localdto);
    return localRepository.saveAndFlush(novolocal);
  }

  @Transactional(rollbackFor = JogoLocalException.class)
  public Local getLocalFromRepository(LocalDto localdto) throws JogoLocalException {
    Optional<Local> maybelocal;
    if (localdto.getId() != null && localdto.getId() > 0) {
      maybelocal = localRepository.findById(localdto.getId());
      if (maybelocal.isPresent()) {
        return maybelocal.get();
      } else throw new JogoLocalException("Não existe nenhum Local guardado com esse ID.");
    }
    if (localdto.getNome().isBlank()) {
      throw new JogoLocalException("Um Local tem de ter um nome.");
    }
    maybelocal = localRepository.findByNome(localdto.getNome());
    if (maybelocal.isPresent()) {
      return maybelocal.get();
    }
    return null;
  }

  @Transactional
  public JogoDto registarResultadoDeJogo(JogoDto jogodto) throws AtualizarJogoException {
    if (jogodto == null) {
      return null;
    }
    if (jogodto.getId() == null) {
      return null;
    }
    if (jogodto.getStats() == null) {
      return null;
    }

    Optional<Jogo> jogoOptional = jogoRepository.findById(jogodto.getId());
    if (jogoOptional.isEmpty()) {
      throw new AtualizarJogoException("Nao é possivel atualizar um jogo que não está registado.");
    }
    Jogo jogo = jogoOptional.get();

    if (jogo.getEstadoDeJogo() == EstadoDeJogo.TERMINADO) {
      throw new AtualizarJogoException("Nao é possivel atualizar um jogo que já terminou.");
    } // Nao se pode registar resultados após o jogo ter terminado
    if (jogodto.getEstadoDeJogo() != EstadoDeJogo.TERMINADO) {
      throw new AtualizarJogoException(
          "Para registar o resultado, deve indicar que o jogo acabou de terminar.");
    } // Pressupoe que este metodo só regista golos e cartoes após ter terminado

    EstatisticaJogoDto ej = jogodto.getStats();
    Set<Golo> golosMarcados =
        ej.getGolos().stream().map(g -> scoreGoloFromDto(g, jogo)).collect(Collectors.toSet());
    Set<Cartao> cartoesMarcados =
        ej.getCartoes().stream().map(c -> markCartaoFromDto(c, jogo)).collect(Collectors.toSet());

    if (!golosMarcados.isEmpty()) {
      goloRepository.saveAll(golosMarcados);
    }
    if (!cartoesMarcados.isEmpty()) {
      cartaoRepository.saveAll(cartoesMarcados);
    }

    EstatisticaJogo stat = new EstatisticaJogo();
    stat.setCartoes(cartoesMarcados);
    stat.setGolos(golosMarcados);

    stat.setJogo(jogo);
    jogo.setStats(stat);
    jogo.setPlacar(stat.getPlacar());
    jogo.setEstadoAtual(EstadoDeJogo.TERMINADO);
    Jogo savedJogo = jogoRepository.save(jogo);
    return JogoMapper.jogoToDto(savedJogo);
  }

  private Golo scoreGoloFromDto(GoloDto golodto, Jogo j) throws AtualizarJogoException {
    if (golodto == null) return null;
    LocalDateTime start = j.getDiaEHora();
    LocalDateTime end = start.plusHours(2);
    LocalDateTime marcado = golodto.getQuando();
    if (marcado == null || !(marcado.isBefore(end) && marcado.isAfter(start))) {
      throw new AtualizarJogoException("O golo só pode ser marcado durante o periodo de jogo!");
    }

    Long e1 = j.getS1().getEquipa().getId();
    Long e2 = j.getS2().getEquipa().getId();
    Long equipa = golodto.getEquipa();
    if (equipa == null || !(equipa.equals(e1) || equipa.equals(e2))) {
      throw new AtualizarJogoException("O golo só pode ser marcado por uma das equipas que jogou!");
    }

    Set<Long> j1 = j.getS1().getJogadoresIds();
    Set<Long> j2 = j.getS2().getJogadoresIds();
    Long jogador = golodto.getMarcador();
    if (jogador == null || !(j1.contains(jogador) || j2.contains(jogador))) {
      throw new AtualizarJogoException(
          "O golo só pode ser marcado por um dos jogadores que jogou!");
    }

    golodto.setJogo(j.getId());
    Golo g =
        GoloMapper.createDtoToGolo(golodto, jogadorRepository, equipaRepository, jogoRepository);
    return g;
  }

  private Cartao markCartaoFromDto(CartaoDto cartaodto, Jogo j) throws AtualizarJogoException {
    if (cartaodto == null) {
      return null;
    }
    if (cartaodto.getCor() == null) {
      throw new AtualizarJogoException("O cartao tem de ter uma cor.");
    }
    LocalDateTime start = j.getDiaEHora();
    LocalDateTime end = start.plusHours(2);
    LocalDateTime marcado = cartaodto.getQuando();
    if (marcado == null || !(marcado.isBefore(end) && marcado.isAfter(start))) {
      throw new AtualizarJogoException("O cartão só pode ser atribuido durante o periodo de jogo!");
    }
    cartaodto.setJogo(j.getId());
    Cartao c =
        CartaoMapper.createDtoToCartao(
            cartaodto, jogadorRepository, equipaRepository, jogoRepository, arbitroRepository);
    return c;
  }

  @Transactional
  public JogoDto updateJogo(long jogoId, JogoDto jogodto) {
    return null;
  }

  @Transactional
  public List<JogadorDto> buscarJogadoresDeSelecao(Long id) throws SelecaoException {
    Optional<Selecao> s = selecaoRepository.findById(id);
    if (s.isEmpty()) {
      throw new SelecaoException("Nao existe selecao com esse ID.");
    }
    return s.get().getJogadores().values().stream().map(JogadorMapper::jogadorToDto).toList();
  }

  @Transactional
  public Set<JogoDto> buscarJogos() {
    return jogoRepository.findAll().stream()
        .map(
            jogo -> {
              JogoDto jogoDto = JogoMapper.jogoToDto(jogo);
              EstatisticaJogoDto stat =
                  EstatisticaMapper.estatisticaJogoToDto(
                      estatisticasHandler.criarEstatisticaJogo(jogoDto));
              jogoDto.setStats(stat);
              return jogoDto;
            })
        .collect(Collectors.toSet());
  }

  public Set<JogoDto> filtrarJogos(String jogosEstado, String golos, String local, String turno) {
    if (jogosEstado == null) jogosEstado = "";
    if (golos == null) golos = "";
    if (local == null) local = "";
    if (turno == null) turno = "";

    QJogo jogo = QJogo.jogo;
    JPAQuery<Jogo> query = new JPAQuery<>(entityManager);

    FetchableQueryBase<Jogo, JPAQuery<Jogo>> fetchable = 
        (FetchableQueryBase<Jogo, JPAQuery<Jogo>>) query.from(jogo)
            .where(
              filtroJogoEstado(jogo.estadoDeJogo, jogosEstado),
              jogo.local.nome.containsIgnoreCase(local),
              filtroJogoTurno(jogo.diaEHora, turno)
            )
            .distinct()
            .orderBy(jogo.id.asc());

    List<Jogo> jogos = fetchable.fetch();

    Set<JogoDto> jogoDtos = jogos.stream().map(JogoMapper::jogoToDto).collect(Collectors.toSet());
    Set<JogoDto> jogosFinaisDtos = new HashSet<>();
    for (JogoDto jogoDto : jogoDtos) {
        EstatisticaJogo estatisticas = estatisticasHandler.criarEstatisticaJogo(jogoDto);
        if (filtroEstatisticas(estatisticas, golos)) {
            jogoDto.setStats(EstatisticaMapper.estatisticaJogoToDto(estatisticas));
            jogosFinaisDtos.add(jogoDto);
        }
    }
    return jogosFinaisDtos;
  }

  private BooleanExpression filtroJogoEstado(EnumPath<EstadoDeJogo> estado, String jogosEstado) {
    if (jogosEstado.isEmpty()) return Expressions.asBoolean(true).isTrue();
    return estado.eq(Enum.valueOf(EstadoDeJogo.class, jogosEstado));
  }

  private BooleanExpression filtroJogoTurno(DateTimePath<LocalDateTime> data, String turno) {
    if (turno.isEmpty()) return Expressions.asBoolean(true).isTrue();
    switch (turno) {
      case "MANHA" -> {return data.hour().between(6, 11);}
      case "TARDE" -> {return data.hour().between(12, 19);}
      case "NOITE" -> {return data.hour().between(20, 23).or(data.hour().between(0, 5));}
    }
    return Expressions.asBoolean(true).isTrue();
  }

  private boolean filtroEstatisticas(EstatisticaJogo estatisticas, String golosStr) {
    if (golosStr.matches("\\d+")) {
      int golos = Integer.parseInt(golosStr);
      if (estatisticas.getGolos().size() != golos) return false;
    }
    return true;
  }
}
