package pt.ul.fc.css.soccernow.filtros;

/**
 * Data Transfer Object (DTO) representing a team. This class is used to transfer data related to a
 * team between layers.
 */
public class EquipaThymeleafDto {
  private Long id;
  private String nome;

  public EquipaThymeleafDto() {}

  public EquipaThymeleafDto(Long id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  /**
   * Gets the ID of the team.
   *
   * @return the ID of the team.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the team.
   *
   * @param id the ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the team.
   *
   * @return the name of the team.
   */
  public String getNome() {
    return nome;
  }

  /**
   * Sets the name of the team.
   *
   * @param nome the name to set.
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public String toString() {
    return "EquipaDto [id="
        + id
        + ", nome="
        + nome
        + "]";
  }
}
