package pt.ul.fc.css.soccernow.entities.jogos;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EventoDeJogo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name="quando", columnDefinition = "TIMESTAMP")
    private LocalDateTime quando;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false ,name = "em_jogo", referencedColumnName = "jogoId")
    private Jogo jogo;

    public EventoDeJogo(){}

    public EventoDeJogo(LocalDateTime quando, Jogo jogo){
        this.quando = quando;
        this.jogo = jogo;
    }

    public LocalDateTime getQuando() {
        return quando;
    }
    public void setQuando(LocalDateTime quando) {
        this.quando = quando;
    }
    public Jogo getJogo() {
        return jogo;
    }
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
}
