package pt.ul.fc.css.soccernow.filtros;

public class JogoFiltros {

    private String jogosEstado;
    private String golos;
    private String local;
    private String turno;

    public JogoFiltros() {}

    public JogoFiltros(String jogosEstado, String golos, String local, String turno) {
        this.jogosEstado = jogosEstado;
        this.golos = golos;
        this.local = local;
        this.turno = turno;
    }

    public String getJogosEstado() {
        return jogosEstado;
    }

    public void setJogosEstado(String jogosEstado) {
        this.jogosEstado = jogosEstado;
    }

    public String getGolos() {
        return golos;
    }

    public void setGolos(String golos) {
        this.golos = golos;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
