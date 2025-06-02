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
    int jogos;

    public EstatisticaJogadorDto() {}
    
    public EstatisticaJogadorDto(Set<GoloDto> golos, Set<CartaoDto> cartoes, int jogos) {
        this.golos = golos;
        this.cartoes = cartoes;
        this.jogos = jogos;
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

    public int getJogos() {
        return jogos;
    }

    public void setJogos(int jogos) {
        this.jogos = jogos;
    }
}
