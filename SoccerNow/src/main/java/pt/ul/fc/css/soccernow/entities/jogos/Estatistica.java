package pt.ul.fc.css.soccernow.entities.jogos;

import java.util.Set;

public abstract class Estatistica {
    Set<Golo> golos;
    Set<Cartao> cartoes;

    public Set<Golo> getGolos() {
        return golos;
    }
    public void setGolos(Set<Golo> golos) {
        this.golos = golos;
    }
    public Set<Cartao> getCartoes() {
        return cartoes;
    }
    public void setCartoes(Set<Cartao> cartoes) {
        this.cartoes = cartoes;
    }
}
