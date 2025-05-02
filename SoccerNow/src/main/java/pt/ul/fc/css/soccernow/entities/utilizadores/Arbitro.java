package pt.ul.fc.css.soccernow.entities.utilizadores;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class Arbitro extends Utilizador implements IArbitro {

    @Embedded
    private Certificado certificado;

    public Arbitro() {}

    public Arbitro(Long id, int nif, String nome, String contacto, Certificado certificado) {
        super(id, nif, nome, contacto);
        this.certificado =  certificado;
    }

    @Override
    public Certificado getCertificado() {
        return this.certificado;
    }

    @Override
    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
    
}
