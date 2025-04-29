package pt.ul.fc.css.soccernow.dto.jogos;

import java.sql.Timestamp;

import pt.ul.fc.css.soccernow.entities.jogos.Cor;

public class CartaoDto {
    private Timestamp quando;
    private Long jogo;
    private Long atribuidoA;
    private Long arbitro;
    private Cor cor;
    
    public CartaoDto() {}
    
    public CartaoDto(Timestamp quando, Long jogo, Long atribuidoA, Long arbitro, Cor cor) {
        this.quando = quando;
        this.jogo = jogo;
        this.atribuidoA = atribuidoA;
        this.arbitro = arbitro;
        this.cor = cor;
    }
    public Timestamp getQuando() {
        return quando;
    }

    public void setQuando(Timestamp quando) {
        this.quando = quando;
    }

    public Long getJogo() {
        return jogo;
    }

    public void setJogo(Long jogo) {
        this.jogo = jogo;
    }

    public Long getAtribuidoA() {
        return atribuidoA;
    }

    public void setAtribuidoA(Long atribuidoA) {
        this.atribuidoA = atribuidoA;
    }

    public Long getArbitro() {
        return arbitro;
    }

    public void setArbitro(Long arbitro) {
        this.arbitro = arbitro;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

}
