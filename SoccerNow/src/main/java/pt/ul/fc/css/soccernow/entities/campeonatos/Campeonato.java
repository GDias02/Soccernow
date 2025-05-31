package pt.ul.fc.css.soccernow.entities.campeonatos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.List;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.JogoCampeonato;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Campeonato implements ICampeonato {

  @Id protected Long id;

  protected String nome;
  protected EstadoCampeonato estado;
  protected Date dataInicio;

  @ManyToOne protected List<JogoCampeonato> jogos;

  @ManyToMany protected List<Equipa> equipas;

  public Campeonato() {}

  public Campeonato(
      Long id,
      String nome,
      EstadoCampeonato estado,
      Date dataInicio,
      List<JogoCampeonato> jogos,
      List<Equipa> equipas) {
    this.id = id;
    this.nome = nome;
    this.estado = estado;
    this.dataInicio = dataInicio;
    this.jogos = jogos;
    this.equipas = equipas;
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

  public Date getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(Date dataInicio) {
    this.dataInicio = dataInicio;
  }

  public List<JogoCampeonato> getJogos() {
    return jogos;
  }

  public void setJogos(List<JogoCampeonato> jogos) {
    this.jogos = jogos;
  }

  public void addJogo(JogoCampeonato jogo) {
    this.jogos.add(jogo);
  }

  public void addJogos(List<JogoCampeonato> jogos) {
    this.jogos.addAll(jogos);
  }

  public void removeJogo(JogoCampeonato jogoCampeonato) {
    this.jogos.remove(jogoCampeonato);
  }

  public List<Equipa> getEquipas() {
    return equipas;
  }

  public void setEquipas(List<Equipa> equipas) {
    this.equipas = equipas;
  }

  public void addEquipa(Equipa equipa) {
    this.equipas.add(equipa);
  }

  public void addEquipas(List<Equipa> equipas) {
    this.equipas.addAll(equipas);
  }

  public void removeEquipa(Equipa equipa) {
    this.equipas.remove(equipa);
  }
}
