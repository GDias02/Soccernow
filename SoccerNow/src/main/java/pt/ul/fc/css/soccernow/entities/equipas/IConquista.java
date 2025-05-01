package pt.ul.fc.css.soccernow.entities.equipas;

import java.util.Date;

/**
 * Interface representing a Conquista (achievement) entity.
 * Provides methods to get and set properties such as ID, description, date,
 * podium position, associated team, and championship.
 */
public interface IConquista {

    /**
     * Gets the ID of the Conquista.
     *
     * @return the ID of the Conquista
     */
    public Long getId();

    /**
     * Sets the ID of the Conquista.
     *
     * @param id the ID to set
     */
    public void setId(Long id);

    /**
     * Gets the description of the Conquista.
     *
     * @return the description of the Conquista
     */
    public String getDescricao();

    /**
     * Sets the description of the Conquista.
     *
     * @param descricao the description to set
     */
    public void setDescricao(String descricao);

    /**
     * Gets the date of the Conquista.
     *
     * @return the date of the Conquista
     */
    public Date getData();

    /**
     * Sets the date of the Conquista.
     *
     * @param data the date to set
     */
    public void setData(Date data);

    /**
     * Gets the podium position of the Conquista.
     *
     * @return the podium position of the Conquista
     */
    public Integer getPosicaoDePodio();

    /**
     * Sets the podium position of the Conquista.
     *
     * @param posicaoDePodio the podium position to set
     */
    public void setPosicaoDePodio(Integer posicaoDePodio);

    /**
     * Gets the associated team of the Conquista.
     *
     * @return the associated team of the Conquista
     */
    public IEquipa getEquipa();

    /**
     * Sets the associated team of the Conquista.
     *
     * @param equipa the team to set
     */
    public void setEquipa(IEquipa equipa);

    /**
     * Gets the associated championship of the Conquista.
     *
     * @return the associated championship of the Conquista
     */
    public ICampeonato getCampeonato();

    /**
     * Sets the associated championship of the Conquista.
     *
     * @param campeonato the championship to set
     */
    public void setCampeonato(ICampeonato campeonato);
}
