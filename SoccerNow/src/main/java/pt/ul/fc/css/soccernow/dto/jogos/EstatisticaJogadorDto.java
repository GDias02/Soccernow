package pt.ul.fc.css.soccernow.dto.jogos;

import java.util.List;

/**
 * Classe Dto para representar as estatisticas de um jogador.
 * Para:
 * - Buscar por Jogadores:
 * - - os golos e os cartoes devem estar preenchidos.
 */
public class EstatisticaJogadorDto {
    List<GoloDto> golos;
    List<CartaoDto> cartoes;
}
