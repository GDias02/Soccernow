package pt.ul.fc.di.css.javafxexample.dto.jogos;

import java.util.Set;

/**
 * Classe Dto para representar as estatisticas de um jogo. Para: - Registar o Resultado de um jogo:
 * - - os golos devem estar preenchidos.
 */
public class EstatisticaJogoDto {
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

  @Override
  public String toString() {
    return "EstatisticaJogoDto [golos=" + golos + ", cartoes=" + cartoes + "]";
  }
}
