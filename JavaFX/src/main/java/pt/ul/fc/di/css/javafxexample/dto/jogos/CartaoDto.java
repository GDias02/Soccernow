package pt.ul.fc.di.css.javafxexample.dto.jogos;

import java.time.LocalDateTime;

public class CartaoDto {
  private LocalDateTime quando;
  private Long jogo;
  private Long atribuidoA;
  private Long arbitro;
  private Long equipa;
  private Cor cor;

  public CartaoDto(
      LocalDateTime quando, Long jogo, Long atribuidoA, Long arbitro, Long equipa, Cor cor) {
    this.quando = quando;
    this.jogo = jogo;
    this.atribuidoA = atribuidoA;
    this.arbitro = arbitro;
    this.equipa = equipa;
    this.cor = cor;
  }

  public CartaoDto() {}

  public LocalDateTime getQuando() {
    return quando;
  }

  public void setQuando(LocalDateTime quando) {
    this.quando = quando;
  }

  public Cor getCor() {
    return cor;
  }

  public void setCor(Cor cor) {
    this.cor = cor;
  }

  public Long getJogo() {
    return jogo;
  }

  public void setJogo(Long jogo) {
    this.jogo = jogo;
  }

  public Long getAtribuidoA() {
    return atribuidoA;
  }

  public void setAtribuidoA(Long atribuidoA) {
    this.atribuidoA = atribuidoA;
  }

  public Long getArbitro() {
    return arbitro;
  }

  public void setArbitro(Long arbitro) {
    this.arbitro = arbitro;
  }

  public Long getEquipa() {
    return equipa;
  }

  public void setEquipa(Long equipa) {
    this.equipa = equipa;
  }

  @Override
  public String toString() {
    return "CartaoDto [quando="
        + quando
        + ", jogo="
        + jogo
        + ", atribuidoA="
        + atribuidoA
        + ", arbitro="
        + arbitro
        + ", equipa="
        + equipa
        + ", cor="
        + cor
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((quando == null) ? 0 : quando.hashCode());
    result = prime * result + ((jogo == null) ? 0 : jogo.hashCode());
    result = prime * result + ((atribuidoA == null) ? 0 : atribuidoA.hashCode());
    result = prime * result + ((arbitro == null) ? 0 : arbitro.hashCode());
    result = prime * result + ((equipa == null) ? 0 : equipa.hashCode());
    result = prime * result + ((cor == null) ? 0 : cor.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    CartaoDto other = (CartaoDto) obj;
    if (quando == null) {
      if (other.quando != null) return false;
    } else if (!quando.equals(other.quando)) return false;
    if (jogo == null) {
      if (other.jogo != null) return false;
    } else if (!jogo.equals(other.jogo)) return false;
    if (atribuidoA == null) {
      if (other.atribuidoA != null) return false;
    } else if (!atribuidoA.equals(other.atribuidoA)) return false;
    if (arbitro == null) {
      if (other.arbitro != null) return false;
    } else if (!arbitro.equals(other.arbitro)) return false;
    if (equipa == null) {
      if (other.equipa != null) return false;
    } else if (!equipa.equals(other.equipa)) return false;
    if (cor != other.cor) return false;
    return true;
  }
}
