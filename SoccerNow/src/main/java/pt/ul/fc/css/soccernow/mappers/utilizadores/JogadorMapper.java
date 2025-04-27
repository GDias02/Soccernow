package pt.ul.fc.css.soccernow.mappers.utilizadores;

import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

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

        EstatisticaJogadorDto estatisticasJogador = new EstatisticaJogadorDto();
        EstatisticaDto estatisticas = new EstatisticaDto();
        estatisticas.setGolos(jogador.getEstatisticas().getGolos());
        estatisticas.setCartoes(jogador.getEstatisticas().getCartoes());
        estatisticasJogador.setEstatisticas(estatisticas);
        jogadorDto.setEstatisticas(estatisticasJogador);

        return jogadorDto;
    }

    public static Jogador dtoToJogador(JogadorDto jogadorDto) {
        Jogador jogador = new Jogador();

        UtilizadorDto utilizador = jogadorDto.getUtilizador();
        jogador.setNif(utilizador.getNif());
        jogador.setNome(utilizador.getNome());
        jogador.setContacto(utilizador.getContacto());

        jogador.setPosicaoPreferida(jogadorDto.getPosicaoPreferida());

        EstatisticaJogador estatisticaJogador = new EstatisticaJogador();
        EstatisticaJogadorDto estatisticaJogadorDto = jogadorDto.getEstatisticas();
        estatisticaJogador.setGolos(estatisticaJogadorDto.getGolos());
        estatisticaJogador.setCartoes(estatisticaJogadorDto.getCartoes());
        jogador.setEstatisticas(estatisticaJogador);

        return jogador;
    }
}
