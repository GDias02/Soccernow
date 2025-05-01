package pt.ul.fc.css.soccernow.entities.jogos;

import java.util.EnumMap;
import java.util.Map;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

@Embeddable
public class Selecao {
    private static final Posicao[] posicoes = Posicao.values();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipa_id", insertable = false, updatable = false)
    private Equipa equipa;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capitao_id", insertable = false, updatable = false)
    private Jogador capitao;

    @OneToOne       //de modo a evitar criar uma nova tabela que teria um tamanho fixo (uma vez que as posicoes sao sempre so 5)
    @JoinColumn(name="guarda_redes", insertable = false, updatable = false)
    private Jogador guardaRedes;
    @OneToOne
    @JoinColumn(name = "fixo", insertable = false, updatable = false)
    private Jogador fixo;
    @OneToOne
    @JoinColumn(name = "ala_esquerda", insertable = false, updatable = false)
    private Jogador alaEsquerda;
    @OneToOne
    @JoinColumn(name = "ala_direita", insertable = false, updatable = false)
    private Jogador alaDireita;
    @OneToOne
    @JoinColumn(name = "pivot", insertable = false, updatable = false)
    private Jogador pivot;

    public Selecao(Equipa e, Jogador capitao, Map<Posicao,Jogador> jogadores){
        this.equipa = e;
        this.capitao = capitao;
        setJogadores(jogadores);
    }
    
    public Selecao() {
        //TODO Auto-generated constructor stub
    }

    public Map<Posicao, Jogador> getJogadores(){
        Map<Posicao, Jogador> jogadores = new EnumMap<>(Posicao.class);
        jogadores.put(posicoes[0], guardaRedes);
        jogadores.put(posicoes[1], fixo);
        jogadores.put(posicoes[2], alaEsquerda);
        jogadores.put(posicoes[3], alaDireita);
        jogadores.put(posicoes[4], pivot);
        return jogadores;
    }

    public void setJogadores(Map<Posicao,Jogador> jogadores){    //TODO - trocar o "posicoes[0]" pelo nome real de cada posicao.
        this.guardaRedes = jogadores.get(posicoes[0]);
        this.fixo = jogadores.get(posicoes[1]);
        this.alaEsquerda = jogadores.get(posicoes[2]);
        this.alaDireita = jogadores.get(posicoes[3]);
        this.pivot = jogadores.get(posicoes[4]);
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public Jogador getCapitao() {
        return capitao;
    }

    public void setCapitao(Jogador capitao) {
        this.capitao = capitao;
    }

    public Posicao[] getPosicoes() {
        return posicoes;
    }

    public Jogador getGuardaRedes() {
        return guardaRedes;
    }

    public void setGuardaRedes(Jogador guardaRedes) {
        this.guardaRedes = guardaRedes;
    }

    public Jogador getFixo() {
        return fixo;
    }

    public void setFixo(Jogador fixo) {
        this.fixo = fixo;
    }

    public Jogador getAlaEsquerda() {
        return alaEsquerda;
    }

    public void setAlaEsquerda(Jogador alaEsquerda) {
        this.alaEsquerda = alaEsquerda;
    }

    public Jogador getAlaDireita() {
        return alaDireita;
    }

    public void setAlaDireita(Jogador alaDireita) {
        this.alaDireita = alaDireita;
    }

    public Jogador getPivot() {
        return pivot;
    }

    public void setPivot(Jogador pivot) {
        this.pivot = pivot;
    }
    

}
