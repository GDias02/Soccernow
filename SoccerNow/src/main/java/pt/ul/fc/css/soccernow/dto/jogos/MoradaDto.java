package pt.ul.fc.css.soccernow.dto.jogos;

/**
 * Classe Dto para representar uma morada de um qualquer local usado para jogos. Para: - Criar um
 * novo jogo: - - Todos os atributos devem estar preenchidos
 */
public class MoradaDto {
  String codigoPostal;
  String rua;
  String localidade;
  String cidade;
  String estado;
  String pais;

  public MoradaDto() {}

  public MoradaDto(
      String codigoPostal,
      String rua,
      String localidade,
      String cidade,
      String estado,
      String pais) {
    this.codigoPostal = codigoPostal;
    this.rua = rua;
    this.localidade = localidade;
    this.cidade = cidade;
    this.estado = estado;
    this.pais = pais;
  }

  public MoradaDto(String cidade, String estado, String pais) {
    this.cidade = cidade;
    this.estado = estado;
    this.pais = pais;
  }

  public String getCodigoPostal() {
    return codigoPostal;
  }

  public void setCodigoPostal(String codigoPostal) {
    this.codigoPostal = codigoPostal;
  }

  public String getRua() {
    return rua;
  }

  public void setRua(String rua) {
    this.rua = rua;
  }

  public String getLocalidade() {
    return localidade;
  }

  public void setLocalidade(String localidade) {
    this.localidade = localidade;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  @Override
  public String toString() {
    return "MoradaDto [codigoPostal="
        + codigoPostal
        + ", rua="
        + rua
        + ", localidade="
        + localidade
        + ", cidade="
        + cidade
        + ", estado="
        + estado
        + ", pais="
        + pais
        + "]";
  }
}
