package pt.ul.fc.css.soccernow.exceptions.jogos;

public class JogoLocalException extends RuntimeException {
  public JogoLocalException(String errorMessage) {
    super(errorMessage);
  }

  public JogoLocalException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
