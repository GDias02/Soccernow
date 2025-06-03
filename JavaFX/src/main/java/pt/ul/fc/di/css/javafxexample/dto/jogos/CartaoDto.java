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
}
