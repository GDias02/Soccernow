package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;

enum EstadoJogo {
AGENDADO,
A_DECORRER,
INTERVALO,
PROLONGAMENTO,
TERMINADO,
CANCELADO
}

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Jogo implements IJogo {
    
    @Embedded
    Selecao s1;
    @Embedded
    Selecao s2;

    @ManyToMany
    List<Arbitro> equipaDeArbitros;

    @Embedded
    Local local;

    @Column(nullable = false)
    Date diaEHora;

    Placar placar;  //para acesso mais rapido ao resultado de um jogo, sem necessidade de carregar todas as suas estatisticas

    @Transient
    EstatisticaJogo stats;

    public Placar getPlacar(){
        return this.placar;
    }
    
    public void updatePlacar(){
        this.placar = this.stats.getPlacar();
    }

    public Selecao getS1() {
        return s1;
    }
    public void setS1(Selecao s1) {
        this.s1 = s1;
    }
    public Selecao getS2() {
        return s2;
    }
    public void setS2(Selecao s2) {
        this.s2 = s2;
    }
    public List<Arbitro> getEquipaDeArbitros() {
        return equipaDeArbitros;
    }
    public void setEquipaDeArbitros(List<Arbitro> equipaDeArbitros) {
        this.equipaDeArbitros = equipaDeArbitros;
    }
    public Local getLocal() {
        return local;
    }
    public void setLocal(Local local) {
        this.local = local;
    }
    public Date getDiaEHora() {
        return diaEHora;
    }
    public void setDiaEHora(Date diaEHora) {
        this.diaEHora = diaEHora;
    }
}
