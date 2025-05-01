package pt.ul.fc.css.soccernow.mappers.jogos;

import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.entities.jogos.Golo;
import pt.ul.fc.css.soccernow.mappers.equipas.EquipaMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;

public class GoloMapper {
        public static GoloDto goloToDto(Golo g){
        if(g == null){return null;}
        GoloDto goloDto = new GoloDto();
        goloDto.setQuando(g.getQuando());
        goloDto.setJogo(JogoMapper.jogoToDto(g.getJogo()));
        goloDto.setMarcador(JogadorMapper.jogadorToDto(g.getMarcador()));
        goloDto.setEquipa(EquipaMapper.equipaToDto(g.getEquipa()));

        return goloDto;
    }
    public static Golo dtoToGolo(GoloDto g){
        if(g == null){return null;}
        Golo golo = new Golo();
        golo.setQuando(g.getQuando());
        golo.setJogo(JogoMapper.dtoToJogo(g.getJogo()));
        golo.setMarcador(JogadorMapper.dtoToJogador(g.getMarcador()));
        golo.setEquipa(EquipaMapper.dtoToEquipa(g.getEquipa()));

        return golo;
    }
}
