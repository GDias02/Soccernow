package pt.ul.fc.css.soccernow.mappers.jogos;

import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;

public class CartaoMapper {
    public static CartaoDto cartaoToDto(Cartao c){
        if(c == null){return null;}
        CartaoDto cartaoDto = new CartaoDto();
        cartaoDto.setQuando(c.getQuando());
        cartaoDto.setJogo(JogoMapper.jogoToDto(c.getJogo()));
        cartaoDto.setAtribuidoA(JogadorMapper.jogadorToDto(c.getAtribuidoA()));
        cartaoDto.setArbitro(ArbitroMapper.arbitroToDto(c.getArbitro()));
        cartaoDto.setCor(c.getCor());
        return cartaoDto;
    }
    public static Cartao dtoToCartao(CartaoDto c){
        if(c == null){return null;}
        Cartao cartao = new Cartao();
        cartao.setQuando(c.getQuando());
        cartao.setJogo(JogoMapper.dtoToJogo(c.getJogo()));
        cartao.setAtribuidoA(JogadorMapper.dtoToJogador(c.getAtribuidoA()));
        cartao.setArbitro(ArbitroMapper.dtoToArbitro(c.getArbitro()));
        cartao.setCor(c.getCor());
        return cartao;
    }
}
