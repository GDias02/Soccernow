package pt.ul.fc.di.css.javafxexample.dto.jogos;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;

/**
 * Classe Dto para representar uma selecao de uma equipa para determinado jogo. Para: - Criar um
 * novo jogo: - - O atributo de tipo EquipaDto requer apenas com o id e o nome preenchido (para
 * efeitos do placar). - - O parametro de capitao e opcional - - Os atributos de tipo JogadorDto
 * requerem apenas o id - - Os atributos das posicoes dos jogadores podem ser colocados recorrendo a
 * setSelecao(Map<Posicao,JogadorDto>).
 */
public class SelecaoDto {
  private Long equipa;
  private Long capitao;
  private Long guardaRedes;
  private Long fixo;
  private Long alaEsquerda;
  private Long alaDireita;
  private Long pivot;

  public Long getEquipa() {
    return equipa;
  }

  public void setEquipa(Long equipa) {
    this.equipa = equipa;
  }

  public Long getCapitao() {
    return capitao;
  }

  public void setCapitao(Long capitao) {
    this.capitao = capitao;
  }

  public Long getGuardaRedes() {
    return guardaRedes;
  }

  public void setGuardaRedes(Long guardaRedes) {
    this.guardaRedes = guardaRedes;
  }

  public Long getFixo() {
    return fixo;
  }

  public void setFixo(Long fixo) {
    this.fixo = fixo;
  }

  public Long getAlaEsquerda() {
    return alaEsquerda;
  }

  public void setAlaEsquerda(Long alaEsquerda) {
    this.alaEsquerda = alaEsquerda;
  }

  public Long getAlaDireita() {
    return alaDireita;
  }

  public void setAlaDireita(Long alaDireita) {
    this.alaDireita = alaDireita;
  }

  public Long getPivot() {
    return pivot;
  }

  public void setPivot(Long pivot) {
    this.pivot = pivot;
  }

  public void setJogadores(Map<Posicao, Long> jogadores) {
    this.guardaRedes = jogadores.get(Posicao.GUARDA_REDES);
    this.fixo = jogadores.get(Posicao.FIXO);
    this.alaEsquerda = jogadores.get(Posicao.ALA_ESQUERDA);
    this.alaDireita = jogadores.get(Posicao.ALA_DIREITA);
    this.pivot = jogadores.get(Posicao.PIVO);
  }

  public Map<Posicao, Long> getJogadores() {
    Map<Posicao, Long> jogadores = new EnumMap<>(Posicao.class);
    jogadores.put(Posicao.GUARDA_REDES, guardaRedes);
    jogadores.put(Posicao.FIXO, fixo);
    jogadores.put(Posicao.ALA_ESQUERDA, alaEsquerda);
    jogadores.put(Posicao.ALA_DIREITA, alaDireita);
    jogadores.put(Posicao.PIVO, pivot);
    return jogadores;
  }

  public Set<Long> getJogadoresIds() {
    Set<Long> jogadores = new HashSet<>();
    jogadores.add(guardaRedes);
    jogadores.add(fixo);
    jogadores.add(alaEsquerda);
    jogadores.add(alaDireita);
    jogadores.add(pivot);
    return jogadores;
  }

  @Override
  public String toString() {
    return "SelecaoDto [equipa="
        + equipa
        + ", capitao="
        + capitao
        + ", guardaRedes="
        + guardaRedes
        + ", fixo="
        + fixo
        + ", alaEsquerda="
        + alaEsquerda
        + ", alaDireita="
        + alaDireita
        + ", pivot="
        + pivot
        + "]";
  }
}
