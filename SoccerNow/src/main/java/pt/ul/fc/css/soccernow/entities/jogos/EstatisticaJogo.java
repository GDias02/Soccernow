package pt.ul.fc.css.soccernow.entities.jogos;

import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;

public class EstatisticaJogo extends Estatistica {

    private IJogo jogo;

    public Placar getPlacar() {
        IEquipa e1 = jogo.getEquipa1();
        IEquipa e2 = jogo.getEquipa2();
        int golos1 = (int) super.getGolos().stream().filter(golo -> golo.getEquipa().equals(e1)).count();
        int golos2 = super.getGolos().size() - golos1;
        Placar p = new Placar(e1.toString(), e2.toString());
        p.setScore(golos1, golos2);
        return p;
    }
    
}
