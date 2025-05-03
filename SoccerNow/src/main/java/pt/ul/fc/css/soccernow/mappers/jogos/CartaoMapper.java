package pt.ul.fc.css.soccernow.mappers.jogos;

import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;

public class CartaoMapper {
  public static CartaoDto cartaoToDto(Cartao c) {
    if (c == null) {
      return null;
    }
    CartaoDto cartaoDto = new CartaoDto();
    cartaoDto.setQuando(c.getQuando());
    cartaoDto.setJogo(c.getJogo().getId());
    cartaoDto.setAtribuidoA(c.getAtribuidoA().getId());
    cartaoDto.setArbitro(c.getArbitro().getId());
    cartaoDto.setEquipa(c.getEquipa().getId());
    cartaoDto.setCor(c.getCor());
    return cartaoDto;
  }

  public static Cartao dtoToCartao(CartaoDto c) {
    if (c == null) {
      return null;
    }
    Cartao cartao = new Cartao();
    cartao.setQuando(c.getQuando());
    cartao.setCor(c.getCor());
    return cartao;
  }
}
