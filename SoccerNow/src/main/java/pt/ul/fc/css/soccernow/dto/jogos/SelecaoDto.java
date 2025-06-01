package pt.ul.fc.css.soccernow.dto.jogos;

import java.util.EnumMap;
import java.util.Map;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

/**
 * Classe Dto para representar uma selecao de uma equipa para determinado jogo. Para: - Criar um
 * novo jogo: - - O atributo de tipo EquipaDto requer apenas com o id e o nome preenchido (para
 * efeitos do placar). - - O parametro de capitao e opcional - - Os atributos de tipo JogadorDto
 * requerem apenas o id - - Os atributos das posicoes dos jogadores podem ser colocados recorrendo a
 * setSelecao(Map<Posicao,JogadorDto>).
 */
public class SelecaoDto {

  private Long id;
  private Long jogo;
  private Long equipa;
  private Long capitao;
  private Long guardaRedes;
  private Long fixo;
  private Long alaEsquerda;
  private Long alaDireita;
  private Long pivot;

  public SelecaoDto() {}

  public SelecaoDto(Long equipa, Long capitao) {
    this.equipa = equipa;
    this.capitao = capitao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getJogo() {
    return jogo;
  }

  public void setJogo(Long jogo) {
    this.jogo = jogo;
  }

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((equipa == null) ? 0 : equipa.hashCode());
    result = prime * result + ((capitao == null) ? 0 : capitao.hashCode());
    result = prime * result + ((guardaRedes == null) ? 0 : guardaRedes.hashCode());
    result = prime * result + ((fixo == null) ? 0 : fixo.hashCode());
    result = prime * result + ((alaEsquerda == null) ? 0 : alaEsquerda.hashCode());
    result = prime * result + ((alaDireita == null) ? 0 : alaDireita.hashCode());
    result = prime * result + ((pivot == null) ? 0 : pivot.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SelecaoDto other = (SelecaoDto) obj;
    if (equipa == null) {
      if (other.equipa != null) return false;
    } else if (!equipa.equals(other.equipa)) return false;
    if (capitao == null) {
      if (other.capitao != null) return false;
    } else if (!capitao.equals(other.capitao)) return false;
    if (guardaRedes == null) {
      if (other.guardaRedes != null) return false;
    } else if (!guardaRedes.equals(other.guardaRedes)) return false;
    if (fixo == null) {
      if (other.fixo != null) return false;
    } else if (!fixo.equals(other.fixo)) return false;
    if (alaEsquerda == null) {
      if (other.alaEsquerda != null) return false;
    } else if (!alaEsquerda.equals(other.alaEsquerda)) return false;
    if (alaDireita == null) {
      if (other.alaDireita != null) return false;
    } else if (!alaDireita.equals(other.alaDireita)) return false;
    if (pivot == null) {
      if (other.pivot != null) return false;
    } else if (!pivot.equals(other.pivot)) return false;
    return true;
  }
}
