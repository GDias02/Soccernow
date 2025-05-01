package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Timestamp;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

public class Cartao extends EventoDeJogo {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false ,name = "atribuido", referencedColumnName = "jogadorId")
    private Jogador atribuido;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false ,name = "arbitro", referencedColumnName = "arbitroId")
    private Arbitro arbitro;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Cor cor;

    public Cartao(Timestamp quando, Jogo jogo, Jogador jogador, Arbitro arbitro, Cor cor) {
        super(quando, jogo);
        this.atribuido = jogador;
        this.arbitro = arbitro;
        this.cor = cor;
    }

    public Cartao() {
        //TODO Auto-generated constructor stub
    }

    public Jogador getAtribuido() {
        return atribuido;
    }

    public void setAtribuido(Jogador atribuido) {
        this.atribuido = atribuido;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}
