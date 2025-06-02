package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.List;
import java.util.stream.Collectors;

import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.JogoAmigavel;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;
import pt.ul.fc.css.soccernow.entities.jogos.Placar;
import pt.ul.fc.css.soccernow.exceptions.jogos.CriarJogoException;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroPostMapper;
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

    List<ArbitroPostDto> arbitros =
        jogo.getEquipaDeArbitros().stream()
            .map(ArbitroPostMapper::arbitroToDto)
            .collect(Collectors.toList());
    jogoDto.setEquipaDeArbitros(arbitros);
    if (jogo.getEstadoDeJogo() == EstadoDeJogo.TERMINADO) {
      jogoDto.setEquipaVencedora(
          jogo.getEquipaVencedora() != null ? jogo.getEquipaVencedora().getId() : 0L);
    }
    if (jogo.getCampeonato() != null) {
      jogoDto.setCampeonato(jogo.getCampeonato().getId());
    }
    if (jogo.getStats() != null) {
      jogoDto.setStats(EstatisticaMapper.estatisticaJogoToDto(jogo.getStats()));
    }

    return jogoDto;
  }

  /**
   * Converte um DTO de criar jogo para um Jogo propriamente dito, sem o guardar. Não verifica: Se
   * jogadores estão em ambas as equipas, Se jogadores ou árbitros estão ocupados na data indicada,
   * Se o local indicado é novo ou já existe.
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
  public static Jogo createDtoToJogo(JogoDto jogodto, CampeonatoRepository cRep)
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
