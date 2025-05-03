package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.EnumMap;
import java.util.Map;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

public class SelecaoDois {
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "equipa2", insertable = false, updatable = false)
  private Equipa equipa;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "capitao2", insertable = false, updatable = false)
  private Jogador capitao;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guardaRedes2", insertable = false, updatable = false)
  private Jogador guardaRedes;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fixo2", insertable = false, updatable = false)
  private Jogador fixo;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "alaEsquerda2", insertable = false, updatable = false)
  private Jogador alaEsquerda;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "alaDireita2", insertable = false, updatable = false)
  private Jogador alaDireita;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pivot2", insertable = false, updatable = false)
  private Jogador pivot;

  public SelecaoDois(Equipa e, Jogador capitao, Map<Posicao, Jogador> jogadores) {
    this.equipa = e;
    this.capitao = capitao;
    setJogadores(jogadores);
  }

  public SelecaoDois() {}

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
