package pt.ul.fc.css.soccernow.handlers;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.entities.jogos.SelecaoDois;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.mappers.jogos.CartaoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.GoloMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.JogoMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
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
    Equipa e1 = equipaRepository.getReferenceById(jogodto.getS1().getEquipa());
    Jogador cap1 = jogadorRepository.getReferenceById(jogodto.getS1().getCapitao());
    Map<Posicao, Long> jogsS1 = jogodto.getS1().getJogadores();
    Map<Posicao, Jogador> jogadoresS1 = new EnumMap<>(Posicao.class);
    jogsS1.forEach((k, v) -> jogadoresS1.put(k, jogadorRepository.getReferenceById(v)));
    Selecao s1 = new Selecao(e1, cap1, jogadoresS1);

    Equipa e2 = equipaRepository.getReferenceById(jogodto.getS2().getEquipa());
    Jogador cap2 = jogadorRepository.getReferenceById(jogodto.getS2().getCapitao());
    Map<Posicao, Long> jogsS2 = jogodto.getS2().getJogadores();
    Map<Posicao, Jogador> jogadoresS2 = new EnumMap<>(Posicao.class);
    jogsS2.forEach((k, v) -> jogadoresS2.put(k, jogadorRepository.getReferenceById(v)));
    SelecaoDois s2 = new SelecaoDois(e2, cap2, jogadoresS1);

    Jogo jogo = JogoMapper.dtoToJogo(jogodto);
    jogo.setS1(s1);
    jogo.setS2(s2);
    Local local = jogo.getLocal();
    Local savedLocal = localRepository.save(local);
    Jogo savedJogo = jogoRepository.save(jogo);
    jogodto.setId(savedJogo.getId());
    return jogodto;
  }

  @Transactional
  public JogoDto updateJogo(long jogoId, JogoDto jogodto) {
    Optional<Jogo> jogoOptional = jogoRepository.findById(jogoId);
    if (jogoOptional.isEmpty()) {
      return null;
    }
    Jogo jogoGuardado = jogoOptional.get();
    Jogo updatedJogo;
    JogoDto res;
    if (!updateValido(jogoGuardado, jogodto)) {
      return null;
    }
    Jogo jogo = JogoMapper.dtoToJogo(jogodto);
    updatedJogo = jogoRepository.save(jogo);
    res = JogoMapper.jogoToDto(updatedJogo);

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
      if (stat.getGolos() != null && !stat.getGolos().isEmpty()) {
        Set<Golo> golos =
            stat.getGolos().stream()
                .map(
                    g -> {
                      Golo golo = GoloMapper.dtoToGolo(g);
                      golo.setMarcador(jogadorRepository.getReferenceById(g.getMarcador()));
                      golo.setEquipa(equipaRepository.getReferenceById(g.getEquipa()));
                      golo.setJogo(jogo);
                      return golo;
                    })
                .collect(Collectors.toSet());
        goloRepository.saveAll(golos);
      }
      EstatisticaJogoDto updatedStats =
          EstatisticaMapper.estatisticaJogoToDto(estatisticasHandler.criarEstatisticaJogo(res));
      res.setStats(updatedStats);
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
    return jogoRepository.findAll().stream().map(jogo -> {
      JogoDto jogoDto = JogoMapper.jogoToDto(jogo);
      EstatisticaJogoDto stat = EstatisticaMapper.estatisticaJogoToDto(estatisticasHandler.criarEstatisticaJogo(jogoDto));
      jogoDto.setStats(stat);
      return jogoDto;
    }).collect(Collectors.toSet());
  }
}
