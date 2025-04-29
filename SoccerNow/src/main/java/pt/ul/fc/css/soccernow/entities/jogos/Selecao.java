package pt.ul.fc.css.soccernow.entities.jogos;

import java.util.EnumMap;
import java.util.Map;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

@Embeddable
public class Selecao {
    private static final Posicao[] posicoes = Posicao.values();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipaId")
    private IEquipa equipa;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogadorId")
    private IJogador capitao;

    @OneToOne(fetch = FetchType.LAZY)       //de modo a evitar criar uma nova tabela que teria um tamanho fixo (uma vez que as posicoes sao sempre so 5)
    @JoinColumn(name="guarda_redes", referencedColumnName="jogadorId")
    private IJogador guardaRedes;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixo", referencedColumnName = "jogadorId")
    private IJogador fixo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ala_esquerda", referencedColumnName = "jogadorId")
    private IJogador alaEsquerda;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ala_direita", referencedColumnName = "jogadorId")
    private IJogador alaDireita;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pivot", referencedColumnName = "jogadorId")
    private IJogador pivot;

    public Selecao(IEquipa e, IJogador capitao, Map<Posicao,IJogador> jogadores){
        this.equipa = e;
        this.capitao = capitao;
        setSelecao(jogadores);
    }
    
    public Map<Posicao, IJogador> getJogadores(){
        Map<Posicao, IJogador> jogadores = new EnumMap<>(Posicao.class);
        jogadores.put(posicoes[0], guardaRedes);
        jogadores.put(posicoes[1], fixo);
        jogadores.put(posicoes[2], alaEsquerda);
        jogadores.put(posicoes[3], alaDireita);
        jogadores.put(posicoes[4], pivot);
        return jogadores;
    }
    public void setSelecao(Map<Posicao,IJogador> jogadores){
        this.guardaRedes = jogadores.get(posicoes[0]);
        this.fixo = jogadores.get(posicoes[1]);
        this.alaEsquerda = jogadores.get(posicoes[2]);
        this.alaDireita = jogadores.get(posicoes[3]);
        this.pivot = jogadores.get(posicoes[4]);
    }

    public IEquipa getEquipa() {
        return equipa;
    }

    public void setEquipa(IEquipa equipa) {
        this.equipa = equipa;
    }

    public IJogador getCapitao() {
        return capitao;
    }

    public void setCapitao(IJogador capitao) {
        this.capitao = capitao;
    }

    public Posicao[] getPosicoes() {
        return posicoes;
    }

    public IJogador getGuardaRedes() {
        return guardaRedes;
    }

    public void setGuardaRedes(IJogador guardaRedes) {
        this.guardaRedes = guardaRedes;
    }

    public IJogador getFixo() {
        return fixo;
    }

    public void setFixo(IJogador fixo) {
        this.fixo = fixo;
    }

    public IJogador getAlaEsquerda() {
        return alaEsquerda;
    }

    public void setAlaEsquerda(IJogador alaEsquerda) {
        this.alaEsquerda = alaEsquerda;
    }

    public IJogador getAlaDireita() {
        return alaDireita;
    }

    public void setAlaDireita(IJogador alaDireita) {
        this.alaDireita = alaDireita;
    }

    public IJogador getPivot() {
        return pivot;
    }

    public void setPivot(IJogador pivot) {
        this.pivot = pivot;
    }
    

}
