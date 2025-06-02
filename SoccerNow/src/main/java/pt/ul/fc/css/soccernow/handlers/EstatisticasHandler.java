package pt.ul.fc.css.soccernow.handlers;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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
import pt.ul.fc.css.soccernow.repositories.JogoRepository;
import pt.ul.fc.css.soccernow.repositories.SelecaoRepository;

@Service
public class EstatisticasHandler implements IEstatisticasHandler {

  @Autowired private GoloRepository goloRepository;
  @Autowired private CartaoRepository cartaoRepository;
  @Autowired private SelecaoRepository selecaoRepository;
  @Autowired private JogoRepository jogoRepository;

  public EstatisticaJogo criarEstatisticaJogo(JogoDto jogo) {
    Long id = jogo.getId();

    Optional<Set<Golo>> maybeGolos = goloRepository.findByJogo_Id(id);
    Set<Golo> golosMarcados = null;
    if (!maybeGolos.isEmpty()) golosMarcados = maybeGolos.get();

    Optional<Set<Cartao>> maybeCartoes = cartaoRepository.findByJogo_Id(id);
    Set<Cartao> cartoesRecebidos = null;
    if (!maybeCartoes.isEmpty()) cartoesRecebidos = maybeCartoes.get();

    EstatisticaJogo ej = new EstatisticaJogo();
    ej.setCartoes(cartoesRecebidos);
    ej.setGolos(golosMarcados);
    return ej;
  }

  @Transactional
  public EstatisticaJogador criarEstatisticaJogador(JogadorDto jogador) {
    Long id = jogador.getUtilizador().getId();

    Optional<Set<Golo>> maybeGolos = goloRepository.findByMarcador_Id(id);
    Set<Golo> golosMarcados = null;
    if (!maybeGolos.isEmpty()) golosMarcados = maybeGolos.get();

    Optional<Set<Cartao>> maybeCartoes = cartaoRepository.findByAtribuidoA_Id(id);
    Set<Cartao> cartoesRecebidos = null;
    if (!maybeCartoes.isEmpty()) cartoesRecebidos = maybeCartoes.get();

    int jogos = selecaoRepository.selecoesComJogador(jogador.getUtilizador().getId());

    EstatisticaJogador ej = new EstatisticaJogador();
    ej.setCartoes(cartoesRecebidos);
    ej.setGolos(golosMarcados);
    ej.setJogos(jogos);

    return ej;
  }

  @Transactional
  public void removerEstatisticaJogador(JogadorDto jogador) {
    Long id = jogador.getUtilizador().getId();

    Optional<Set<Golo>> maybeGolos = goloRepository.findByMarcador_Id(id);
    Set<Golo> golosMarcados;
    if (!maybeGolos.isEmpty()) {
      golosMarcados = maybeGolos.get();
      goloRepository.deleteAll(golosMarcados);
    }

    Optional<Set<Cartao>> maybeCartoes = cartaoRepository.findByAtribuidoA_Id(id);
    Set<Cartao> cartoesRecebidos;
    if (!maybeCartoes.isEmpty()) {
      cartoesRecebidos = maybeCartoes.get();
      cartaoRepository.deleteAll(cartoesRecebidos);
    }
  }

  @Transactional
  public EstatisticaArbitro criarEstatisticaArbitro(ArbitroDto arbitro) {
    Long id = arbitro.getUtilizador().getId();

    Optional<Set<Cartao>> maybeCartoes = cartaoRepository.findByArbitro_Id(id);
    Set<Cartao> cartoesAtribuidos = null;
    if (!maybeCartoes.isEmpty()) cartoesAtribuidos = maybeCartoes.get();

    int jogos = jogoRepository.jogosArbitro(id);

    EstatisticaArbitro ej = new EstatisticaArbitro();
    ej.setCartoes(cartoesAtribuidos);
    ej.setGolos(null);
    ej.setJogos(jogos);
    return ej;
  }

  @Transactional
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

  @Transactional
  public void updateEstatisticaJogador(JogadorDto jogador) {
    Set<Golo> golosMarcados =
        jogador.getEstatisticas().getGolos().stream().map(GoloMapper::dtoToGolo).collect(Collectors.toSet());
    Set<Cartao> cartoesAtribuidos =
        jogador.getEstatisticas().getCartoes().stream()
            .map(CartaoMapper::dtoToCartao)
            .collect(Collectors.toSet());
    goloRepository.saveAll(golosMarcados);
    cartaoRepository.saveAll(cartoesAtribuidos);
  }
}
