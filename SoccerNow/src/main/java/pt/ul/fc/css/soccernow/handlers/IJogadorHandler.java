package pt.ul.fc.css.soccernow.handlers;

import java.util.Set;

import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;

public interface IJogadorHandler {
    
    public JogadorDto registarJogador(JogadorPostDto jogadorDto);

    public JogadorDto verificarJogador(int nif);

    public void removerJogador(int nif);

    public JogadorPostDto atualizarJogador(JogadorPostDto jogadorDto);

    public Set<JogadorDto> buscarJogadores();
}
