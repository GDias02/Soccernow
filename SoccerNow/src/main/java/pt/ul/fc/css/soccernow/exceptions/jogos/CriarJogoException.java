package pt.ul.fc.css.soccernow.exceptions.jogos;

public class CriarJogoException extends RuntimeException {
  public CriarJogoException(String errorMessage) {
    super(errorMessage);
  }

  public CriarJogoException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
