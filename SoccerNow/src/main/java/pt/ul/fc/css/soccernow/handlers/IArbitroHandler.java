package pt.ul.fc.css.soccernow.handlers;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;

public interface IArbitroHandler {
    
    public ArbitroDto registarArbitro(ArbitroDto arbitroDto);

    public ArbitroDto verificarArbitro(int nif);

    public void removerArbitro(int nif);

    public ArbitroDto atualizarArbitro(ArbitroDto arbitroDto);
}
