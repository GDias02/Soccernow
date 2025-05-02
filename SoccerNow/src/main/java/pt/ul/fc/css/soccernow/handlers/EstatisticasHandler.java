package pt.ul.fc.css.soccernow.handlers;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaArbitro;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.mappers.jogos.CartaoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.GoloMapper;
import pt.ul.fc.css.soccernow.repositories.CartaoRepository;
import pt.ul.fc.css.soccernow.repositories.GoloRepository;

public class EstatisticasHandler implements IEstatisticasHandler {

  @Autowired private GoloRepository goloRepository;
  @Autowired private CartaoRepository cartaoRepository;

  public EstatisticaJogo criarEstatisticaJogo(JogoDto jogo) {
    Long id = jogo.getId();
    Set<Golo> golosMarcados = goloRepository.findByJogo(id);
    Set<Cartao> cartoesRecebidos = cartaoRepository.findByJogo(id);
    EstatisticaJogo ej = new EstatisticaJogo();
    ej.setCartoes(cartoesRecebidos);
    ej.setGolos(golosMarcados);
    return ej;
  }

  public EstatisticaJogador criarEstatisticaJogador(JogadorDto jogador) {
    Long id = jogador.getUtilizador().getId();
    Set<Golo> golosMarcados = goloRepository.findByMarcador(id);
    Set<Cartao> cartoesRecebidos = cartaoRepository.findByAtribuidoA(id);
    EstatisticaJogador ej = new EstatisticaJogador();
    ej.setCartoes(cartoesRecebidos);
    ej.setGolos(golosMarcados);
    return ej;
  }

  public EstatisticaArbitro criarEstatisticaArbitro(ArbitroDto arbitro) {
    Long id = arbitro.getUtilizador().getId();
    Set<Cartao> cartoesAtribuidos = cartaoRepository.findByArbitro(id);
    EstatisticaArbitro ej = new EstatisticaArbitro();
    ej.setCartoes(cartoesAtribuidos);
    ej.setGolos(null);
    return ej;
  }

  public void updateEstatisticaJogo(JogoDto jogo) {
    Set<Golo> golosMarcados =
        jogo.getStats().getGolos().stream().map(GoloMapper::dtoToGolo).collect(Collectors.toSet());
    Set<Cartao> cartoesAtribuidos =
        jogo.getStats().getCartoes().stream()
            .map(CartaoMapper::dtoToCartao)
            .collect(Collectors.toSet());
    goloRepository.saveAll(golosMarcados);
    cartaoRepository.saveAll(cartoesAtribuidos);
  }
}
