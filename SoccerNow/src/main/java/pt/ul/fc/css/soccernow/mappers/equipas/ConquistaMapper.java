package pt.ul.fc.css.soccernow.mappers.equipas;

import java.util.List;
import java.util.stream.Collectors;

import pt.ul.fc.css.soccernow.dto.equipas.*;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.equipas.*;
import pt.ul.fc.css.soccernow.repositories.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;

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
    public static IConquista dtoToConquista(ConquistaDto dto, CampeonatoRepository campeonatoRepository, EquipaRepository equipaRepository) {
        IConquista conquista = new Conquista();
        conquista.setDescricao(dto.getDescricao());
        conquista.setPosicaoDePodio(dto.getPosicaoDePodio());
        conquista.setId(dto.getId());

        Campeonato campeonato = campeonatoRepository.findById(dto.getCampeonatoId()).get();
        Equipa equipa = equipaRepository.findById(dto.getEquipaId()).get();

        if (campeonato != null && equipa != null) {
            conquista.setCampeonato(campeonato);
            conquista.setEquipa(equipa);
        } else  {
            throw new IllegalArgumentException("Campeonato or Equipa not found for the given IDs.");
        }

        return conquista;
    }

    /**
     * Converts a list of ConquistaDto objects to a list of IConquista entities.
     *
     * @param dtos the list of ConquistaDto objects to convert
     * @return the list of converted IConquista entities
     */
    public static List<IConquista> manyDtosToConquistas(List<ConquistaDto> dtos, CampeonatoRepository campeonatoRepository, EquipaRepository equipaRepository) {
        return dtos.stream()
                .map(dto -> { return ConquistaMapper.dtoToConquista(dto, campeonatoRepository, equipaRepository);})
                .collect(Collectors.toList());
    }

    /**
     * Converts an IConquista entity to a ConquistaDto object.
     *
     * @param conquista the IConquista entity to convert
     * @return the converted ConquistaDto object
     */
    public static ConquistaDto conquistaToDto(IConquista conquista) {
        ConquistaDto dto = new ConquistaDto();
        dto.setDescricao(conquista.getDescricao());
        dto.setPosicaoDePodio(conquista.getPosicaoDePodio());
        dto.setId(conquista.getId());    
        return dto;
    }

    /**
     * Converts a list of IConquista entities to a list of ConquistaDto objects.
     *
     * @param conquistas the list of IConquista entities to convert
     * @return the list of converted ConquistaDto objects
     */
    public static List<ConquistaDto> manyConquistasToDtos(List<IConquista> conquistas) {
        return conquistas.stream()
                .map(ConquistaMapper::conquistaToDto)
                .collect(Collectors.toList());
    }
    
}
