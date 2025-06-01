package pt.ul.fc.css.soccernow.mappers.jogos;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.JogoAmigavel;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;
import pt.ul.fc.css.soccernow.entities.jogos.Placar;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.exceptions.jogos.CriarJogoException;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.CampeonatoRepository;

public class JogoMapper {

  /**
   * Converte um Jogo para o DTO correspondente com o maximo de campos preenchidos dependendo do
   * estado do jogo. Atenção: o campo Stats não será preenchido por este método
   *
   * @param jogo
   * @return
   */
  public static JogoDto jogoToDto(Jogo jogo) {
    if (jogo == null) {
      return null;
    }
    JogoDto jogoDto = new JogoDto();
    jogoDto.setId(jogo.getId());
    jogoDto.setS1(SelecaoMapper.selecaoToDto(jogo.getS1()));
    jogoDto.setS2(SelecaoMapper.selecaoToDto(jogo.getS2()));

    jogoDto.setDiaEHora(jogo.getDiaEHora());
    jogoDto.setEstadoDeJogo(jogo.getEstadoDeJogo());
    jogoDto.setLocalDto(LocalMapper.localToDto(jogo.getLocal()));

    List<ArbitroDto> arbitros =
        jogo.getEquipaDeArbitros().stream()
            .map(ArbitroMapper::arbitroToDto)
            .collect(Collectors.toList());
    jogoDto.setEquipaDeArbitros(arbitros);
    if (jogo.getEstadoDeJogo() == EstadoDeJogo.TERMINADO) {
      jogoDto.setEquipaVencedora(
          jogo.getEquipaVencedora() != null ? jogo.getEquipaVencedora().getId() : 0L);
    }
    if (jogo.getCampeonato() != null) {
      jogoDto.setCampeonato(jogo.getCampeonato().getId());
    }

    return jogoDto;
  }

  /**
   * Converte um DTO de criar jogo para um Jogo propriamente dito, sem o guardar. Verifica a
   * existencia dos campos relevantes e a validade dos IDs passados. Não verifica: Se jogadores
   * estão em ambas as equipas, Se jogadores ou árbitros estão ocupados na data indicada, Se o local
   * indicado é novo ou já existe.
   *
   * @param jogodto
   * @param lRep
   * @param aRep
   * @param sRep
   * @param eRep
   * @param cRep
   * @return
   * @throws CriarJogoException
   */
  public static Jogo createDtoToJogo(
      JogoDto jogodto, ArbitroRepository aRep, CampeonatoRepository cRep)
      throws CriarJogoException {
    if (jogodto == null) throw new CriarJogoException("JogoDto é null");
    if (jogodto.getLocalDto() == null || !validarCriarLocalDto(jogodto.getLocalDto()))
      throw new CriarJogoException("O novo jogo não tem o local definido");

    Jogo jogo;
    if (jogodto.getCampeonato() != null) {
      jogo = new JogoCampeonato();
      Campeonato c = cRep.getReferenceById(jogodto.getCampeonato());
      jogo.setCampeonato(c);
    } else {
      jogo = new JogoAmigavel();
    }

    jogo.setDiaEHora(jogodto.getDiaEHora());

    jogo.setEstadoAtual(
        EstadoDeJogo.AGENDADO); // Nao importa o que esta no dto, a criacao gera AGENDADO.

    try {
      List<Arbitro> arbitros =
          jogodto.getEquipaDeArbitros().stream()
              .map(a -> aRep.getReferenceById(a.getUtilizador().getId()))
              .collect(Collectors.toList());
      jogo.setEquipaDeArbitros(arbitros);
    } catch (EntityNotFoundException e) {
      throw new CriarJogoException("Pelo menos um dos árbitros indicados não está registado.");
    }
    Placar p = new Placar();
    p.setScore(0, 0);
    jogo.setPlacar(p);

    return jogo;
  }

  public static Jogo registarResultadoDtoToJogo(JogoDto from, Jogo into) {
    // TODO - registarResultadoDtoToJogo
    return null;
  }

  private static boolean validarCriarLocalDto(LocalDto l) {

    if (l.getId() > 0L) return true;
    if (!l.getNome().isBlank()) return true;
    if (!l.getProprietario().isBlank()) return true;
    MoradaDto m;
    if ((m = l.getMorada()) != null) {
      if (!m.getPais().isBlank()) return true;
      if (!m.getEstado().isBlank()) return true;
      if (!m.getCidade().isBlank()) return true;
      if (!m.getLocalidade().isBlank()) return true;
      if (!m.getRua().isBlank()) return true;
      if (!m.getCodigoPostal().isBlank()) return true;
    }
    return false;
  }
}
