package pt.ul.fc.css.soccernow.entities.equipas;

import java.util.List;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

/** Interface representing a futsal team. */
public interface IEquipa {

  /** Sets the Id of the team */
  public void setId(Long id);

  /**
   * Gets the ID of the team.
   *
   * @return the ID of the team.
   */
  public Long getId();

  /**
   * Gets the name of the team.
   *
   * @return the name of the team.
   */
  public String getNome();

  /**
   * Gets the list of games associated with the team game history.
   *
   * @return a list of games.
   */
  public List<Jogo> getHistoricoDeJogos();

  /**
   * Gets a specific game by its ID.
   *
   * @param idJogo the ID of the game.
   * @return the game with the specified ID.
   */
  public Jogo getJogo(Long idJogo);

  /**
   * Adds a game to the team's game history.
   *
   * @param jogo the game to add.
   */
  public void addJogo(Jogo jogo);

  /**
   * Adds multiple games to the team's game history. getHistoricoDeJogos will have the old jogos and
   * the new jogos that are given here
   *
   * @param jogos the list of games to add.
   */
  public void addJogos(List<Jogo> jogos);

  /**
   * Sets historico de jogo getHistoricoDeJogos will output this jogos
   *
   * <p>* @param jogos the Historico of the equipa.
   */
  public void setHistoricoDeJogos(List<Jogo> jogos);

  /**
   * Gets the list of the team's players.
   *
   * @return a list of players.
   */
  public List<Jogador> getJogadores();

  /**
   * Gets a specific player by their ID.
   *
   * @param id the ID of the player.
   * @return the player with the specified ID.
   */
  public Jogador getJogador(Long id);

  /**
   * Adds a player to the team's roster.
   *
   * @param jogador the player to add.
   */
  public void addJogador(Jogador jogador);

  /**
   * Adds a list of players to the team's roster.
   *
   * @param List of jogador the player to add.
   */
  public void addJogadores(List<Jogador> jogadores);

  /**
   * Removes a player from the team's roster.
   *
   * @param jogador the player to remove.
   */
  public void removeJogador(Jogador jogador);

  /**
   * Removes a game from the team by its ID.
   *
   * <p><b>Warning:</b> This method should only be used in very special cases.
   *
   * @param idJogo the ID of the game to remove.
   */
  public void removeJogo(Long idJogo);

  /**
   * Sets the name of the team.
   *
   * <p><b>Warning:</b> This method should only be used in very special cases.
   *
   * @param nome the new name of the team.
   */
  public void setNome(String nome);
}
