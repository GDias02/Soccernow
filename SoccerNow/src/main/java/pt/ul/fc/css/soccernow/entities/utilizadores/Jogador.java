package pt.ul.fc.css.soccernow.entities.utilizadores;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;

@Entity
public class Jogador extends Utilizador implements IJogador {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Posicao posicaoPreferida;

    @Embedded
    private EstatisticaJogador estatisticas;

    public Jogador() {}

    public Jogador(Long id, int nif, String nome, String contacto, Posicao posicaoPreferida, EstatisticaJogador estatisticas) {
        super(id, nif, nome, contacto);
        this.posicaoPreferida = posicaoPreferida;
        this.estatisticas = estatisticas;
    }

    @Override
    public Posicao getPosicaoPreferida() {
        return this.posicaoPreferida;
    }

    @Override
    public void setPosicaoPreferida(Posicao posicaoPreferida) {
        this.posicaoPreferida = posicaoPreferida;
    }

    public EstatisticaJogador getEstatisticas() {
        return estatisticas;
    }

    public void setEstatisticas(EstatisticaJogador estatisticas) {
        this.estatisticas = estatisticas;
    }
}
