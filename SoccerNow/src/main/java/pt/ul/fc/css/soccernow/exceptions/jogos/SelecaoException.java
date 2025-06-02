package pt.ul.fc.css.soccernow.exceptions.jogos;

public class SelecaoException extends RuntimeException {
  public SelecaoException(String errorMessage) {
    super(errorMessage);
  }

  public SelecaoException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
