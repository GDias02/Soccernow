package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.stream.Collectors;

import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaArbitroDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaArbitro;

public class EstatisticaArbitroMapper {
    public static EstatisticaArbitroDto estatisticaArbitroToDto(EstatisticaArbitro stat) {
    if (stat == null) {
      return null;
    }
    EstatisticaArbitroDto statDto = new EstatisticaArbitroDto();
    statDto.setCartoes(
        stat.getCartoes().stream().map(CartaoMapper::cartaoToDto).collect(Collectors.toSet()));
    statDto.setJogos(stat.getJogos());
    return statDto;
  }

  public static EstatisticaArbitro dtoToEstatisticaArbitro(EstatisticaArbitroDto stats) {
    if (stats == null) {
      return null;
    }
    EstatisticaArbitro stat = new EstatisticaArbitro();
    stat.setCartoes(
        stats.getCartoes().stream().map(CartaoMapper::dtoToCartao).collect(Collectors.toSet()));
    stat.setGolos(null);
    stat.setJogos(stats.getJogos());
    return stat;
  }
}
