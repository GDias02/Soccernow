package pt.ul.fc.css.soccernow.dto.equipas;

import java.util.List;

public class EquipaDto {
    private Long id;
    private String nome;
    private List<Long> historicoDeJogos;
    private List<Long> jogadores;
    
    public EquipaDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Long> getHistoricoDeJogos() {
        return historicoDeJogos;
    }

    public void setHistoricoDeJogos(List<Long> historicoDeJogos) {
        this.historicoDeJogos = historicoDeJogos;
    }

    public List<Long> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Long> jogadores) {
        this.jogadores = jogadores;
    }
}
