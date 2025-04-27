package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Timestamp;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;

@Entity
public class Golo extends EventoDeJogo {

    @Column(nullable = false)
    private IJogador marcador;

    @Column(nullable = false)
    private IEquipa equipa;

    public Golo(Timestamp quando, IJogo jogo, IJogador marcador, IEquipa equipa) {
        super(quando, jogo);
        this.marcador = marcador;
        this.equipa = equipa;
    }

    public IJogador getMarcador() {
        return marcador;
    }

    public void setMarcador(IJogador marcador) {
        this.marcador = marcador;
    }
    
    public IEquipa getEquipa() {
        return equipa;
    }

    public void setEquipa(IEquipa equipa) {
        this.equipa = equipa;
    }

}
