package pt.ul.fc.css.soccernow.mappers.jogos;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.entities.jogos.Selecao;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.exceptions.jogos.SelecaoException;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.SelecaoRepository;

public class SelecaoMapper {

  public static SelecaoDto selecaoToDto(Selecao selecao) {
    if (selecao == null) {
      return null;
    }
    SelecaoDto selecaoDto = new SelecaoDto();
    selecaoDto.setId(selecao.getId());
    selecaoDto.setEquipa(selecao.getEquipa().getId());
    selecaoDto.setCapitao(selecao.getCapitao().getId());
    selecaoDto.setJogo(selecao.getJogo().getId());

    Map<Posicao, Jogador> jogs = selecao.getJogadores();
    Map<Posicao, Long> jogadores = new EnumMap<>(Posicao.class);
    jogs.forEach((k, v) -> jogadores.put(k, v.getId()));
    selecaoDto.setJogadores(jogadores);

    return selecaoDto;
  }

  public static Selecao dtoToSelecao(
      SelecaoDto selecaodto, SelecaoRepository sRep, EquipaRepository eRep)
      throws SelecaoException {

    if (selecaodto == null) return null;
    if (selecaodto.getJogo() != null && selecaodto.getJogo() > 0) {
      throw new SelecaoException("Não pode criar uma seleção nova para um jogo existente");
    }
    Selecao selecao = new Selecao();
    System.out.println("\n\nSELECAO DTO A CONVERTER: \n\n" + selecaodto + "\n\n");

    if (selecaodto.getId() != null && selecaodto.getId() > 0) {
      Optional<Selecao> maybeSelecao = sRep.findById(selecaodto.getId());
      if (maybeSelecao.isPresent()) {
        return maybeSelecao.get();
      } else {
        throw new SelecaoException("Foi fornecido um id de uma selecao que nao existe.");
      }
    }

    if (selecaodto.getCapitao() != null && selecaodto.getCapitao() > 0) {
      Jogador j = selecao.getEquipa().getJogador(selecaodto.getCapitao());
      if (j == null) throw new SelecaoException("O capitao nao pertence à equipa desta seleção");
      selecao.setCapitao(j);
    } else {
      throw new SelecaoException("Falta definir o capitao da seleção!");
    }
    if (selecaodto.getAlaDireita() != null && selecaodto.getAlaDireita() > 0) {
      Jogador j = selecao.getEquipa().getJogador(selecaodto.getAlaDireita());
      if (j == null)
        throw new SelecaoException("O ala direita nao pertence à equipa desta seleção");
      selecao.setAlaDireita(j);
    } else {
      throw new SelecaoException("Falta definir a ala direita da seleção!");
    }
    if (selecaodto.getAlaEsquerda() != null && selecaodto.getAlaEsquerda() > 0) {
      Jogador j = selecao.getEquipa().getJogador(selecaodto.getAlaEsquerda());
      if (j == null)
        throw new SelecaoException("O ala esquerda nao pertence à equipa desta seleção");
      selecao.setAlaEsquerda(j);
    } else {
      throw new SelecaoException("Falta definir a ala esquerda da seleção!");
    }
    if (selecaodto.getPivot() != null && selecaodto.getPivot() > 0) {
      Jogador j = selecao.getEquipa().getJogador(selecaodto.getPivot());
      if (j == null) throw new SelecaoException("O pivot nao pertence à equipa desta seleção");
      selecao.setPivot(j);
    } else {
      throw new SelecaoException("Falta definir o pivot da seleção!");
    }
    if (selecaodto.getGuardaRedes() != null && selecaodto.getGuardaRedes() > 0) {
      Jogador j = selecao.getEquipa().getJogador(selecaodto.getGuardaRedes());
      if (j == null)
        throw new SelecaoException("O guarda-redes nao pertence à equipa desta seleção");
      selecao.setGuardaRedes(j);
    } else {
      throw new SelecaoException("Falta definir o guarda-redes da seleção!");
    }
    if (selecaodto.getFixo() != null && selecaodto.getFixo() > 0) {
      Jogador j = selecao.getEquipa().getJogador(selecaodto.getFixo());
      if (j == null) throw new SelecaoException("O fixo nao pertence à equipa desta seleção");
      selecao.setFixo(j);
    } else {
      throw new SelecaoException("Falta definir o fixo da seleção!");
    }

    return selecao;
  }
}
