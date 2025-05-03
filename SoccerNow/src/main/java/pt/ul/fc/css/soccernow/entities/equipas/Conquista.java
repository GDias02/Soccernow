package pt.ul.fc.css.soccernow.entities.equipas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;

@Entity
public class Conquista implements IConquista {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String descricao;

  private int posicaoDePodio;

  @ManyToOne
  @JoinColumn(name = "equipa_id", nullable = false)
  private Equipa equipa;

  @ManyToOne
  @JoinColumn(name = "campeonato_id", nullable = false)
  private Campeonato campeonato;

  @Version
  private int version;

  public Long getId() {
    return id;
  }

  @Override
  public String getDescricao() {
    return descricao;
  }

  @Override
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  @Override
  public Integer getPosicaoDePodio() {
    return posicaoDePodio;
  }

  @Override
  public void setPosicaoDePodio(Integer posicaoDePodio) {
    this.posicaoDePodio = posicaoDePodio;
  }

  @Override
  public Equipa getEquipa() {
    return equipa;
  }

  @Override
  public void setEquipa(Equipa equipa) {
    this.equipa = equipa;
  }

  @Override
  public Campeonato getCampeonato() {
    return campeonato;
  }

  @Override
  public void setCampeonato(Campeonato campeonato) {
    this.campeonato = campeonato;
  }
}
