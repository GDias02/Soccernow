package pt.ul.fc.css.soccernow.entities.jogos;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Local {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @Column(unique = true, nullable = false)
  String nome;

  String proprietario;
  int capacidade;
  @Embedded Morada morada;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getProprietario() {
    return proprietario;
  }

  public void setProprietario(String proprietario) {
    this.proprietario = proprietario;
  }

  public int getCapacidade() {
    return capacidade;
  }

  public void setCapacidade(int capacidade) {
    this.capacidade = capacidade;
  }

  public Morada getMorada() {
    return morada;
  }

  public void setMorada(Morada morada) {
    this.morada = morada;
  }
}
