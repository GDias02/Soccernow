package pt.ul.fc.css.soccernow.dto.jogos;

import java.util.List;
/**
 * Classe Dto para representar as estatisticas de um jogador.
 * Para:
 * - Buscar por equipas
 * - - Os golos e os cartoes devem estar preenchidos.
 */
public class EstatisticaEquipaDto {
    List<GoloDto> golos;
    List<CartaoDto> cartoes;
}
