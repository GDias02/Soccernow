package pt.ul.fc.css.soccernow.dto.jogos;

import java.sql.Timestamp;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.Cor;

public class CartaoDto {
    private Timestamp quando;
    private JogoDto jogo;
    private JogadorDto atribuidoA;
    private ArbitroDto arbitro;
    private Cor cor;
    
    public CartaoDto() {}

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

    public JogadorDto getAtribuidoA() {
        return atribuidoA;
    }

    public void setAtribuidoA(JogadorDto atribuidoA) {
        this.atribuidoA = atribuidoA;
    }

    public ArbitroDto getArbitro() {
        return arbitro;
    }

    public void setArbitro(ArbitroDto arbitro) {
        this.arbitro = arbitro;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

}
