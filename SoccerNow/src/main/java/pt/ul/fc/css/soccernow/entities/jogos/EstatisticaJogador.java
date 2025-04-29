package pt.ul.fc.css.soccernow.entities.jogos;

public class EstatisticaJogador extends Estatistica {
    public int golosMarcados(){return super.getGolos().size();}

    //Para fazer em SQL no handler
    /* 
    public int golosMarcadosEmCampeonatos(){
        return (int) super.getGolos().stream().filter(golo ->
            golo.getJogo().getClass().equals(JogoCampeonato.class)).count();
    }
            */
}
