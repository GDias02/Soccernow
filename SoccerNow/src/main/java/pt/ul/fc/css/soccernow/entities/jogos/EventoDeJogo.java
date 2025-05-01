package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EventoDeJogo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Timestamp quando;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false ,name = "em_jogo", referencedColumnName = "jogoId")
    private Jogo jogo;

    public EventoDeJogo(){}

    public EventoDeJogo(Timestamp quando, Jogo jogo){
        this.quando = quando;
        this.jogo = jogo;
    }

    public Timestamp getQuando() {
        return quando;
    }
    public void setQuando(Timestamp quando) {
        this.quando = quando;
    }
    public Jogo getJogo() {
        return jogo;
    }
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
}
