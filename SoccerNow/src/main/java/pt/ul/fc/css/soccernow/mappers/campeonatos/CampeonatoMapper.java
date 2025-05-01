package pt.ul.fc.css.soccernow.mappers.campeonatos;

import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoEliminatoria;
import pt.ul.fc.css.soccernow.entities.campeonatos.CampeonatoPontos;

public class CampeonatoMapper {
    public static CampeonatoDto campeonatoToDto(Campeonato campeonato) {
        CampeonatoDto campeonatoDto = new CampeonatoDto();

        return campeonatoDto;
    }

    public static Campeonato dtoToCampeonato(CampeonatoDto campeonatoDto) {
        Campeonato campeonato;
        
        //TODO
        if (true)
            campeonato = new CampeonatoPontos();
        else
            campeonato = new CampeonatoEliminatoria();

        return campeonato;
    }
}
