package pt.ul.fc.css.soccernow.entities.jogos;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;

@Entity
public class Golo extends EventoDeJogo {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false ,name = "marcador")
    private Jogador marcador;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false ,name = "equipa_marcadora")
    private Equipa equipa;

    public Golo(LocalDateTime quando, Jogo jogo, Jogador marcador, Equipa equipa) {
        super(quando, jogo);
        this.marcador = marcador;
        this.equipa = equipa;
    }

    public Golo() {
        //TODO Auto-generated constructor stub
    }

    public Jogador getMarcador() {
        return marcador;
    }

    public void setMarcador(Jogador marcador) {
        this.marcador = marcador;
    }
    
    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

}
