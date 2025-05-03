package pt.ul.fc.css.soccernow.dto.jogos;

import java.util.Set;

/**
 * Classe Dto para representar as estatisticas de um jogador.
 * Para:
 * - Buscar por Jogadores:
 * - - os golos e os cartoes devem estar preenchidos.
 */
public class EstatisticaJogadorDto {
    Set<GoloDto> golos;
    Set<CartaoDto> cartoes;

    public EstatisticaJogadorDto() {}
    
    public EstatisticaJogadorDto(Set<GoloDto> golos, Set<CartaoDto> cartoes) {
        this.golos = golos;
        this.cartoes = cartoes;
    }

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
