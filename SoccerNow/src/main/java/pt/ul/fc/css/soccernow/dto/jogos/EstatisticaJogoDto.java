package pt.ul.fc.css.soccernow.dto.jogos;

import java.util.List;

/**
 * Classe Dto para representar as estatisticas de um jogo.
 * Para:
 * - Registar o Resultado de um jogo:
 * - - os golos devem estar preenchidos.
 */
public class EstatisticaJogoDto {
    List<GoloDto> golos;
    List<CartaoDto> cartoes;
}
