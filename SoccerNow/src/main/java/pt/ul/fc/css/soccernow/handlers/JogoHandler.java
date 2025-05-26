package pt.ul.fc.css.soccernow.handlers;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.Placar;
import pt.ul.fc.css.soccernow.mappers.jogos.CartaoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.GoloMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.JogoMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repositories.CartaoRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.GoloRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;
import pt.ul.fc.css.soccernow.repositories.LocalRepository;

@Service
public class JogoHandler implements IJogoHandler {

  @Autowired private JogoRepository jogoRepository;

  @Autowired private GoloRepository goloRepository;

  @Autowired private LocalRepository localRepository;

  @Autowired private CartaoRepository cartaoRepository;

  @Autowired private EquipaRepository equipaRepository;

  @Autowired private JogadorRepository jogadorRepository;

  @Autowired private ArbitroRepository arbitroRepository;

  @Autowired private CampeonatoRepository campeonatoRepository;

  @Autowired private EstatisticasHandler estatisticasHandler;

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
  public JogoDto createJogo(JogoDto jogodto) {
    Jogo jogo =
        JogoMapper.createDtoToJogo(
            jogodto, equipaRepository, jogadorRepository, arbitroRepository, campeonatoRepository);

    Local local = jogo.getLocal();
    Local savedLocal = localRepository.save(local);
    jogo.setLocal(savedLocal);
    Jogo savedJogo = jogoRepository.save(jogo);
    jogodto.setId(savedJogo.getId());
    return jogodto;
  }

  @Transactional
  public JogoDto registarResultadoDeJogo(JogoDto jogodto) {
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
      return null;
    }
    Jogo jogo = jogoOptional.get();

    if (jogo.getEstadoDeJogo() == EstadoDeJogo.TERMINADO) {
      return null;
    } // Nao se pode registar resultados após o jogo ter terminado
    if (jogodto.getEstadoDeJogo() != EstadoDeJogo.TERMINADO) {
      return null;
    } // Pressupoe que este metodo só regista golos e cartoes após ter terminado

    EstatisticaJogoDto ej = jogodto.getStats();
    Set<Golo> golosMarcados =
        ej.getGolos().stream()
            .map(
                g ->
                    GoloMapper.createDtoToGolo(
                        g, jogadorRepository, equipaRepository, jogoRepository))
            .collect(Collectors.toSet());
    Set<Cartao> cartoesMarcados =
        ej.getCartoes().stream()
            .map(
                g ->
                    CartaoMapper.createDtoToCartao(
                        g, jogadorRepository, equipaRepository, jogoRepository, arbitroRepository))
            .collect(Collectors.toSet());
    goloRepository.saveAll(golosMarcados);
    cartaoRepository.saveAll(cartoesMarcados);
    EstatisticaJogo stat = new EstatisticaJogo();
    stat.setCartoes(cartoesMarcados);
    stat.setGolos(golosMarcados);

    stat.setJogo(jogo);
    jogo.setPlacar(stat.getPlacar());
    Jogo savedJogo = jogoRepository.save(jogo);
    return JogoMapper.jogoToDto(savedJogo);
  }

  @Transactional
  public JogoDto updateJogo(long jogoId, JogoDto jogodto) {
    Optional<Jogo> jogoOptional = jogoRepository.findById(jogoId);
    if (jogoOptional.isEmpty()) {
      return null;
    }
    Jogo jogoGuardado = jogoOptional.get();
    Jogo updatedJogo;
    JogoDto res = null;
    if (!updateValido(jogoGuardado, jogodto)) {
      return null;
    }
    Jogo jogo = JogoMapper.dtoToJogo(jogodto);

    EstatisticaJogoDto stat = jogodto.getStats();
    if (stat != null) {
      if (stat.getCartoes() != null && !stat.getCartoes().isEmpty()) {
        Set<Cartao> cartoes =
            stat.getCartoes().stream()
                .map(
                    c -> {
                      Cartao cartao = CartaoMapper.dtoToCartao(c);
                      cartao.setArbitro(arbitroRepository.getReferenceById(c.getArbitro()));
                      cartao.setAtribuidoA(jogadorRepository.getReferenceById(c.getAtribuidoA()));
                      cartao.setEquipa(equipaRepository.getReferenceById(c.getEquipa()));
                      cartao.setJogo(jogo);
                      return cartao;
                    })
                .collect(Collectors.toSet());
        cartaoRepository.saveAll(cartoes);
      }
      Placar p = new Placar();
      Long e1 = jogodto.getS1().getEquipa();
      int golosE1 = 0;
      int golosE2 = 0;
      if (stat.getGolos() != null && !stat.getGolos().isEmpty()) {
        Set<Golo> golos =
            stat.getGolos().stream()
                .map(
                    g -> {
                      Golo golo = GoloMapper.dtoToGolo(g);
                      golo.setMarcador(jogadorRepository.getReferenceById(g.getMarcador()));
                      golo.setEquipa(equipaRepository.getReferenceById(g.getEquipa()));
                      golo.setJogo(jogoRepository.getReferenceById(jogoId));
                      return golo;
                    })
                .collect(Collectors.toSet());
        for (Golo g : golos) {
          if (g.getEquipa().getId().equals(e1)) {
            golosE1++;
          } else {
            golosE2++;
          }
        }
        goloRepository.saveAll(golos);
        p.setScore(golosE1, golosE2);
        jogo.setPlacar(p);
      }
      updatedJogo = jogoRepository.save(jogo);
      /*
      res = JogoMapper.jogoToDto(updatedJogo);
      EstatisticaJogoDto updatedStats =
          EstatisticaMapper.estatisticaJogoToDto(estatisticasHandler.criarEstatisticaJogo(res));
      res.setStats(updatedStats);
      res.setEquipaVencedora(updatedJogo.getEquipaVencedora().getId());
      */
    }
    return res;
  }

  // PERMITE DEMASIADAS COISAS
  private boolean updateValido(Jogo updatedJogo, JogoDto jogodto) {
    return (updatedJogo.getEstadoDeJogo() != EstadoDeJogo.TERMINADO);
    // permite o registo final de um jogo.
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
}
