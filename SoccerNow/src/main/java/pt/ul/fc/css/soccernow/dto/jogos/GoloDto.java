package pt.ul.fc.css.soccernow.dto.jogos;

import java.time.LocalDateTime;

/**
 * Classe Dto para representar um golo marcado num jogo. Para: - Registar o resultado de um jogo: -
 * - Todos os atributos devem estar preenchidos, excepto JogoDto. Esse nao é necessario. - - O
 * atributo marcador e equipa requerem apenas os ids respetivos.
 */
public class GoloDto {
  private LocalDateTime quando;
  private Long jogo;
  private Long marcador;
  private Long equipa;

  public LocalDateTime getQuando() {
    return quando;
  }

  public void setQuando(LocalDateTime quando) {
    this.quando = quando;
  }

  public Long getJogo() {
    return jogo;
  }

  public void setJogo(Long jogo) {
    this.jogo = jogo;
  }

  public Long getMarcador() {
    return marcador;
  }

  public void setMarcador(Long marcador) {
    this.marcador = marcador;
  }

  public Long getEquipa() {
    return equipa;
  }

  public void setEquipa(Long equipa) {
    this.equipa = equipa;
  }
}
