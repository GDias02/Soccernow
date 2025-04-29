package pt.ul.fc.css.soccernow.dto.jogos;

import java.util.Map;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

/**
 * Classe Dto para representar uma selecao de uma equipa para determinado jogo.
 * Para:
 * - Criar um novo jogo:
 * - - O atributo de tipo EquipaDto requer apenas com o id e o nome preenchido (para efeitos do placar).
 * - - O parametro de capitao e opcional
 * - - Os atributos de tipo JogadorDto requerem apenas o id
 * - - Os atributos das posicoes dos jogadores podem ser colocados recorrendo a setSelecao(Map<Posicao,JogadorDto>).
 */
public class SelecaoDto {
    private static final Posicao[] posicoes = Posicao.values();
    private EquipaDto equipa;

    private JogadorDto capitao;

    private JogadorDto guardaRedes;

    private JogadorDto fixo;

    private JogadorDto alaEsquerda;

    private JogadorDto alaDireita;

    private JogadorDto pivot;

    public void setSelecao(Map<Posicao,JogadorDto> jogadores){
    this.guardaRedes = jogadores.get(posicoes[0]);
    this.fixo = jogadores.get(posicoes[1]);
    this.alaEsquerda = jogadores.get(posicoes[2]);
    this.alaDireita = jogadores.get(posicoes[3]);
    this.pivot = jogadores.get(posicoes[4]);
    }

    public static Posicao[] getPosicoes() {
        return posicoes;
    }

    public EquipaDto getEquipa() {
        return equipa;
    }

    public void setEquipa(EquipaDto equipa) {
        this.equipa = equipa;
    }

    public JogadorDto getCapitao() {
        return capitao;
    }

    public void setCapitao(JogadorDto capitao) {
        this.capitao = capitao;
    }

    public JogadorDto getGuardaRedes() {
        return guardaRedes;
    }

    public void setGuardaRedes(JogadorDto guardaRedes) {
        this.guardaRedes = guardaRedes;
    }

    public JogadorDto getFixo() {
        return fixo;
    }

    public void setFixo(JogadorDto fixo) {
        this.fixo = fixo;
    }

    public JogadorDto getAlaEsquerda() {
        return alaEsquerda;
    }

    public void setAlaEsquerda(JogadorDto alaEsquerda) {
        this.alaEsquerda = alaEsquerda;
    }

    public JogadorDto getAlaDireita() {
        return alaDireita;
    }

    public void setAlaDireita(JogadorDto alaDireita) {
        this.alaDireita = alaDireita;
    }

    public JogadorDto getPivot() {
        return pivot;
    }

    public void setPivot(JogadorDto pivot) {
        this.pivot = pivot;
    }
}