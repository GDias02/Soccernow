package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.stream.Collectors;

import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;

public class EstatisticaJogadorMapper {

  public static EstatisticaJogadorDto estatisticaJogadorToDto(EstatisticaJogador stat) {
    if (stat == null) {
      return null;
    }
    EstatisticaJogadorDto statDto = new EstatisticaJogadorDto();
    statDto.setCartoes(
        stat.getCartoes().stream().map(CartaoMapper::cartaoToDto).collect(Collectors.toSet()));
    statDto.setGolos(
        stat.getGolos().stream().map(GoloMapper::goloToDto).collect(Collectors.toSet()));
    statDto.setJogos(stat.getJogos());
    return statDto;
  }

  public static EstatisticaJogador dtoToEstatisticaJogador(EstatisticaJogadorDto stats) {
    if (stats == null) {
      return null;
    }
    EstatisticaJogador stat = new EstatisticaJogador();
    stat.setCartoes(
        stats.getCartoes().stream().map(CartaoMapper::dtoToCartao).collect(Collectors.toSet()));
    stat.setGolos(stats.getGolos().stream().map(GoloMapper::dtoToGolo).collect(Collectors.toSet()));
    stat.setJogos(stats.getJogos());
    return stat;
  }
}
