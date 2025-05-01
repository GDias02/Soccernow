package pt.ul.fc.css.soccernow.mappers.equipas;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.entities.equipas.*;
import pt.ul.fc.css.soccernow.entities.jogos.*;
import pt.ul.fc.css.soccernow.entities.utilizadores.*;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

/**
 * Mapper class for converting between Equipa entities and DTOs.
 * Provides methods to convert single objects and lists in both directions.
 */
public class EquipaMapper {
    
    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private JogoRepository jogoRepository;

    /**
     * Converts an IEquipa entity to an EquipaDto.
     *
     * @param equipa the IEquipa entity to convert
     * @return the converted EquipaDto
     */
    public EquipaDto equipaToDto(IEquipa equipa){
        EquipaDto dto = new EquipaDto();
        dto.setNome(equipa.getNome());
        List<Jogo> historico = equipa.getHistoricoDeJogos();
        for (IJogo jogo: historico){
            dto.addJogo(jogo.getId());
        }
        List<Jogador> jogadores = equipa.getJogadores();
        for (IJogador jogador: jogadores){
            dto.addJogador(jogador.getId());
        }
        if (equipa.getId() != null) {
            dto.setId(equipa.getId());
        }
        return dto;
    }

    /**
     * Converts a list of IEquipa entities to a list of EquipaDto objects.
     *
     * @param equipas the list of IEquipa entities to convert
     * @return the list of converted EquipaDto objects
     */
    public List<EquipaDto> manyEquipasToDtos(List<IEquipa> equipas){
        return equipas.stream()
                    .map(this::equipaToDto)
                    .collect(Collectors.toList());
    }

    /**
     * Converts an EquipaDto to an IEquipa entity.
     *
     * @param dto the EquipaDto to convert
     * @return the converted IEquipa entity
     */
    public IEquipa dtoToEquipa(EquipaDto dto){
        IEquipa equipa = new Equipa(dto.getNome());
        equipa.addJogadores(jogadorRepository.findAllById(dto.getJogadores()));
        equipa.addJogos(jogoRepository.findAllById(dto.getHistoricoDeJogos()));
        //entities don't have setId()
        return equipa;
    }

    /**
     * Converts a list of EquipaDto objects to a list of IEquipa entities.
     *
     * @param dtos the list of EquipaDto objects to convert
     * @return the list of converted IEquipa entities
     */
    public List<IEquipa> manyDtosToEquipas(List<EquipaDto> dtos){
        return dtos.stream()
                .map(this::dtoToEquipa)
                .collect(Collectors.toList());
    }
}
