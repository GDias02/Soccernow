package pt.ul.fc.css.soccernow.entities.equipas;

import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.ArrayList;

import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;
import pt.ul.fc.css.soccernow.entities.jogos.IJogo;;;




@Entity
public class Equipa implements IEquipa {
    private long id;
    private String nome;
    private List<IJogo> historicoDeJogos;
    private List<IJogador> jogadores;

    public Equipa(String nome) {
        this.nome = nome;
        this.historicoDeJogos = new ArrayList<>();
        this.jogadores = new ArrayList<>();
    }

    @Override
    public void setId(long id){
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public List<IJogo> getHistoricoDeJogos() {
        return historicoDeJogos;
    }

    @Override
    public IJogo getJogo(long idJogo) {
        for (IJogo jogo : historicoDeJogos) {
            if (jogo.getId() == idJogo) {
                return jogo;
            }
        }
        return null;
    }

    @Override
    public void addJogo(IJogo jogo) {
        historicoDeJogos.add(jogo);
    }

    @Override
    public void addJogos(List<IJogo> jogos) {
        historicoDeJogos.addAll(jogos);
    }

    @Override
    public List<IJogador> getJogadores() {
        return jogadores;
    }

    @Override
    public IJogador getJogador(long idJogador) {
        for (IJogador jogador : jogadores) {
            if (jogador.getId() == idJogador) {
                return jogador;
            }
        }
        return null;
    }

    @Override
    public void addJogador(IJogador jogador) {
        jogadores.add(jogador);
    }

    @Override
    public void addJogadores(List<IJogador> jogadores){
        for (IJogador j : jogadores) addJogador(j);        
    }

    @Override
    public void removeJogador(IJogador jogador) {
        jogadores.remove(jogador);
    }

    @Override
    public void removeJogo(long idJogo) {
        IJogo jogo = getJogo(idJogo);
        if (jogo != null) {
            historicoDeJogos.remove(jogo);
        }
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
}
