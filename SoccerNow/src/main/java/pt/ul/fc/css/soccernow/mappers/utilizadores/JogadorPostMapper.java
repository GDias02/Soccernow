package pt.ul.fc.css.soccernow.mappers.utilizadores;

import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

public class JogadorPostMapper {

    public static JogadorPostDto jogadorToDto(Jogador jogador) {
        JogadorPostDto jogadorPostDto = new JogadorPostDto();

        UtilizadorDto utilizador = new UtilizadorDto();
        utilizador.setId(jogador.getId());
        utilizador.setNif(jogador.getNif());
        utilizador.setNome(jogador.getNome());
        utilizador.setContacto(jogador.getContacto());
        jogadorPostDto.setUtilizador(utilizador);

        jogadorPostDto.setPosicaoPreferida(jogador.getPosicaoPreferida());

        return jogadorPostDto;
    }

    public static Jogador dtoToJogador(JogadorPostDto jogadorPostDto) {
        Jogador jogador = new Jogador();

        UtilizadorDto utilizador = jogadorPostDto.getUtilizador();
        jogador.setId(utilizador.getId());
        jogador.setNif(utilizador.getNif());
        jogador.setNome(utilizador.getNome());
        jogador.setContacto(utilizador.getContacto());

        jogador.setPosicaoPreferida(jogadorPostDto.getPosicaoPreferida());

        return jogador;
    }
}
