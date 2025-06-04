package pt.ul.fc.di.css.javafxexample.dto.jogos;

import java.util.Set;

/**
 * Classe Dto para representar as estatisticas de um arbitro. Para: - Buscar por Arbitros: - - os
 * golos e os cartoes devem estar preenchidos.
 */
public class EstatisticaArbitroDto {
  Set<CartaoDto> cartoes;
  int jogos;

  public EstatisticaArbitroDto() {}

  public EstatisticaArbitroDto(Set<CartaoDto> cartoes, int jogos) {
    this.jogos = jogos;
    this.cartoes = cartoes;
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
