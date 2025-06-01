package pt.ul.fc.css.soccernow.entities.campeonatos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;

public class CampeonatoPontos extends Campeonato {
    
    private Map<Equipa, Long> tabela;

    public CampeonatoPontos(){
        //For JPA
    }

    public CampeonatoPontos(Long id, String nome, EstadoCampeonato estado, Date dataInicio, List<JogoCampeonato> jogos,
            List<Equipa> equipas, Map<Equipa, Long> tabela){
        super(id,nome,estado,dataInicio,jogos,equipas);
        this.tabela = tabela;
    }

    public CampeonatoPontos(Long id, String nome, EstadoCampeonato estado, Date dataInicio, List<JogoCampeonato> jogos,
            List<Equipa> equipas){
        super(id,nome,estado,dataInicio,jogos,equipas);
        this.tabela = new HashMap<>();
    }

    public Map<Equipa, Long> getTabela() {
        return tabela;
    }

    public void setTabela(Map<Equipa, Long> tabela) {
        this.tabela = tabela;
    }

    @Override
    public String toString() {
        return "CampeonatoPontos [tabela=" + tabela + ", id=" + id + ", nome=" + nome + ", estado=" + estado
                + ", dataInicio=" + dataInicio + "]";
    }

    
}
