package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

@Entity
public class Cartao extends EventoDeJogo {

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private Jogador atribuidoA;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private Arbitro arbitro;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private Cor cor;

  public Cartao(LocalDateTime quando, Jogo jogo, Jogador jogador, Arbitro arbitro, Cor cor) {
    super(quando, jogo);
    this.atribuidoA = jogador;
    this.arbitro = arbitro;
    this.cor = cor;
  }

  public Cartao() {
    // TODO Auto-generated constructor stub
  }

  public Jogador getAtribuidoA() {
    return atribuidoA;
  }

  public void setAtribuidoA(Jogador atribuidoA) {
    this.atribuidoA = atribuidoA;
  }

  public Arbitro getArbitro() {
    return arbitro;
  }

  public void setArbitro(Arbitro arbitro) {
    this.arbitro = arbitro;
  }

  public Cor getCor() {
    return cor;
  }

  public void setCor(Cor cor) {
    this.cor = cor;
  }
}
