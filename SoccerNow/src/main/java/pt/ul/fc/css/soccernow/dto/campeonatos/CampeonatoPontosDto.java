package pt.ul.fc.css.soccernow.dto.campeonatos;

import java.util.Map;

public class CampeonatoPontosDto {
    //Mapa Equipa - Pontos
    private Map<Long, Long> tabela;

    public CampeonatoPontosDto() {
    }

    public CampeonatoPontosDto(Map<Long, Long> tabela) {
        this.tabela = tabela;
    }

    public Map<Long, Long> getTabela() {
        return tabela;
    }

    public void setTabela(Map<Long, Long> tabela) {
        this.tabela = tabela;
    }

}
