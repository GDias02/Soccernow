package pt.ul.fc.di.css.javafxexample.dto.utilizadores;

public class ArbitroDto {
    
    private UtilizadorDto utilizador;
    private CertificadoDto certificado;

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
}
