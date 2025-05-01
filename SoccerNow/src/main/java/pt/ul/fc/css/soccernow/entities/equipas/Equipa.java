package pt.ul.fc.css.soccernow.entities.equipas;

import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import java.util.ArrayList;

import pt.ul.fc.css.soccernow.entities.utilizadores.*;
import pt.ul.fc.css.soccernow.entities.jogos.*;

@Entity
public class Equipa implements IEquipa {

    @Id
    private Long id;
    private String nome;

    @ManyToMany
    @JoinTable(
        name = "equipa_jogo",
        joinColumns = @JoinColumn(name = "equipa_id"),
        inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    private List<Jogo> historicoDeJogos;

    @ManyToMany
    @JoinTable(
        name = "equipa_jogador",
        joinColumns = @JoinColumn(name = "equipa_id"),
        inverseJoinColumns = @JoinColumn(name = "jogador_id")
    )
    private List<Jogador> jogadores;

    public Equipa(String nome) {
        this.nome = nome;
        this.historicoDeJogos = new ArrayList<>();
        this.jogadores = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public List<Jogo> getHistoricoDeJogos() {
        return historicoDeJogos;
    }

    @Override
    public Jogo getJogo(Long idJogo) {
        for (Jogo jogo : historicoDeJogos) {
            if (jogo.getId().equals(idJogo)) {
                return jogo;
            }
        }
        return null;
    }

    @Override
    public void addJogo(Jogo jogo) {
        historicoDeJogos.add(jogo);
    }

    @Override
    public void addJogos(List<Jogo> jogos) {
        historicoDeJogos.addAll(jogos);
    }

    @Override
    public List<Jogador> getJogadores() {
        return jogadores;
    }

    @Override
    public Jogador getJogador(Long idJogador) {
        for (Jogador jogador : jogadores) {
            if (jogador.getId().equals(idJogador)) {
                return jogador;
            }
        }
        return null;
    }

    @Override
    public void addJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    @Override
    public void addJogadores(List<Jogador> jogadores){
        for (Jogador j : jogadores) addJogador(j);        
    }

    @Override
    public void removeJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    @Override
    public void removeJogo(Long idJogo) {
        Jogo jogo = getJogo(idJogo);
        if (jogo != null) {
            historicoDeJogos.remove(jogo);
        }
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
}
