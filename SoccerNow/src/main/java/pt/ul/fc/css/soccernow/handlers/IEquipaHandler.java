package pt.ul.fc.css.soccernow.handlers;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;

import java.util.List;


/**
 * Interface that defines the operations for managing futsal teams (equipas).
 * Provides methods for CRUD operations and mapping between DTOs and entities.
 */
public interface IEquipaHandler {

    /**
     * Gets a team by it's ID.
     * 
     * @param id The unique identifier of the team.
     * @return The corresponding {@link EquipaDto} if the team exists.
     */
    public EquipaDto verificarEquipa(Long id);

    /**
     * Removes a team by its ID.
     * 
     * @param id The unique identifier of the team to be removed.
     * @return The {@link EquipaDto} of the removed team.
     */
    public EquipaDto removerEquipa(Long id);

    /**
     * Updates the details of an existing team.
     * 
     * @param id The unique identifier of the team to be updated.
     * @param equipaDto The new details of the team encapsulated in a {@link EquipaDto}.
     * @return The updated {@link EquipaDto}.
     */
    public EquipaDto atualizarEquipa(Long id, EquipaDto equipaDto);

    /**
     * Retrieves a list of all registered teams.
     * 
     * @return A list of {@link EquipaDto} representing all teams.
     */
    public List<EquipaDto> verificarEquipas();

    /**
     * Registers a new team in the system.
     * 
     * @param equipaDto The details of the team to be registered encapsulated in a {@link EquipaDto}.
     * @return The registered {@link EquipaDto}.
     */
    public EquipaDto registarEquipa(EquipaDto equipaDto);

}

