package pt.ul.fc.css.soccernow.dto.campeonatos;

import java.util.HashMap;
import java.util.Map;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoPontos;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;

public class CampeonatoPontosDto {
  // Mapa Equipa - Pontos
  private Map<Long, Long> tabela;

  public CampeonatoPontosDto() {}

  public CampeonatoPontosDto(CampeonatoPontos campeonato) {
    this.tabela = serializeTabela(campeonato.getTabela());
  }

  public Map<Long, Long> getTabela() {
    return tabela;
  }

  public void setTabela(Map<Long, Long> tabela) {
    this.tabela = tabela;
  }

  private Map<Long, Long> serializeTabela(Map<Equipa, Long> t) {
    Map<Long, Long> tabela = new HashMap<>();
    t.forEach((equipa, pontos) -> tabela.put(equipa.id, pontos));
    return tabela;
  }
}
