package pt.ul.fc.css.soccernow.entities.jogos;

public class Placar {
    String equipa1;
    String equipa2;
    int pontuacao1;
    int pontuacao2;

    public Placar(String equipa1, String equipa2){
        this.equipa1 = equipa1;
        this.equipa2 = equipa2;
        this.pontuacao1 = 0;
        this.pontuacao2 = 0;
    }

    public void setScore(int pontuacao1, int pontuacao2){
        this.pontuacao1 = pontuacao1;
        this.pontuacao2 = pontuacao2;
    }

    public String getShortScore(){
        return (pontuacao1+" - "+pontuacao2);
    }

    @Override
    public String toString(){
        return (equipa1 + ": "+pontuacao1+"\n"+equipa2+": "+pontuacao2);
    }
}
