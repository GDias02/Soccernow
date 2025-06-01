package pt.ul.fc.css.soccernow.entities.campeonatos;

import java.util.Date;
import java.util.List;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;

public class CampeonatoEliminatoria extends Campeonato {
  // TODO
  public CampeonatoEliminatoria(
      Long id,
      String nome,
      EstadoCampeonato estado,
      Date dataInicio,
      List<JogoCampeonato> jogos,
      List<Equipa> equipas) {
    super(id, nome, estado, dataInicio, jogos, equipas);
  }
}
