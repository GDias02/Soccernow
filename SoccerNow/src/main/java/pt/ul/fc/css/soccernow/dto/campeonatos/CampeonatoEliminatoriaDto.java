package pt.ul.fc.css.soccernow.dto.campeonatos;

import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoEliminatoria;

public class CampeonatoEliminatoriaDto {
  // TODO
  private boolean dummy = true; //this is needed so Jackson won't throw an error
  
  public CampeonatoEliminatoriaDto(CampeonatoEliminatoria campeonato) {}

  public CampeonatoEliminatoriaDto() {}

  public boolean isDummy() {
    return dummy;
  }

  public void setDummy(boolean dummy) {
    this.dummy = dummy;
  }
}
