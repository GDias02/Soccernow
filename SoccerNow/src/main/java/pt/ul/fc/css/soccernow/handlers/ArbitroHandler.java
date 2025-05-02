package pt.ul.fc.css.soccernow.handlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;

@Service
public class ArbitroHandler implements IArbitroHandler {

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Override
    public ArbitroDto registarArbitro(ArbitroDto arbitroDto) {
        if (!validInput(arbitroDto)) return null;

        UtilizadorDto utilizadorDto = arbitroDto.getUtilizador();
        
        if (utilizadorDto.getId() != 0) return null;

        Arbitro arbitro = ArbitroMapper.dtoToArbitro(arbitroDto);
        Arbitro savedArbitro = arbitroRepository.save(arbitro);
        ArbitroDto responseDto = ArbitroMapper.arbitroToDto(savedArbitro);

        return responseDto;
    }

    @Override
    public ArbitroDto verificarArbitro(int nif) {
        Optional<Arbitro> maybeArbitro = arbitroRepository.findByNif(nif);
        return maybeArbitro.isEmpty() ? null : ArbitroMapper.arbitroToDto(maybeArbitro.get());
    }

    @Override
    public void removerArbitro(int nif) {
        arbitroRepository.deleteByNif(nif);
    }

    @Override
    public ArbitroDto atualizarArbitro(ArbitroDto arbitroDto) {
        if (!validInput(arbitroDto)) return null;

        Long id = arbitroDto.getUtilizador().getId();
        
        if (id == 0 || arbitroRepository.findById(id).isEmpty()) return null;

        Arbitro arbitro = ArbitroMapper.dtoToArbitro(arbitroDto);
        Arbitro updatedArbitro = arbitroRepository.save(arbitro);
        ArbitroDto responseDto = ArbitroMapper.arbitroToDto(updatedArbitro);

        return responseDto;
    }

    public boolean validInput(ArbitroDto arbitroDto) {
        if (arbitroDto == null) return false;

        UtilizadorDto utilizadorDto = arbitroDto.getUtilizador();
        if (utilizadorDto == null) return false;
        int nif = utilizadorDto.getNif();

        return (100000000 <= nif && nif <= 999999999)
            && isFilled(utilizadorDto.getNome())
            && isFilled(utilizadorDto.getContacto());
    }

    private boolean isFilled(String field) {
        return field != null && field.length() > 0;
    }
    
}
