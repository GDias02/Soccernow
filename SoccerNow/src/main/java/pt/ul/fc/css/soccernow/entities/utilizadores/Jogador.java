package pt.ul.fc.css.soccernow.entities.utilizadores;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;

@Entity
public class Jogador extends Utilizador implements IJogador {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Posicao posicaoPreferida;

    @OneToOne
    private EstatisticaJogador estatisticas;

    public Jogador() {}

    @Override
    public Posicao getPosicaoPreferida() {
        return this.posicaoPreferida;
    }

    @Override
    public void setPosicaoPreferida(Posicao posicaoPreferida) {
        this.posicaoPreferida = posicaoPreferida;
    }

    @Override
    public EstatisticaJogador getEstatisticas() {
        return this.estatisticas;
    }

    @Override
    public void setEstatisticas(EstatisticaJogador estatisticas) {
        this.estatisticas = estatisticas;
    }
}
