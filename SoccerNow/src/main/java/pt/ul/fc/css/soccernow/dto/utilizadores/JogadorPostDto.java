package pt.ul.fc.css.soccernow.dto.utilizadores;

import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

public class JogadorPostDto {

    private UtilizadorDto utilizador;

    private Posicao posicaoPreferida;

    public JogadorPostDto() {}

    public UtilizadorDto getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UtilizadorDto utilizador) {
        this.utilizador = utilizador;
    }

    public Posicao getPosicaoPreferida() {
        return posicaoPreferida;
    }

    public void setPosicaoPreferida(Posicao posicaoPreferida) {
        this.posicaoPreferida = posicaoPreferida;
    }
}