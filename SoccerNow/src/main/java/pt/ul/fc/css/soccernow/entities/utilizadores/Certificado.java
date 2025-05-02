package pt.ul.fc.css.soccernow.entities.utilizadores;

import jakarta.persistence.Embeddable;

@Embeddable
public class Certificado {
    
    private boolean existsCertificado;

    public Certificado() {}

    public Certificado(boolean existsCertificado) {
        this.existsCertificado = existsCertificado;
    }

    public boolean isExistsCertificado() {
        return existsCertificado;
    }

    public void setExistsCertificado(boolean existsCertificado) {
        this.existsCertificado = existsCertificado;
    }
}
