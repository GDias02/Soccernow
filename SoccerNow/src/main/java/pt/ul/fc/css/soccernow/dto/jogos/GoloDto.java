package pt.ul.fc.css.soccernow.dto.jogos;

import java.sql.Timestamp;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;

/**
 * Classe Dto para representar um golo marcado num jogo.
 * Para:
 * - Registar o resultado de um jogo:
 * - - Todos os atributos devem estar preenchidos, excepto JogoDto. Esse nao é necessario.
 * - - O atributo marcador e equipa requerem apenas os ids respetivos.
 */
public class GoloDto {
    private Timestamp quando;
    private JogoDto jogo;
    private JogadorDto marcador;
    private EquipaDto equipa;
    
    public Timestamp getQuando() {
        return quando;
    }
    public void setQuando(Timestamp quando) {
        this.quando = quando;
    }
    public JogoDto getJogo() {
        return jogo;
    }
    public void setJogo(JogoDto jogo) {
        this.jogo = jogo;
    }
    public JogadorDto getMarcador() {
        return marcador;
    }
    public void setMarcador(JogadorDto marcador) {
        this.marcador = marcador;
    }
    public EquipaDto getEquipa() {
        return equipa;
    }
    public void setEquipa(EquipaDto equipa) {
        this.equipa = equipa;
    }

}
