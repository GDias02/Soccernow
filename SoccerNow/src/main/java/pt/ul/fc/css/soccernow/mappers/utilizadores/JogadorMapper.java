package pt.ul.fc.css.soccernow.mappers.utilizadores;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cartao;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.mappers.jogos.CartaoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.GoloMapper;

public class JogadorMapper {

    public static JogadorDto jogadorToDto(Jogador jogador) {
        JogadorDto jogadorDto = new JogadorDto();

        UtilizadorDto utilizador = new UtilizadorDto();
        utilizador.setId(jogador.getId());
        utilizador.setNif(jogador.getNif());
        utilizador.setNome(jogador.getNome());
        utilizador.setContacto(jogador.getContacto());
        jogadorDto.setUtilizador(utilizador);

        jogadorDto.setPosicaoPreferida(jogador.getPosicaoPreferida());

        EstatisticaJogadorDto estatisticasDto = new EstatisticaJogadorDto();
        EstatisticaJogador estatisticas = jogador.getEstatisticas();
        if (estatisticas != null) {
            Set<Golo> golos = estatisticas.getGolos();
            if (golos == null) golos = new HashSet<>();
            Set<Cartao> cartoes = estatisticas.getCartoes();
            if (cartoes == null) cartoes = new HashSet<>();
            
            estatisticasDto.setGolos(golos.stream().map(GoloMapper::goloToDto).collect(Collectors.toSet()));
            estatisticasDto.setCartoes(cartoes.stream().map(CartaoMapper::cartaoToDto).collect(Collectors.toSet()));
        }
        jogadorDto.setEstatisticas(estatisticasDto);

        return jogadorDto;
    }

    public static Jogador dtoToJogador(JogadorDto jogadorDto) {
        Jogador jogador = new Jogador();

        UtilizadorDto utilizador = jogadorDto.getUtilizador();
        jogador.setId(utilizador.getId());
        jogador.setNif(utilizador.getNif());
        jogador.setNome(utilizador.getNome());
        jogador.setContacto(utilizador.getContacto());

        jogador.setPosicaoPreferida(jogadorDto.getPosicaoPreferida());

        EstatisticaJogadorDto estatisticasDto = jogadorDto.getEstatisticas();
        EstatisticaJogador estatisticas = jogador.getEstatisticas();
        if (estatisticas == null) estatisticas = new EstatisticaJogador();
        estatisticas.setGolos(estatisticasDto.getGolos().stream().map(GoloMapper::dtoToGolo).collect(Collectors.toSet()));
        estatisticas.setCartoes(estatisticasDto.getCartoes().stream().map(CartaoMapper::dtoToCartao).collect(Collectors.toSet()));
        jogador.setEstatisticas(estatisticas);

        return jogador;
    }
}
