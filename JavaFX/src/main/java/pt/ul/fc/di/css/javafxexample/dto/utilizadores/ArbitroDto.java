package pt.ul.fc.di.css.javafxexample.dto.utilizadores;

import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaArbitroDto;

public class ArbitroDto {
    
  private UtilizadorDto utilizador;
  private CertificadoDto certificado;
  private EstatisticaArbitroDto estatisticas;

  public ArbitroDto() {}

  public ArbitroDto(UtilizadorDto utilizador, CertificadoDto certificado) {
    this.utilizador = utilizador;
    this.certificado = certificado;
  }

  public UtilizadorDto getUtilizador() {
    return utilizador;
  }

  public void setUtilizador(UtilizadorDto utilizador) {
    this.utilizador = utilizador;
  }

  public CertificadoDto getCertificado() {
    return certificado;
  }

  public void setCertificado(CertificadoDto certificado) {
    this.certificado = certificado;
  }

  public EstatisticaArbitroDto getEstatisticas() {
    return estatisticas;
  }

  public void setEstatisticas(EstatisticaArbitroDto estatisticas) {
    this.estatisticas = estatisticas;
  }
}
