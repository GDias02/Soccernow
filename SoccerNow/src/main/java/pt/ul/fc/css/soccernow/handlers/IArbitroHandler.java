package pt.ul.fc.css.soccernow.handlers;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarArbitroException;

public interface IArbitroHandler {
    
    public ArbitroPostDto registarArbitro(ArbitroPostDto arbitroDto) throws RegistarArbitroException;

    public ArbitroDto verificarArbitro(int nif) throws VerificarArbitroException, NotFoundException;

    public void removerArbitro(int nif) throws RemoverArbitroException, NotFoundException;

    public ArbitroPostDto atualizarArbitro(ArbitroPostDto arbitroDto) throws AtualizarArbitroException, NotFoundException;
}
