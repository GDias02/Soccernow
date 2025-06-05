package pt.ul.fc.di.css.javafxexample.dto.campeonatos;

public class CampeonatoEliminatoriaDto {
  // TODO
  private boolean dummy = true; //this is needed so Jackson won't throw an error

  public CampeonatoEliminatoriaDto() {}

  public boolean isDummy() {
    return dummy;
  }

  public void setDummy(boolean dummy) {
    this.dummy = dummy;
  }
}
