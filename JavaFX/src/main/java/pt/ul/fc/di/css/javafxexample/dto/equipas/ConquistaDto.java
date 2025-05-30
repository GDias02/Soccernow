package pt.ul.fc.di.css.javafxexample.dto.equipas;

/**
 * Data Transfer Object (DTO) representing a conquest.
 * This class is used to transfer data related to a conquest between layers.
 * The date field should follow the format: dd/mm/yyyy.
 */
public class ConquistaDto{
    private Long id;
    private String descricao;
    private String data; 
    private Integer posicaoDePodio;
    private Long equipaId;
    private Long campeonatoId;

    /**
     * Gets the ID of the conquest.
     * 
     * @return the ID of the conquest.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the conquest.
     * 
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the description of the conquest.
     * 
     * @return the description of the conquest.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Sets the description of the conquest.
     * 
     * @param descricao the description to set.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Gets the date of the conquest.
     * The date should follow the format: dd/mm/yyyy.
     * 
     * @return the date of the conquest.
     */
    public String getData() { 
        return data;
    }

    /**
     * Sets the date of the conquest.
     * The date should follow the format: dd/mm/yyyy.
     * 
     * @param data the date to set.
     */
    public void setData(String data) { 
        this.data = data;
    }

    /**
     * Gets the podium position of the conquest.
     * 
     * @return the podium position of the conquest.
     */
    public Integer getPosicaoDePodio() {
        return posicaoDePodio;
    }

    /**
     * Sets the podium position of the conquest.
     * 
     * @param posicaoDePodio the podium position to set.
     */
    public void setPosicaoDePodio(Integer posicaoDePodio) {
        this.posicaoDePodio = posicaoDePodio;
    }

    /**
     * Gets the ID of the team associated with the conquest.
     * 
     * @return the team ID.
     */
    public Long getEquipaId() {
        return equipaId;
    }

    /**
     * Sets the ID of the team associated with the conquest.
     * 
     * @param equipaId the team ID to set.
     */
    public void setEquipaId(Long equipaId) {
        this.equipaId = equipaId;
    }

    /**
     * Gets the ID of the championship associated with the conquest.
     * 
     * @return the championship ID.
     */
    public Long getCampeonatoId() {
        return campeonatoId;
    }

    /**
     * Sets the ID of the championship associated with the conquest.
     * 
     * @param campeonatoId the championship ID to set.
     */
    public void setCampeonatoId(Long campeonatoId) {
        this.campeonatoId = campeonatoId;
    }
}
