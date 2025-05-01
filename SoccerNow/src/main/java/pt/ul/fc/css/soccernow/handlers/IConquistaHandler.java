package pt.ul.fc.css.soccernow.handlers;

import java.util.List;

import pt.ul.fc.css.soccernow.dto.equipas.ConquistaDto;

/**
 * Interface that defines the operations for managing team achievements (conquistas).
 * Provides methods for CRUD operations and mapping between DTOs and entities.
 */
public interface IConquistaHandler {

    /**
     * Retrieves a list of all registered achievements for a specific team.
     * 
     * @param equipaId The unique identifier of the team.
     * @return A list of {@link ConquistaDto} representing all achievements of the team.
     */
    public List<ConquistaDto> verificarConquistas(long equipaId);

    /**
     * Retrieves an achievement by its ID from a specific team.
     * 
     * @param equipaId The unique identifier of the team.
     * @param conquistaId The unique identifier of the achievement.
     * @return The corresponding {@link ConquistaDto} if the achievement exists.
     */
    public ConquistaDto verificarConquista(long equipaId, long conquistaId);

    /**
     * Registers a new achievement for a specific team.
     * 
     * @param equipaId The unique identifier of the team.
     * @param conquistaDto The details of the achievement to be registered encapsulated in a {@link ConquistaDto}.
     * @return The registered {@link ConquistaDto}.
     */
    public ConquistaDto registarConquista(long equipaId, ConquistaDto conquistaDto);

    /**
     * Removes an achievement by its ID belonging to a specific team.
     * 
     * @param equipaId The unique identifier of the team.
     * @param conquistaId The unique identifier of the achievement to be removed.
     * @return The {@link ConquistaDto} of the removed achievement.
     */
    public ConquistaDto removerConquista(long equipaId, long conquistaId);

    /**
     * Updates the details of an existing achievement from a specific team.
     * 
     * @param equipaId The unique identifier of the team.
     * @param conquistaDto The new details of the achievement encapsulated in a {@link ConquistaDto}.
     * @return The updated {@link ConquistaDto}.
     */
    public ConquistaDto atualizarConquista(long equipaId, long conquistaId, ConquistaDto conquistaDto);

}
