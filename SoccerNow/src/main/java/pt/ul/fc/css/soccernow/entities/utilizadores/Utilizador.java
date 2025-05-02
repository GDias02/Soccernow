package pt.ul.fc.css.soccernow.entities.utilizadores;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Version;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Utilizador implements IUtilizador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="UTILIZADOR_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private int nif;

    @Column(nullable = false)
    private String nome;

    private String contacto;

    @Version
    private long version;

    public Utilizador() {}

    public Utilizador(Long id, int nif, String nome, String contacto) {
        this.id = id;
        this.nif = nif;
        this.nome = nome;
        this.contacto = contacto;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
}
