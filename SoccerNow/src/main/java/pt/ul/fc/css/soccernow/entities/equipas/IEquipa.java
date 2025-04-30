package pt.ul.fc.css.soccernow.entities.equipas;

import java.util.List;

import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;
import pt.ul.fc.css.soccernow.entities.jogos.IJogo;

/**
 * Interface representing a futsal team.
 */
public interface IEquipa {
    

    /**
     * Sets the ID of the team.
     * 
     * 
     * @param id the new ID of the team, must be valid.
     */
    public void setId(long id);

    /**
     * Gets the ID of the team.
     * 
     * @return the ID of the team.
     */
    public long getId();

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
    public List<IJogo> getHistoricoDeJogos();

    /**
     * Gets a specific game by its ID.
     * 
     * @param idJogo the ID of the game.
     * @return the game with the specified ID.
     */
    public IJogo getJogo(long idJogo);

    /**
     * Adds a game to the team's game history.
     * 
     * @param jogo the game to add.
     */
    public void addJogo(IJogo jogo);

    /**
     * Adds multiple games to the team's game history.
     * 
     * @param jogos the list of games to add.
     */
    public void addJogos(List<IJogo> jogos);

    /**
     * Gets the list of the team's players.
     * 
     * @return a list of players.
     */
    public List<IJogador> getJogadores();

    /**
     * Gets a specific player by their ID.
     * 
     * @param id the ID of the player.
     * @return the player with the specified ID.
     */
    public IJogador getJogador(long id);

    /**
     * Adds a player to the team's roster.
     * 
     * @param jogador the player to add.
     */
    public void addJogador(IJogador jogador);

    /**
     * Adds a list of players to the team's roster.
     * 
     * @param List of jogador the player to add.
     */
    public void addJogadores(List<IJogador> jogadores);

    /**
     * Removes a player from the team's roster.
     * 
     * @param jogador the player to remove.
     */
    public void removeJogador(IJogador jogador);

    /**
     * Removes a game from the team by its ID.
     * <p><b>Warning:</b> This method should only be used in very special cases.</p>
     * 
     * @param idJogo the ID of the game to remove.
     */
    public void removeJogo(long idJogo);

    /**
     * Sets the name of the team.
     * <p><b>Warning:</b> This method should only be used in very special cases.</p>
     * 
     * @param nome the new name of the team.
     */
    public void setNome(String nome);
}
