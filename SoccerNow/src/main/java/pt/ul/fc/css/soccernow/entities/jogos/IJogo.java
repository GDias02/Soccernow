package pt.ul.fc.css.soccernow.entities.jogos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

public interface IJogo {

  Long getId();

  EstadoDeJogo getEstadoDeJogo();

  Equipa getEquipa1();

  Equipa getEquipa2();

  Map<Posicao, Jogador> getSelecao(Equipa equipa);

  Placar getPlacar();

  Arbitro getArbitroPrincipal();

  List<Arbitro> getEquipaDeArbitros();

  Local getLocal();

  LocalDateTime getDiaEHora();

  EstatisticaJogo getStats();

  Campeonato getCampeonato();

  void setEstadoAtual(EstadoDeJogo ej);

  void setS1(Selecao s1);

  void setS2(Selecao s2);

  void setPlacar(Placar p);

  void setEquipaDeArbitros(List<Arbitro> la);

  void setLocal(Local l);

  void setDiaEHora(LocalDateTime ldt);

  void setStats(EstatisticaJogo sj);

  void setCampeonato(Campeonato c);
}
