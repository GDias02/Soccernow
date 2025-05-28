package pt.ul.fc.css.soccernow.dto.campeonatos;

import java.util.Date;
import java.util.List;

import pt.ul.fc.css.soccernow.entities.campeonatos.EstadoCampeonato;

public class CampeonatoDto {
    
    private Long id;
    private String nome;
    private EstadoCampeonato estado;
    private List<Long> jogos;
    private List<Long> equipas;
    private Date dataInicio;

    private String tipo;
    private CampeonatoEliminatoriaDto eliminatoriaDto;
    private CampeonatoPontosDto pontosDto;

    CampeonatoDto(){}

    CampeonatoDto(
        Long id, 
        String nome, 
        EstadoCampeonato estado, 
        List<Long> jogos,
        List<Long> equipas,
        Date dataInicio,
        String tipo, 
        CampeonatoEliminatoriaDto eliminatoria, 
        CampeonatoPontosDto pontos){
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.jogos = jogos;
        this.equipas = equipas;
        this.dataInicio = dataInicio;
        this.tipo = tipo;
        this.eliminatoriaDto = eliminatoria;
        this.pontosDto = pontos;
    }

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

    public EstadoCampeonato getEstado() {
        return estado;
    }

    public void setEstado(EstadoCampeonato estado) {
        this.estado = estado;
    }

    public List<Long> getJogos() {
        return jogos;
    }

    public void setJogos(List<Long> jogos) {
        this.jogos = jogos;
    }

    public List<Long> getEquipas() {
        return equipas;
    }

    public void setEquipas(List<Long> equipas) {
        this.equipas = equipas;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CampeonatoEliminatoriaDto getEliminatoriaDto() {
        return eliminatoriaDto;
    }

    public void setEliminatoriaDto(CampeonatoEliminatoriaDto eliminatoriaDto) {
        this.eliminatoriaDto = eliminatoriaDto;
    }

    public CampeonatoPontosDto getPontosDto() {
        return pontosDto;
    }

    public void setPontosDto(CampeonatoPontosDto pontosDto) {
        this.pontosDto = pontosDto;
    }

    @Override
    public String toString() {
        return "CampeonatoDto [id=" + id + ", nome=" + nome + ", estado=" + estado + ", dataInicio=" + dataInicio
                + ", tipo=" + tipo + "]";
    }


    
}
