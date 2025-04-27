package pt.ul.fc.css.soccernow.entities.utilizadores;

public interface IUtilizador {
    
    public Long getId();

    public int getNif();

    public void setNif(int nif);

    public String getNome();

    public void setNome(String nome);

    public String getContacto();

    public void setContacto(String contacto);
}
