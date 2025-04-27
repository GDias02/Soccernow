package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Timestamp;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.entities.utilizadores.IArbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;

enum Cor {
    AMARELO, VERMELHO
}

public class Cartao extends EventoDeJogo {
    @Column(nullable = false)
    private IJogador atribuido;

    @Column(nullable = false)
    private IArbitro arbitro;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Cor cor;

    public Cartao(Timestamp quando, IJogo jogo, IJogador jogador, IArbitro arbitro, Cor cor) {
        super(quando, jogo);
        this.atribuido = jogador;
        this.arbitro = arbitro;
        this.cor = cor;
    }
}
