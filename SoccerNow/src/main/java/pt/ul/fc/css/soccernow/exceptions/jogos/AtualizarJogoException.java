package pt.ul.fc.css.soccernow.exceptions.jogos;

public class AtualizarJogoException extends RuntimeException {
  public AtualizarJogoException(String errorMessage) {
    super(errorMessage);
  }

  public AtualizarJogoException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
