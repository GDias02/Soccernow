package pt.ul.fc.css.soccernow.mappers.jogos;

import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

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

  public static Cartao createDtoToCartao(
      CartaoDto c,
      JogadorRepository jRep,
      EquipaRepository eRep,
      JogoRepository jogoRep,
      ArbitroRepository aRep) {
    if (c == null) {
      return null;
    }
    Cartao cartao = new Cartao();
    cartao.setQuando(c.getQuando());
    cartao.setJogo(jogoRep.getReferenceById(c.getJogo()));
    cartao.setAtribuidoA(jRep.getReferenceById(c.getAtribuidoA()));
    cartao.setEquipa(eRep.getReferenceById(c.getEquipa()));
    cartao.setArbitro(aRep.getReferenceById(c.getArbitro()));

    cartao.setQuando(c.getQuando());
    cartao.setCor(c.getCor());
    return cartao;
  }
}
