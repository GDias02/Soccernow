package pt.ul.fc.css.soccernow.mappers.jogos;

import jakarta.persistence.EntityNotFoundException;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.exceptions.jogos.AtualizarJogoException;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

public class GoloMapper {
  public static GoloDto goloToDto(Golo g) {
    if (g == null) {
      return null;
    }
    GoloDto goloDto = new GoloDto();
    goloDto.setQuando(g.getQuando());
    goloDto.setJogo(g.getJogo().getId());
    goloDto.setMarcador(g.getMarcador().getId());
    goloDto.setEquipa(g.getEquipa().getId());

    return goloDto;
  }

  public static Golo dtoToGolo(GoloDto g) {
    if (g == null) {
      return null;
    }
    Golo golo = new Golo();
    golo.setQuando(g.getQuando());
    return golo;
  }

  public static Golo createDtoToGolo(
      GoloDto g, JogadorRepository jRep, EquipaRepository eRep, JogoRepository jogoRep)
      throws AtualizarJogoException {
    if (g == null) {
      return null;
    }
    Golo golo = new Golo();
    golo.setQuando(g.getQuando());
    try {
      golo.setJogo(jogoRep.getReferenceById(g.getJogo()));
      golo.setMarcador(jRep.getReferenceById(g.getMarcador()));
      golo.setEquipa(eRep.getReferenceById(g.getEquipa()));
    } catch (EntityNotFoundException e) {
      throw new AtualizarJogoException(
          "O golo tem de ser marcado num jogo registado, por um jogador registado, numa equipa"
              + " registada.");
    }

    return golo;
  }
}
