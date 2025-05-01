package pt.ul.fc.css.soccernow.entities.jogos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

public interface IJogo {

    Long getId();

    Equipa getEquipa1();

    Equipa getEquipa2();

    Map<Posicao,Jogador> getSelecao(Equipa equipa);

    Placar getPlacar();

    Arbitro getArbitroPrincipal();

    List<Arbitro> getEquipaDeArbitros();

    Local getLocal();

    LocalDateTime getDiaEHora();

    EstatisticaJogo getStats();
    
}
