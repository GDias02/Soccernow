package pt.ul.fc.css.soccernow.entities.jogos;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.IArbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.IJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

public interface IJogo {

    Long getId();

    IEquipa getEquipa1();

    IEquipa getEquipa2();

    Map<Posicao,IJogador> getSelecao(IEquipa equipa);

    Placar getPlacar();

    IArbitro getArbitroPrincipal();

    List<IArbitro> getEquipaDeArbitros();

    Local getLocal();

    Date getDiaEHora();

    EstatisticaJogo getEstatisticaJogo();
    
}
