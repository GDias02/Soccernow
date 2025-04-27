package pt.ul.fc.css.soccernow.entities.utilizadores;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class Arbitro extends Utilizador implements IArbitro {

    @Embedded
    private Certificado certificado;

    public Arbitro() {}

    @Override
    public Certificado getCertificado() {
        return this.certificado;
    }

    @Override
    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
    
}
