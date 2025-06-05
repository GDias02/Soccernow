package pt.ul.fc.css.soccernow.filtros;

import pt.ul.fc.css.soccernow.entities.jogos.Cor;

public class CartaoThymeleafDto {
  private int horas;
  private int minutos;
  private Long jogo;
  private Long atribuidoA;
  private String atribuidoANome;
  private Long arbitro;
  private String arbitroNome;
  private Long equipa;
  private String equipaNome;
  private Cor cor;

  public CartaoThymeleafDto(
      int horas, int minutos, Long jogo, Long atribuidoA, Long arbitro, Long equipa, Cor cor) {
    this.horas = horas;
    this.minutos = minutos;
    this.jogo = jogo;
    this.atribuidoA = atribuidoA;
    this.arbitro = arbitro;
    this.equipa = equipa;
    this.cor = cor;
  }

  public CartaoThymeleafDto() {}

  public int getHoras() {
    return horas;
  }

  public void setHoras(int horas) {
    this.horas = horas;
  }

  public int getMinutos() {
    return minutos;
  }

  public void setMinutos(int minutos) {
    this.minutos = minutos;
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
    return "CartaoDto [horas="
        + horas
        + ", minutos="
        + minutos
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

  public String getAtribuidoANome() {
    return atribuidoANome;
  }

  public void setAtribuidoANome(String atribuidoANome) {
    this.atribuidoANome = atribuidoANome;
  }

  public String getArbitroNome() {
    return arbitroNome;
  }

  public void setArbitroNome(String arbitroNome) {
    this.arbitroNome = arbitroNome;
  }

  public String getEquipaNome() {
    return equipaNome;
  }

  public void setEquipaNome(String equipaNome) {
    this.equipaNome = equipaNome;
  }
}
