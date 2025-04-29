package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pt.ul.fc.css.soccernow.entities.campeonatos.ICampeonato;

@Entity
@DiscriminatorValue("1")
public class JogoCampeonato extends Jogo {
    @ManyToOne
    @JoinColumn(name = "campeonato_id", referencedColumnName = "id")
    private ICampeonato campeonato;

    public ICampeonato getCampeonato(){
        return this.campeonato;
    }
    public void setCampeonato(ICampeonato c){
        this.campeonato = c;
    }
}
