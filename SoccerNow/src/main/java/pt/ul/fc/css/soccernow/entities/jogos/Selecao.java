package pt.ul.fc.css.soccernow.entities.jogos;

import java.util.EnumMap;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

@Entity
public class Selecao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "jogo_id", nullable = false)
  private Jogo jogo;

  @ManyToOne
  @JoinColumn(name = "equipa1")
  private Equipa equipa;

  @ManyToOne
  @JoinColumn(name = "capitao1")
  private Jogador capitao;

  @ManyToOne
  @JoinColumn(name = "guardaRedes1")
  private Jogador guardaRedes;

  @ManyToOne
  @JoinColumn(name = "fixo1")
  private Jogador fixo;

  @ManyToOne
  @JoinColumn(name = "alaEsquerda1")
  private Jogador alaEsquerda;

  @ManyToOne
  @JoinColumn(name = "alaDireita1")
  private Jogador alaDireita;

  @ManyToOne
  @JoinColumn(name = "pivot1")
  private Jogador pivot;

  public Selecao(Equipa e, Jogador capitao, Map<Posicao, Jogador> jogadores) {
    this.equipa = e;
    this.capitao = capitao;
    setJogadores(jogadores);
  }

  public Selecao() {
    // TODO Auto-generated constructor stub
  }

  public Jogo getJogo() {
    return jogo;
  }

  public void setJogo(Jogo jogo) {
    this.jogo = jogo;
  }

  public Map<Posicao, Jogador> getJogadores() {
    Map<Posicao, Jogador> jogadores = new EnumMap<>(Posicao.class);
    jogadores.put(Posicao.GUARDA_REDES, guardaRedes);
    jogadores.put(Posicao.FIXO, fixo);
    jogadores.put(Posicao.ALA_ESQUERDA, alaEsquerda);
    jogadores.put(Posicao.ALA_DIREITA, alaDireita);
    jogadores.put(Posicao.PIVO, pivot);
    return jogadores;
  }

  public void setJogadores(
      Map<Posicao, Jogador>
          jogadores) { // TODO - trocar o "posicoes[0]" pelo nome real de cada posicao.
    this.guardaRedes = jogadores.get(Posicao.GUARDA_REDES);
    this.fixo = jogadores.get(Posicao.FIXO);
    this.alaEsquerda = jogadores.get(Posicao.ALA_ESQUERDA);
    this.alaDireita = jogadores.get(Posicao.ALA_DIREITA);
    this.pivot = jogadores.get(Posicao.PIVO);
  }

  public Equipa getEquipa() {
    return equipa;
  }

  public void setEquipa(Equipa equipa) {
    this.equipa = equipa;
  }

  public Jogador getCapitao() {
    return capitao;
  }

  public void setCapitao(Jogador capitao) {
    this.capitao = capitao;
  }

  public Jogador getGuardaRedes() {
    return guardaRedes;
  }

  public void setGuardaRedes(Jogador guardaRedes) {
    this.guardaRedes = guardaRedes;
  }

  public Jogador getFixo() {
    return fixo;
  }

  public void setFixo(Jogador fixo) {
    this.fixo = fixo;
  }

  public Jogador getAlaEsquerda() {
    return alaEsquerda;
  }

  public void setAlaEsquerda(Jogador alaEsquerda) {
    this.alaEsquerda = alaEsquerda;
  }

  public Jogador getAlaDireita() {
    return alaDireita;
  }

  public void setAlaDireita(Jogador alaDireita) {
    this.alaDireita = alaDireita;
  }

  public Jogador getPivot() {
    return pivot;
  }

  public void setPivot(Jogador pivot) {
    this.pivot = pivot;
  }
}
