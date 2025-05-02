package pt.ul.fc.css.soccernow.mappers.utilizadores;

import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

public class JogadorPostMapper {

    public static JogadorPostDto jogadorToDto(Jogador jogador) {
        JogadorPostDto JogadorPostDto = new JogadorPostDto();

        UtilizadorDto utilizador = new UtilizadorDto();
        utilizador.setId(jogador.getId());
        utilizador.setNif(jogador.getNif());
        utilizador.setNome(jogador.getNome());
        utilizador.setContacto(jogador.getContacto());
        JogadorPostDto.setUtilizador(utilizador);

        JogadorPostDto.setPosicaoPreferida(jogador.getPosicaoPreferida());

        return JogadorPostDto;
    }

    public static Jogador dtoToJogador(JogadorPostDto JogadorPostDto) {
        Jogador jogador = new Jogador();

        UtilizadorDto utilizador = JogadorPostDto.getUtilizador();
        jogador.setId(utilizador.getId());
        jogador.setNif(utilizador.getNif());
        jogador.setNome(utilizador.getNome());
        jogador.setContacto(utilizador.getContacto());

        jogador.setPosicaoPreferida(JogadorPostDto.getPosicaoPreferida());

        return jogador;
    }
}
