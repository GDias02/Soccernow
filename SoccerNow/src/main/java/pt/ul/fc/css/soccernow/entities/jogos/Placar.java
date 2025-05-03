package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class Placar {
  @Transient String equipa1;
  @Transient String equipa2;

  int pontuacao1;
  int pontuacao2;

  public Placar(String equipa1, String equipa2) {
    this.equipa1 = equipa1;
    this.equipa2 = equipa2;
    this.pontuacao1 = 0;
    this.pontuacao2 = 0;
  }

  public Placar() {
    // TODO Auto-generated constructor stub
  }

  public void setScore(int pontuacao1, int pontuacao2) {
    this.pontuacao1 = pontuacao1;
    this.pontuacao2 = pontuacao2;
  }

  public int getPontuacao1() {
    return pontuacao1;
  }

  public int getPontuacao2() {
    return pontuacao2;
  }

  public String getShortScore() {
    return (pontuacao1 + " - " + pontuacao2);
  }

  @Override
  public String toString() {
    return (equipa1 + ": " + pontuacao1 + "\n" + equipa2 + ": " + pontuacao2);
  }
}
