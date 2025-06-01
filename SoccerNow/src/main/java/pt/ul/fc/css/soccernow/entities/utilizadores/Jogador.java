package pt.ul.fc.css.soccernow.entities.utilizadores;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@SQLDelete(sql = "UPDATE jogador SET deleted = true, version = version + 1 WHERE utilizador_id=? AND version=?")
public class Jogador extends Utilizador implements IJogador {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Posicao posicaoPreferida;

    private boolean deleted = Boolean.FALSE;

    public Jogador() {}

    public Jogador(Long id, int nif, String nome, String contacto, Posicao posicaoPreferida) {
        super(id, nif, nome, contacto);
        this.posicaoPreferida = posicaoPreferida;
    }

    @Override
    public Posicao getPosicaoPreferida() {
        return this.posicaoPreferida;
    }

    @Override
    public void setPosicaoPreferida(Posicao posicaoPreferida) {
        this.posicaoPreferida = posicaoPreferida;
    }
}
