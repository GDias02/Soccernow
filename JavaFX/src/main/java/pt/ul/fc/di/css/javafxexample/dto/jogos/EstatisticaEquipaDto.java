package pt.ul.fc.di.css.javafxexample.dto.jogos;

import java.util.Set;
/**
 * Classe Dto para representar as estatisticas de um jogador.
 * Para:
 * - Buscar por equipas
 * - - Os golos e os cartoes devem estar preenchidos.
 */
public class EstatisticaEquipaDto {
    Set<GoloDto> golos;
    Set<CartaoDto> cartoes;
    public Set<GoloDto> getGolos() {
        return golos;
    }
    public void setGolos(Set<GoloDto> golos) {
        this.golos = golos;
    }
    public Set<CartaoDto> getCartoes() {
        return cartoes;
    }
    public void setCartoes(Set<CartaoDto> cartoes) {
        this.cartoes = cartoes;
    }
}
