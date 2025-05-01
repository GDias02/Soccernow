package pt.ul.fc.css.soccernow.entities.utilizadores;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@SQLDelete(sql = "UPDATE table_product SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public abstract class Utilizador implements IUtilizador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UTILIZADOR_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private int nif;

    @Column(nullable = false)
    private String nome;

    private String contacto;

    private boolean deleted = Boolean.FALSE;

    public Utilizador() {}

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public int getNif() {
        return this.nif;
    }

    @Override
    public void setNif(int nif) {
        this.nif = nif;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getContacto() {
        return this.contacto;
    }

    @Override
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
