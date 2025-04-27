package pt.ul.fc.css.soccernow.entities.utilizadores;

import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;

public interface IJogador extends IUtilizador {
    
    public Posicao getPosicaoPreferida();

    public void setPosicaoPreferida(Posicao posicaoPreferida);

    public EstatisticaJogador getEstatisticas();

    public void setEstatisticas(EstatisticaJogador estatisticas);
}
