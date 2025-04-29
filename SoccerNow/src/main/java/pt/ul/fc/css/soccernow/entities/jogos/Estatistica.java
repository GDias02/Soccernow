package pt.ul.fc.css.soccernow.entities.jogos;

import java.util.List;

public abstract class Estatistica {
    List<Golo> golos;
    List<Cartao> cartoes;

    public List<Golo> getGolos() {
        return golos;
    }
    public void setGolos(List<Golo> golos) {
        this.golos = golos;
    }
    public List<Cartao> getCartoes() {
        return cartoes;
    }
    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }
}
