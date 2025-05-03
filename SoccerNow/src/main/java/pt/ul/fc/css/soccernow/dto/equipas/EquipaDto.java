package pt.ul.fc.css.soccernow.dto.equipas;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a team. This class is used to transfer data related to a
 * team between layers.
 */
public class EquipaDto {
  private Long id;
  private String nome;
  private List<Long> historicoDeJogos;
  private List<Long> jogadores;

  public EquipaDto() {}

  public EquipaDto(Long id, String nome, List<Long> jogadores) {
    this.id = id;
    this.nome = nome;
    this.jogadores = jogadores;
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

  /**
   * Gets the history of games played by the team.
   *
   * @return a list of game IDs representing the team's game history.
   */
  public List<Long> getHistoricoDeJogos() {
    return historicoDeJogos;
  }

  /**
   * Sets the history of games played by the team.
   *
   * @param historicoDeJogos a list of game IDs to set as the team's game history.
   */
  public void setHistoricoDeJogos(List<Long> historicoDeJogos) {
    this.historicoDeJogos = historicoDeJogos;
  }

  /**
   * Adds a game to the history of games played by the team.
   *
   * @param jogo a Long of a game's ID
   */
  public void addJogo(Long jogo) {
    this.historicoDeJogos.add(jogo);
  }

  /**
   * Gets the list of players in the team.
   *
   * @return a list of player IDs representing the players in the team.
   */
  public List<Long> getJogadores() {
    return jogadores;
  }

  /**
   * Sets the list of players in the team.
   *
   * @param jogadores a list of player IDs to set as the team's players.
   */
  public void setJogadores(List<Long> jogadores) {
    this.jogadores = jogadores;
  }

  /**
   * Add Jogador to the list of players in the team.
   *
   * @param jogador a Long of a player's ID.
   */
  public void addJogador(Long jogador) {
    this.jogadores.add(jogador);
  }

  @Override
  public String toString() {
    return "EquipaDto [id="
        + id
        + ", nome="
        + nome
        + ", historicoDeJogos="
        + historicoDeJogos
        + ", jogadores="
        + jogadores
        + "]";
  }
}
