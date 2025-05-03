package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.stream.Collectors;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.JogoAmigavel;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.Morada;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.entities.jogos.SelecaoDois;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;

public class JogoMapper {

  public static JogoDto jogoToDto(Jogo jogo) {
    if (jogo == null) {
      return null;
    }
    JogoDto jogoDto = new JogoDto();
    jogoDto.setId(jogo.getId());
    jogoDto.setS1(selecaoToDto(jogo.getS1()));
    jogoDto.setS2(selecaoToDto(jogo.getS2()));
    jogoDto.setEquipaDeArbitros(
        jogo.getEquipaDeArbitros().stream()
            .map(ArbitroMapper::arbitroToDto)
            .collect(Collectors.toList()));
    jogoDto.setDiaEHora(jogo.getDiaEHora());
    jogoDto.setEstadoDeJogo(jogo.getEstadoDeJogo());
    jogoDto.setLocalDto(localToDto(jogo.getLocal()));
    // jogoDto.setStats(EstatisticaMapper.estatisticaJogoToDto(jogo.getStats()));

    return jogoDto;
  }

  public static Jogo dtoToJogo(JogoDto jogodto) {
    if (jogodto == null) {
      return null;
    }
    Jogo jogo;
    /*if(jogodto.getCampeonato() == null){
    } else {
        jogo = new JogoCampeonato();
        }*/

    jogo = new JogoAmigavel();

    jogo.setEquipaDeArbitros(
        jogodto.getEquipaDeArbitros().stream()
            .map(ArbitroMapper::dtoToArbitro)
            .collect(Collectors.toList()));
    jogo.setDiaEHora(jogodto.getDiaEHora());
    jogo.setEstadoAtual(jogodto.getEstadoDeJogo());
    jogo.setLocal(dtoToLocal(jogodto.getLocalDto()));

    // jogo nao conhece as suas proprias estatisticas.
    return jogo;
  }

  public static Jogo dtoToJogo(JogoDto from, Jogo into) {
    if (from == null) {
      return null;
    }
    if (into == null) {
      return null;
    }
    JogoDto jogodto = from;
    Jogo jogo = into;
    jogo.setEquipaDeArbitros(
        jogodto.getEquipaDeArbitros().stream()
            .map(ArbitroMapper::dtoToArbitro)
            .collect(Collectors.toList()));
    jogo.setDiaEHora(jogodto.getDiaEHora());
    jogo.setEstadoAtual(jogodto.getEstadoDeJogo());
    jogo.setLocal(dtoToLocal(jogodto.getLocalDto()));

    // jogo nao conhece as suas proprias estatisticas.

    return jogo;
  }

  private static Local dtoToLocal(LocalDto l) {
    if (l == null) {
      return null;
    }
    Local local = new Local();

    local.setNome(l.getNome());
    local.setCapacidade(l.getCapacidade());
    local.setProprietario(l.getProprietario());
    local.setMorada(dtoToMorada(l.getMorada()));
    return local;
  }

  private static Morada dtoToMorada(MoradaDto m) {
    if (m == null) {
      return null;
    }
    Morada morada = new Morada();

    morada.setCodigoPostal(m.getCodigoPostal());
    morada.setRua(m.getRua());
    morada.setLocalidade(m.getLocalidade());
    morada.setCidade(m.getCidade());
    morada.setEstado(m.getEstado());
    morada.setPais(m.getPais());
    return morada;
  }

  private static Selecao dtoToSelecao(SelecaoDto s) {
    if (s == null) {
      return null;
    }
    Selecao selecao = new Selecao();
    return selecao;
  }

  public static SelecaoDto selecaoToDto(Selecao s) {
    if (s == null) {
      return null;
    }
    SelecaoDto selecaoDto = new SelecaoDto();
    return selecaoDto;
  }

  public static SelecaoDto selecaoToDto(SelecaoDois s) {
    if (s == null) {
      return null;
    }
    SelecaoDto selecaoDto = new SelecaoDto();
    return selecaoDto;
  }

  public static LocalDto localToDto(Local l) {
    if (l == null) {
      return null;
    }
    LocalDto localDto = new LocalDto();
    localDto.setId(l.getId());
    localDto.setNome(l.getNome());
    localDto.setCapacidade(l.getCapacidade());
    localDto.setProprietario(l.getProprietario());
    localDto.setMorada(moradaToDto(l.getMorada()));
    return localDto;
  }

  private static MoradaDto moradaToDto(Morada m) {
    if (m == null) {
      return null;
    }
    MoradaDto moradaDto = new MoradaDto();

    moradaDto.setCodigoPostal(m.getCodigoPostal());
    moradaDto.setRua(m.getRua());
    moradaDto.setLocalidade(m.getLocalidade());
    moradaDto.setCidade(m.getCidade());
    moradaDto.setEstado(m.getEstado());
    moradaDto.setPais(m.getPais());
    return moradaDto;
  }
}
