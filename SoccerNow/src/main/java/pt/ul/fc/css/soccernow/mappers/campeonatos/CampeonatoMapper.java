package pt.ul.fc.css.soccernow.mappers.campeonatos;

import java.util.List;
import java.util.stream.Collectors;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoEliminatoriaDto;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoPontosDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoEliminatoria;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoPontos;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

public class CampeonatoMapper {
  public static CampeonatoDto campeonatoToDto(Campeonato campeonato) {
    if (!((campeonato instanceof CampeonatoEliminatoria)
        || (campeonato instanceof CampeonatoPontos)))
      throw new RuntimeException("Este campeonato é inválido");

    String tipo = campeonato instanceof CampeonatoEliminatoria ? "eliminatoria" : "pontos";
    List<Long> equipas =
        campeonato.getEquipas().stream()
            .map((equipa) -> equipa.getId())
            .collect(Collectors.toList());
    List<Long> jogos =
        campeonato.getJogos().stream().map((jogo) -> jogo.getId()).collect(Collectors.toList());
    CampeonatoDto campeonatoDto =
        new CampeonatoDto(
            campeonato.getId(),
            campeonato.getNome(),
            campeonato.getEstado(),
            jogos,
            equipas,
            campeonato.getDataInicio(),
            tipo);

    if (campeonato instanceof CampeonatoEliminatoria) {
      CampeonatoEliminatoriaDto eliminatoria =
          new CampeonatoEliminatoriaDto((CampeonatoEliminatoria) campeonato);
      campeonatoDto.setEliminatoriaDto(eliminatoria);
    } else if (campeonato instanceof CampeonatoPontos) {
      CampeonatoPontosDto pontos = new CampeonatoPontosDto((CampeonatoPontos) campeonato);
      campeonatoDto.setPontosDto(pontos);
    }

    return campeonatoDto;
  }

  public static List<CampeonatoDto> manyCampeonatosToDtos(List<Campeonato> campeonatos) {
    return campeonatos.stream().map(CampeonatoMapper::campeonatoToDto).collect(Collectors.toList());
  }

  public static Campeonato dtoToCampeonato(
      CampeonatoDto campeonatoDto,
      EquipaRepository equipaRepository,
      JogoRepository jogoRepository) {
    Campeonato campeonato = null;
    List<Equipa> equipas = equipaRepository.findAllById(campeonatoDto.getEquipas());
    List<JogoCampeonato> jogos =
        jogoRepository.findAllById(campeonatoDto.getJogos()).stream()
            .map(jogo -> (JogoCampeonato) jogo)
            .collect(Collectors.toList());
    if (campeonatoDto.getTipo().equals("pontos")) {
      campeonato =
          new CampeonatoPontos(
              campeonatoDto.getId(),
              campeonatoDto.getNome(),
              campeonatoDto.getEstado(),
              campeonatoDto.getDataInicio(),
              jogos,
              equipas);

    } else if (campeonatoDto.getTipo().equals("eliminatoria")) {

      campeonato =
          new CampeonatoEliminatoria(
              campeonatoDto.getId(),
              campeonatoDto.getNome(),
              campeonatoDto.getEstado(),
              campeonatoDto.getDataInicio(),
              jogos,
              equipas);
    }
    return campeonato;
  }

  public static List<Campeonato> manyDtosToCampeonatos(
      List<CampeonatoDto> campeonatos,
      EquipaRepository equipaRepository,
      JogoRepository jogoRepository) {
    return campeonatos.stream()
        .map(
            campeonato -> {
              return dtoToCampeonato(campeonato, equipaRepository, jogoRepository);
            })
        .collect(Collectors.toList());
  }
}
