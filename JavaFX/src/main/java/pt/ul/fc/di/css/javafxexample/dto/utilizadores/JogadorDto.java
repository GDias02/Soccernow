package pt.ul.fc.di.css.javafxexample.dto.utilizadores;

import java.util.HashSet;

import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;

public class JogadorDto {

    private UtilizadorDto utilizador;

    private Posicao posicaoPreferida;

    private EstatisticaJogadorDto estatisticas;

    public JogadorDto() {}

    public JogadorDto(UtilizadorDto utilizador, Posicao posicaoPreferida, EstatisticaJogadorDto estatisticas) {
        this.utilizador = utilizador;
        this.posicaoPreferida = posicaoPreferida;
        this.estatisticas = estatisticas;
    }

    public JogadorDto(JogadorPostDto jogadorPostDto) {
        this.utilizador = jogadorPostDto.getUtilizador();
        this.posicaoPreferida = jogadorPostDto.getPosicaoPreferida();

        EstatisticaJogadorDto estatisticasDto = new EstatisticaJogadorDto();
        estatisticasDto.setGolos(new HashSet<>());
        estatisticasDto.setCartoes(new HashSet<>());
        this.estatisticas = estatisticasDto;
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

    public EstatisticaJogadorDto getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticaJogadorDto estatisticas) {
        this.estatisticas = estatisticas;
    }
}