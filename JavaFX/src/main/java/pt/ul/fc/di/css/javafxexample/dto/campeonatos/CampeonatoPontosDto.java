package pt.ul.fc.di.css.javafxexample.dto.campeonatos;

import java.util.Map;

public class CampeonatoPontosDto {
  // Mapa Equipa - Pontos
  private Map<Long, Long> tabela;

  public CampeonatoPontosDto() {}

  public Map<Long, Long> getTabela() {
    return tabela;
  }

  public void setTabela(Map<Long, Long> tabela) {
    this.tabela = tabela;
  }
}
