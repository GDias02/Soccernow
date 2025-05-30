package pt.ul.fc.di.css.javafxexample.dto.utilizadores;

public class JogadorPostDto {

    private UtilizadorDto utilizador;

    private Posicao posicaoPreferida;

    public JogadorPostDto() {}

    public JogadorPostDto(UtilizadorDto utilizador, Posicao posicaoPreferida) {
        this.utilizador = utilizador;
        this.posicaoPreferida = posicaoPreferida;
    }

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