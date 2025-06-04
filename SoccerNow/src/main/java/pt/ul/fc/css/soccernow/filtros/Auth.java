package pt.ul.fc.css.soccernow.filtros;

public class Auth {

    private String nif;
    private String password;

    public Auth() {}

    public Auth(String nif, String password) {
        this.nif = nif;
        this.password = password;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
