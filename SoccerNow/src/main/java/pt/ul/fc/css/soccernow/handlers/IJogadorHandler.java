package pt.ul.fc.css.soccernow.handlers;

import java.util.Set;

import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;

public interface IJogadorHandler {
    
    public JogadorDto registarJogador(JogadorDto jogadorDto);

    public JogadorDto verificarJogador(int nif);

    public JogadorDto removerJogador(int nif);

    public JogadorDto atualizarJogador(JogadorDto jogadorDto);

    public Set<JogadorDto> buscarJogadores();
}
