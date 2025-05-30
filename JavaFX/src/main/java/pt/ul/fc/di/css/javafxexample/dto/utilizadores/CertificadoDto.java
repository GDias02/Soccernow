package pt.ul.fc.di.css.javafxexample.dto.utilizadores;

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
