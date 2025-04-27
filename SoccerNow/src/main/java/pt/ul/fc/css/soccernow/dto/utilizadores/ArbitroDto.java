package pt.ul.fc.css.soccernow.dto.utilizadores;

public class ArbitroDto {
    
    private UtilizadorDto utilizador;
    private CertificadoDto certificado;

    public ArbitroDto() {}

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
