package pt.ul.fc.css.soccernow.dto.jogos;

/**
 * Classe Dto para representar um local usado para jogos. Para: - Criar um novo jogo: - - Todos os
 * atributos devem estar preenchidos - - a morada deve estar completa.
 */
public class LocalDto {

  Long id;

  String nome;
  String proprietario;
  int capacidade;
  MoradaDto morada;

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

  public MoradaDto getMorada() {
    return morada;
  }

  public void setMorada(MoradaDto morada) {
    this.morada = morada;
  }

  @Override
  public String toString() {
    return "LocalDto [id="
        + id
        + ", nome="
        + nome
        + ", proprietario="
        + proprietario
        + ", capacidade="
        + capacidade
        + ", morada="
        + morada
        + "]";
  }
}
