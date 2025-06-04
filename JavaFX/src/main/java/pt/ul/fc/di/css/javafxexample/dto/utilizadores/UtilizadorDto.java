package pt.ul.fc.di.css.javafxexample.dto.utilizadores;

public class UtilizadorDto {
    
    private Long id;
    private int nif;
    private String nome;
    private String contacto;

    public UtilizadorDto() {}

    public UtilizadorDto(Long id, int nif, String nome, String contacto) {
        this.id = id;
        this.nif = nif;
        this.nome = nome;
        this.contacto = contacto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return nome ;
    }
}
