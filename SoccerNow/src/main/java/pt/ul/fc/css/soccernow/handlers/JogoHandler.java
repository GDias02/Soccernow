package pt.ul.fc.css.soccernow.handlers;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaMapper;
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

  public JogoDto getJogo(long id) {
    Optional<Jogo> jogoOptional = jogoRepository.findById(id);
    if (jogoOptional.isEmpty()) {
      return null;
    }
    return JogoMapper.jogoToDto(jogoOptional.get());
  }

  public JogoDto saveJogo(Jogo jogo) {
    return JogoMapper.jogoToDto(jogoRepository.save(jogo));
  }

  public JogoDto createJogo(JogoDto jogodto) {
    Equipa e1 = equipaRepository.findById(jogodto.getS1().getEquipa()).get();
    Jogador cap1 = jogadorRepository.findById(jogodto.getS1().getCapitao()).get();
    Map<Posicao, Long> jogsS1 = jogodto.getS1().getJogadores();
    Map<Posicao, Jogador> jogadoresS1 = new EnumMap<>(Posicao.class);
    jogsS1.forEach((k, v) -> jogadoresS1.put(k, jogadorRepository.findById(v).get()));
    Selecao s1 = new Selecao(e1, cap1, jogadoresS1);

    Equipa e2 = equipaRepository.findById(jogodto.getS2().getEquipa()).get();
    Jogador cap2 = jogadorRepository.findById(jogodto.getS2().getCapitao()).get();
    Map<Posicao, Long> jogsS2 = jogodto.getS2().getJogadores();
    Map<Posicao, Jogador> jogadoresS2 = new EnumMap<>(Posicao.class);
    jogsS2.forEach((k, v) -> jogadoresS2.put(k, jogadorRepository.findById(v).get()));
    Selecao s2 = new Selecao(e2, cap2, jogadoresS2);

    Jogo jogo = JogoMapper.dtoToJogo(jogodto);
    jogo.setS1(s1);
    jogo.setS2(s2);
    Local local = jogo.getLocal();
    Local savedLocal = localRepository.save(local);
    Jogo savedJogo = jogoRepository.save(jogo);
    jogodto.setId(savedJogo.getId());
    return jogodto;
  }

  public JogoDto updateJogo(long jogoId, JogoDto jogodto) {
    Optional<Jogo> jogoOpt = jogoRepository.findById(jogoId);
    Jogo updatedJogo;
    if (jogoOpt.isEmpty()) {
      return null;
    } else {
      updatedJogo = jogoOpt.get();
    }
    if (updateValido(updatedJogo, jogodto)) {
      EstatisticaJogo updatedStats = EstatisticaMapper.dtoToEstatisticaJogo(jogodto.getStats());
      JogoMapper.dtoToJogo(jogodto, updatedJogo);
      goloRepository.saveAll(updatedStats.getGolos());
      cartaoRepository.saveAll(updatedStats.getCartoes());
      jogoRepository.save(updatedJogo);
    }
    return JogoMapper.jogoToDto(updatedJogo);
  }

  // PERMITE DEMASIADAS COISAS
  private boolean updateValido(Jogo updatedJogo, JogoDto jogodto) {
    return (updatedJogo.getEstadoDeJogo() != EstadoDeJogo.TERMINADO);
    // permite o registo final de um jogo.
  }
}
