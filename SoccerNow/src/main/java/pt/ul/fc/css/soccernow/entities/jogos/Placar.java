package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class Placar {

  @Transient Long equipa1;
  @Transient Long equipa2;

  Long equipaVencedora;

  int pontuacao1;
  int pontuacao2;

  public Placar() {}

  public Placar(Long e1, Long e2) {
    this.equipa1 = e1;
    this.equipa2 = e2;
    this.equipaVencedora = 0L; // 0 indica empate ou jogo ainda nao terminado

    this.pontuacao1 = 0;
    this.pontuacao2 = 0;
  }

  public void setScore(int pontuacao1, int pontuacao2) {
    this.pontuacao1 = pontuacao1;
    this.pontuacao2 = pontuacao2;
    this.equipaVencedora = calculateVencedor();
  }

  public int getPontuacao1() {
    return pontuacao1;
  }

  public int getPontuacao2() {
    return pontuacao2;
  }

  public Long getEquipaVencedora() {
    return this.equipaVencedora;
  }

  public String getShortScore() {
    return (pontuacao1 + " - " + pontuacao2);
  }

  public Long calculateVencedor() {
    if (this.pontuacao1 > this.pontuacao2) {
      return this.equipa1;
    }
    if (this.pontuacao2 > this.pontuacao1) {
      return this.equipa2;
    }
    return 0L;
  }

  @Override
  public String toString() {
    return ("Pontuacao: " + pontuacao1 + "-" + pontuacao2 + " > vencedor: " + this.equipaVencedora);
  }
}
