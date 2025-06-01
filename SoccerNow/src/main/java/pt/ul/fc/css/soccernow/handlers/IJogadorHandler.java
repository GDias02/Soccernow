package pt.ul.fc.css.soccernow.handlers;

import java.util.Set;

import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarJogadorException;

public interface IJogadorHandler {
    
    public JogadorDto registarJogador(JogadorPostDto jogadorDto) throws RegistarJogadorException;

    public JogadorDto verificarJogador(int nif) throws VerificarJogadorException, NotFoundException;

    public void removerJogador(int nif) throws RemoverJogadorException, NotFoundException;

    public JogadorPostDto atualizarJogador(JogadorPostDto jogadorDto) throws AtualizarJogadorException, NotFoundException;

    public Set<JogadorDto> buscarJogadores();
}
