package pt.ul.fc.css.soccernow.filtros;

public class GoloThymeleafDto {

  private int horas;
  private int minutos;
  private Long jogo;
  private Long marcador;
  private String marcadorNome;
  private Long equipa;
  private String equipaNome;

  public GoloThymeleafDto() {}

  public GoloThymeleafDto(int horas, int minutos, Long jogo, Long marcador, Long equipa) {
    this.horas = horas;
    this.minutos = minutos;
    this.jogo = jogo;
    this.marcador = marcador;
    this.equipa = equipa;
  }

  public Long getJogo() {
    return jogo;
  }

  public void setJogo(Long jogo) {
    this.jogo = jogo;
  }

  public Long getMarcador() {
    return marcador;
  }

  public void setMarcador(Long marcador) {
    this.marcador = marcador;
  }

  public Long getEquipa() {
    return equipa;
  }

  public void setEquipa(Long equipa) {
    this.equipa = equipa;
  }

  @Override
  public String toString() {
    return "GoloDto [jogo="
        + jogo
        + ", marcador="
        + marcador
        + ", equipa="
        + equipa
        + "]";
  }

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

  public String getMarcadorNome() {
    return marcadorNome;
  }

  public void setMarcadorNome(String marcadorNome) {
    this.marcadorNome = marcadorNome;
  }

  public String getEquipaNome() {
    return equipaNome;
  }

  public void setEquipaNome(String equipaNome) {
    this.equipaNome = equipaNome;
  }
}
