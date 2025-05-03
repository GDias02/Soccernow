package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.stream.Collectors;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogo;

public class EstatisticaMapper {
  public static EstatisticaJogoDto estatisticaJogoToDto(EstatisticaJogo stat) {
    if (stat == null) {
      return null;
    }
    EstatisticaJogoDto statDto = new EstatisticaJogoDto();
    statDto.setCartoes(
        stat.getCartoes().stream().map(CartaoMapper::cartaoToDto).collect(Collectors.toSet()));
    statDto.setGolos(
        stat.getGolos().stream().map(GoloMapper::goloToDto).collect(Collectors.toSet()));
    return statDto;
  }

  public static EstatisticaJogo dtoToEstatisticaJogo(EstatisticaJogoDto stats) {
    if (stats == null) {
      return null;
    }
    EstatisticaJogo stat = new EstatisticaJogo();
    stat.setCartoes(
        stats.getCartoes().stream().map(CartaoMapper::dtoToCartao).collect(Collectors.toSet()));
    stat.setGolos(stats.getGolos().stream().map(GoloMapper::dtoToGolo).collect(Collectors.toSet()));
    return stat;
  }
}
