package pt.ul.fc.css.soccernow.mappers.equipas;

import java.util.List;
import java.util.stream.Collectors;

import pt.ul.fc.css.soccernow.dto.equipas.*;
import pt.ul.fc.css.soccernow.entities.equipas.*;

/**
 * Mapper class for converting between Conquista entities and DTOs.
 * Provides methods to convert single objects and lists in both directions.
 */
public class ConquistaMapper {
    /**
     * Converts a ConquistaDto object to an IConquista entity.
     *
     * @param dto the ConquistaDto object to convert
     * @return the converted IConquista entity
     */
    public IConquista dtoToConquista(ConquistaDto dto) {
        IConquista conquista = new Conquista();
        conquista.setDescricao(dto.getDescricao());
        conquista.setPosicaoDePodio(dto.getPosicaoDePodio());
        //entities don't have setId()
        return conquista;
    }

    /**
     * Converts a list of ConquistaDto objects to a list of IConquista entities.
     *
     * @param dtos the list of ConquistaDto objects to convert
     * @return the list of converted IConquista entities
     */
    public List<IConquista> manyDtosToConquistas(List<ConquistaDto> dtos) {
        return dtos.stream()
                .map(this::dtoToConquista)
                .collect(Collectors.toList());
    }

    /**
     * Converts an IConquista entity to a ConquistaDto object.
     *
     * @param conquista the IConquista entity to convert
     * @return the converted ConquistaDto object
     */
    public ConquistaDto conquistaToDto(IConquista conquista) {
        ConquistaDto dto = new ConquistaDto();
        dto.setDescricao(conquista.getDescricao());
        dto.setPosicaoDePodio(conquista.getPosicaoDePodio());
        if (conquista.getId() != null) {
            dto.setId(conquista.getId());
        }
        return dto;
    }

    /**
     * Converts a list of IConquista entities to a list of ConquistaDto objects.
     *
     * @param conquistas the list of IConquista entities to convert
     * @return the list of converted ConquistaDto objects
     */
    public List<ConquistaDto> manyConquistasToDtos(List<IConquista> conquistas) {
        return conquistas.stream()
                .map(this::conquistaToDto)
                .collect(Collectors.toList());
    }
    
}
