package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EventoDeJogo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Timestamp quando;
    
    @Column(nullable = false)
    private IJogo jogo;

    public EventoDeJogo(){}

    public EventoDeJogo(Timestamp quando, IJogo jogo){
        this.quando = quando;
        this.jogo = jogo;
    }

    public Timestamp getQuando() {
        return quando;
    }
    public void setQuando(Timestamp quando) {
        this.quando = quando;
    }
    public IJogo getJogo() {
        return jogo;
    }
    public void setJogo(IJogo jogo) {
        this.jogo = jogo;
    }
}
