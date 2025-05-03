package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EventoDeJogo {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  @Column(name = "quando", columnDefinition = "TIMESTAMP")
  private LocalDateTime quando;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, name = "em_jogo")
  private Jogo jogo;

  public EventoDeJogo() {}

  public EventoDeJogo(LocalDateTime quando, Jogo jogo) {
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
