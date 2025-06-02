package pt.ul.fc.css.soccernow.entities.jogos;

import pt.ul.fc.css.soccernow.entities.equipas.Equipa;

public class EstatisticaJogo extends Estatistica {

  private Jogo jogo;

  public Placar getPlacar() {
    Equipa e1 = jogo.getEquipa1();
    Equipa e2 = jogo.getEquipa2();
    int golos1 =
        (int) super.getGolos().stream().filter(golo -> golo.getEquipa().equals(e1)).count();
    int golos2 = super.getGolos().size() - golos1;
    Placar p = new Placar(e1.getId(), e2.getId());
    p.setScore(golos1, golos2);
    return p;
  }

  public void setJogo(Jogo j) {
    this.jogo = j;
  }
}
