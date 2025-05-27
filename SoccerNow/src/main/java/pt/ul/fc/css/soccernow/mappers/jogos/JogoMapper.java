package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.entities.jogos.JogoAmigavel;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.Morada;
import pt.ul.fc.css.soccernow.entities.jogos.Placar;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.entities.jogos.SelecaoDois;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

public class JogoMapper {

  public static JogoDto jogoToDto(Jogo jogo) {
    if (jogo == null) {
      return null;
    }
    JogoDto jogoDto = new JogoDto();
    jogoDto.setId(jogo.getId());
    jogoDto.setS1(selecaoToDto(jogo.getS1()));
    jogoDto.setS2(selecaoToDto(jogo.getS2()));

    jogoDto.setDiaEHora(jogo.getDiaEHora());
    jogoDto.setEstadoDeJogo(jogo.getEstadoDeJogo());
    jogoDto.setLocalDto(localToDto(jogo.getLocal()));
    // jogoDto.setStats(EstatisticaMapper.estatisticaJogoToDto(jogo.getStats()));

    return jogoDto;
  }

  // TODO - versao demasiado simples, nao resolve quase nada. usar createDtoToJogo para criar um
  // jogo a partir de um jogodto dedicado a create (sem id), ou usar registarResultadoDtoToJogo para
  // finalizar um jogo
  public static Jogo dtoToJogo(JogoDto jogodto) {
    if (jogodto == null) {
      return null;
    }
    Jogo jogo;

    jogo = new JogoAmigavel();

    jogo.setDiaEHora(jogodto.getDiaEHora());
    jogo.setEstadoAtual(jogodto.getEstadoDeJogo());
    jogo.setLocal(dtoToLocal(jogodto.getLocalDto()));

    // jogo nao conhece as suas proprias estatisticas.
    return jogo;
  }

  public static Jogo createDtoToJogo(
      JogoDto jogodto,
      EquipaRepository eRep,
      JogadorRepository jRep,
      ArbitroRepository aRep,
      CampeonatoRepository cRep) {
    if (jogodto == null) {
      return null;
    }
    if (jogodto.getId() != null) {
      return null;
    }
    Jogo jogo;
    if (jogodto.getCampeonato() != null) {
      jogo = new JogoCampeonato();
      Campeonato c = cRep.getReferenceById(jogodto.getCampeonato());
      jogo.setCampeonato(c);
    } else {
      jogo = new JogoAmigavel();
    }

    jogo.setDiaEHora(jogodto.getDiaEHora());
    jogo.setEstadoAtual(jogodto.getEstadoDeJogo());
    jogo.setLocal(dtoToLocal(jogodto.getLocalDto()));

    Equipa e1 = eRep.getReferenceById(jogodto.getS1().getEquipa());
    Jogador cap1 = jRep.getReferenceById(jogodto.getS1().getCapitao());
    Map<Posicao, Long> jogsS1 = jogodto.getS1().getJogadores();
    Map<Posicao, Jogador> jogadoresS1 = new EnumMap<>(Posicao.class);
    jogsS1.forEach((k, v) -> jogadoresS1.put(k, jRep.getReferenceById(v)));
    Selecao s1 = new Selecao(e1, cap1, jogadoresS1);

    Equipa e2 = eRep.getReferenceById(jogodto.getS2().getEquipa());
    Jogador cap2 = jRep.getReferenceById(jogodto.getS2().getCapitao());
    Map<Posicao, Long> jogsS2 = jogodto.getS2().getJogadores();
    Map<Posicao, Jogador> jogadoresS2 = new EnumMap<>(Posicao.class);
    jogsS2.forEach((k, v) -> jogadoresS2.put(k, jRep.getReferenceById(v)));
    Selecao s2 = new Selecao(e2, cap2, jogadoresS1);

    List<Arbitro> arbitros =
        jogodto.getEquipaDeArbitros().stream()
            .map(a -> aRep.getReferenceById(a.getUtilizador().getId()))
            .collect(Collectors.toList());

    jogo.setS1(s1);
    jogo.setS2(s2);
    jogo.setEquipaDeArbitros(arbitros);
    Placar p = new Placar();
    p.setScore(0, 0);
    jogo.setPlacar(p);

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

  public static Local dtoToLocal(LocalDto l) {
    if (l == null) {
      return null;
    }
    Local local = new Local();
    local.setId(l.getId());
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
    selecaoDto.setEquipa(s.getEquipa().getId());
    return selecaoDto;
  }

  public static SelecaoDto selecaoToDto(SelecaoDois s) {
    if (s == null) {
      return null;
    }
    SelecaoDto selecaoDto = new SelecaoDto();
    selecaoDto.setEquipa(s.getEquipa().getId());
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
