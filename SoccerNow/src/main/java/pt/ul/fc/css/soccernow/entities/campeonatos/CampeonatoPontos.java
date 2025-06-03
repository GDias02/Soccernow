package pt.ul.fc.css.soccernow.entities.campeonatos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;

@Entity
public class CampeonatoPontos extends Campeonato {
    
    @ElementCollection //Porque os pontos são apenas Longs e não Entidades
    @CollectionTable(
        name = "tabela_pontos",
        joinColumns = @JoinColumn(name = "campeonato_id")
    )
    @MapKeyJoinColumn(name = "equipa_id") //Mapeamento relativo à chave, entidade
    @Column(name = "pontos")
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
