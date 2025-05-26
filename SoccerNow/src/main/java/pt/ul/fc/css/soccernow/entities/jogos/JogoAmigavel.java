package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;

@Entity
@DiscriminatorValue("0")
public class JogoAmigavel extends Jogo {

  @Override
  public Campeonato getCampeonato() {
    return null;
  }

  @Override
  public void setCampeonato(Campeonato c) {}
}
