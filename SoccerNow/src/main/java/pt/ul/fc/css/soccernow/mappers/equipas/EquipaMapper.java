package pt.ul.fc.css.soccernow.mappers.equipas;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.IJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

/**
 * Mapper class for converting between Equipa entities and DTOs. Provides methods to convert single
 * objects and lists in both directions.
 */
public class EquipaMapper {

  //@Autowired private static JogadorRepository jogadorRepository;

  //@Autowired private static JogoRepository jogoRepository;

  /**
   * Converts an Equipa entity to an EquipaDto.
   *
   * @param equipa the Equipa entity to convert
   * @return the converted EquipaDto
   */
  public static EquipaDto equipaToDto(Equipa equipa) {
    if (equipa == null) return null;
    EquipaDto dto = new EquipaDto();
    dto.setNome(equipa.getNome());
    dto.setHistoricoDeJogos(
        equipa.getHistoricoDeJogos().stream()
            .map(Jogo::getId)
            .collect(Collectors.toList())
    );
    dto.setJogadores(
        equipa.getJogadores().stream()
            .map(Jogador::getId) 
            .collect(Collectors.toList())
    );
    if (equipa.getId() != null) {
      dto.setId(equipa.getId());
    }

    return dto;
  }

  /**
   * Converts a list of Equipa entities to a list of EquipaDto objects.
   *
   * @param equipas the list of Equipa entities to convert
   * @return the list of converted EquipaDto objects
   */
  public static List<EquipaDto> manyEquipasToDtos(List<Equipa> equipas) {
    return equipas.stream().map(EquipaMapper::equipaToDto).collect(Collectors.toList());
  }

  /**
   * Converts an EquipaDto to an Equipa entity.
   *
   * @param dto the EquipaDto to convert
   * @return the converted Equipa entity
   */
  public static Equipa dtoToEquipa(EquipaDto dto, JogadorRepository jogadorRepository, JogoRepository jogoRepository) {
    Equipa equipa = new Equipa(dto.getNome());
    equipa.setId(dto.getId());
    if (dto.getJogadores() != null && !dto.getJogadores().isEmpty())
      equipa.addJogadores(jogadorRepository.findAllById(dto.getJogadores()));
    if (dto.getHistoricoDeJogos() != null && !dto.getHistoricoDeJogos().isEmpty())
      equipa.addJogos(jogoRepository.findAllById(dto.getHistoricoDeJogos()));
    return equipa;
  }

  /**
   * Converts a list of EquipaDto objects to a list of Equipa entities.
   *
   * @param dtos the list of EquipaDto objects to convert
   * @return the list of converted Equipa entities
   */
  public static List<Equipa> manyDtosToEquipas(List<EquipaDto> dtos, JogadorRepository jogadorRepository, JogoRepository jogoRepository) {
    return dtos.stream().map(equipa -> {
        return dtoToEquipa(equipa, jogadorRepository, jogoRepository);
      }
    ).collect(Collectors.toList());
  }
}
