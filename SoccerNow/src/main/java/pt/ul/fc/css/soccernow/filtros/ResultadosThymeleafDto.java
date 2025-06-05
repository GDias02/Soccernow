package pt.ul.fc.css.soccernow.filtros;

public class ResultadosThymeleafDto {
    
    private String placar;
    private String equipaVencedora;

    public ResultadosThymeleafDto() {}

    public ResultadosThymeleafDto(String placar, String equipaVencedora) {
        this.placar = placar;
        this.equipaVencedora = equipaVencedora;
    }

    public String getPlacar() {
        return placar;
    }

    public void setPlacar(String placar) {
        this.placar = placar;
    }

    public String getEquipaVencedora() {
        return equipaVencedora;
    }

    public void setEquipaVencedora(String equipaVencedora) {
        this.equipaVencedora = equipaVencedora;
    }
}
