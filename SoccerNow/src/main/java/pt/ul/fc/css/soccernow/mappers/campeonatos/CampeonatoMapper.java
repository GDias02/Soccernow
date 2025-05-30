package pt.ul.fc.css.soccernow.mappers.campeonatos;

import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoEliminatoriaDto;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoPontosDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoEliminatoria;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoPontos;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

public class CampeonatoMapper {
  public static CampeonatoDto campeonatoToDto(Campeonato campeonato) {
    if (!((campeonato instanceof CampeonatoEliminatoria)
        || (campeonato instanceof CampeonatoPontos)))
      throw RuntimeException("Este campeonato é inválido");

    CampeonatoDto campeonatoDto =
        new CampeonatoDto(
            campeonato.getId(),
            campeonato.getNome(),
            campeonato.getEstado(),
            campeonato.getJogos(),
            campeonato.getEquipas(),
            campeonato.getDataInicio(),
            campeonato instanceof CampeonatoEliminatoria ? "eliminatoria" : "pontos");

    if (campeonatoDto.getTipo() == "eliminatoria") {
      CampeonatoEliminatoria eliminatoria = new CampeonatoEliminatoriaDto(campeonato);
      campeonatoDto.setEliminatoriaDto(eliminatoria);
    } else {
      CampeonatoPontos pontos = new CampeonatoPontosDto(campeonato);
      campeonatoDto.setPontosDto(pontos);
    }

    return campeonatoDto;
  }

  public static Campeonato dtoToCampeonato(
      CampeonatoDto campeonatoDto,
      EquipaRepository equipaRepository,
      JogoRepository jogoRepository) {
    Campeonato campeonato;
    List<Equipa> equipas = equipaRepository.findAllById(campeonatoDto.getEquipas());
    List<Jogos> jogos = jogoRepository.findAllById(campeonatoDto.getJogos());
    if (campeonatoDto.getTipo() == "pontos") {
      campeonato =
          new CampeonatoPontos(
              (campeonatoDto.getId()),
              campeonatoDto.getNome(),
              campeonatoDto.getEstado(),
              campeonatoDto.getDataInicio(),
              jogos,
              equipas,
              campeonatoDto.getPontosDto().getTabela());
    } else if (campeonatoDto.getTipo() == "eliminatoria") {
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
}
