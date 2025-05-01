package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class JogoAmigavel extends Jogo {
    
}
