package pt.ul.fc.di.css.javafxexample.dto.jogos;

import java.util.Set;

public class EstatisticaArbitroDto {
    Set<CartaoDto> cartoes;
    int jogos;

    public EstatisticaArbitroDto() {}
    
    public EstatisticaArbitroDto(Set<CartaoDto> cartoes, int jogos) {
        this.cartoes = cartoes;
        this.jogos = jogos;
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
