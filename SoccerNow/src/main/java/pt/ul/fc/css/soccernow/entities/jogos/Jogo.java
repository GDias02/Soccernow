package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.IArbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_de_jogo")
public abstract class Jogo implements IJogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Selecao s1;
    @Embedded
    private Selecao s2;

    @ManyToMany
    private List<IArbitro> equipaDeArbitros;

    @Column(nullable = false)
    private EstadoDeJogo estadoAtual;

    @ManyToOne
    @JoinColumn(name = "local_id", referencedColumnName = "id")
    private Local local;

    @Column(nullable = false)
    private Date diaEHora;

    @Column
    private String resultadoFinal; //para acesso mais rapido ao resultado de um jogo, sem necessidade de carregar todas as suas estatisticas

    @Transient
    private Placar placar;

    @Transient
    private EstatisticaJogo stats;

    public IEquipa getEquipa1() {return s1.getEquipa();}
    public IEquipa getEquipa2() {return s2.getEquipa();}

    public Map<Posicao,IJogador> getSelecao(IEquipa equipa){
        if (equipa.equals(s1.getEquipa())){return s1.getJogadores();}
        if (equipa.equals(s2.getEquipa())){return s2.getJogadores();}
        else {return null;}
    }
    public IArbitro getArbitroPrincipal(){
        return equipaDeArbitros.get(0);
    }

    //Getters & Setters
    public Long getId() {return id;}

    public Placar getPlacar(){return this.placar;}
    public Selecao getS1() {return s1;}
    public Selecao getS2() {return s2;}
    public List<IArbitro> getEquipaDeArbitros() {return equipaDeArbitros;}
    public Local getLocal() {return local;}
    public Date getDiaEHora() {return diaEHora;}
    public EstadoDeJogo getEstadoAtual() {return estadoAtual;}
    public String getResultadoFinal(){return resultadoFinal;}
    public EstatisticaJogo getEstatisticaJogo() {return this.stats;}
    
    public void setS1(Selecao s1) {this.s1 = s1;}
    public void setS2(Selecao s2) {this.s2 = s2;}
    public void setEquipaDeArbitros(List<IArbitro> equipaDeArbitros) {this.equipaDeArbitros = equipaDeArbitros;}
    public void setLocal(Local local) {this.local = local;}
    public void setDiaEHora(Date diaEHora) {this.diaEHora = diaEHora;}
    public void setEstadoAtual(EstadoDeJogo estadoAtual) {this.estadoAtual = estadoAtual;}
    
    public void updatePlacar(){this.placar = this.stats.getPlacar();}
}
