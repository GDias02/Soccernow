package pt.ul.fc.css.soccernow.dto.utilizadores;

public class CertificadoDto {
    
    private boolean existsCertificado;

    public CertificadoDto() {}

    public CertificadoDto(boolean existsCertificado) {
        this.existsCertificado = existsCertificado;
    }

    public boolean isExistsCertificado() {
        return existsCertificado;
    }

    public void setExistsCertificado(boolean existsCertificado) {
        this.existsCertificado = existsCertificado;
    }
}
