package pt.ul.fc.css.soccernow.handlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Certificado;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarArbitroException;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.CertificadoMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

@Service
public class ArbitroHandler implements IArbitroHandler {

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Autowired
    private JogadorRepository jogadorRepository;

    @Override
    @Transactional
    public ArbitroDto registarArbitro(ArbitroDto arbitroDto) throws RegistarArbitroException {
        try {
            validInput(arbitroDto);
        } catch (IllegalArgumentException e) {
            throw new RegistarArbitroException(e.getMessage());
        }

        UtilizadorDto utilizadorDto = arbitroDto.getUtilizador();
        int nif = utilizadorDto.getNif();
        if (!jogadorRepository.findByNif(nif).isEmpty() || !arbitroRepository.findByNif(nif).isEmpty())
            throw new RegistarArbitroException("Já existe um utilizador com esse nif");

        Arbitro arbitro = ArbitroMapper.dtoToArbitro(arbitroDto);
        arbitro.setId(0L);

        Arbitro savedArbitro = new Arbitro();
        try {
            savedArbitro = arbitroRepository.save(arbitro);
        } catch (IllegalArgumentException e) {
            throw new RegistarArbitroException("Erro a registar árbitro: " + e.getMessage());
        }

        ArbitroDto responseDto = ArbitroMapper.arbitroToDto(savedArbitro);

        return responseDto;
    }

    @Override
    @Transactional
    public ArbitroDto verificarArbitro(int nif) throws VerificarArbitroException, NotFoundException {
        if (!(100000000 <= nif && nif <= 999999999)) throw new VerificarArbitroException("O nif do árbitro tem de ter 9 dígitos");

        Optional<Arbitro> maybeArbitro = arbitroRepository.findAliveByNif(nif);
        if (maybeArbitro.isEmpty()) throw new NotFoundException("O árbitro não existe");

        ArbitroDto arbitroDto = ArbitroMapper.arbitroToDto(maybeArbitro.get());
        return arbitroDto;
    }

    @Override
    @Transactional
    public void removerArbitro(int nif) throws RemoverArbitroException, NotFoundException {
        if (!(100000000 <= nif && nif <= 999999999)) throw new RemoverArbitroException("O nif do árbitro tem de ter 9 dígitos");

        Optional<Arbitro> maybeArbitro = arbitroRepository.findAliveByNif(nif);
        if (maybeArbitro.isEmpty()) throw new NotFoundException("O árbitro não existe");

        arbitroRepository.deleteByNif(nif);
    }

    @Override
    @Transactional
    public ArbitroDto atualizarArbitro(ArbitroDto arbitroDto) throws AtualizarArbitroException, NotFoundException {
        try {
            validInput(arbitroDto);
        } catch (IllegalArgumentException e) {
            throw new AtualizarArbitroException(e.getMessage());
        }

        UtilizadorDto utilizador = arbitroDto.getUtilizador();

        Long id = utilizador.getId();
        if (!(id > 0)) throw new AtualizarArbitroException("O id do árbitro tem de ser positivo");
        Optional<Arbitro> maybeArbitro = arbitroRepository.findAliveById(id);
        if (maybeArbitro.isEmpty()) throw new NotFoundException("O árbitro não existe");

        int nif = utilizador.getNif();
        Optional<Arbitro> maybeDuplicate = arbitroRepository.findByNif(nif);
        if ((!maybeDuplicate.isEmpty() && !id.equals(maybeDuplicate.get().getId())) || !jogadorRepository.findByNif(nif).isEmpty())
            throw new AtualizarArbitroException("Já existe um utilizador com esse nif");

        Arbitro arbitro = maybeArbitro.get();
        arbitro.setNif(utilizador.getNif());
        arbitro.setNome(utilizador.getNome());
        arbitro.setContacto(utilizador.getContacto());

        CertificadoDto certificadoDto = arbitroDto.getCertificado();
        if (certificadoDto != null) {
            Certificado certificado = CertificadoMapper.dtoToCertificado(certificadoDto);
            arbitro.setCertificado(certificado);
        }

        Arbitro updatedArbitro;
        try {
            updatedArbitro = arbitroRepository.save(arbitro);
        } catch (IllegalArgumentException e) {
            throw new AtualizarArbitroException("Erro a atualizar árbitro: " + e.getMessage());
        }

        ArbitroDto responseDto = ArbitroMapper.arbitroToDto(updatedArbitro);
        return responseDto;
    }

    private void validInput(ArbitroDto arbitroDto) throws IllegalArgumentException {
        if (arbitroDto == null) throw new IllegalArgumentException("Sem dados para o árbitro");

        UtilizadorDto utilizadorDto = arbitroDto.getUtilizador();
        if (utilizadorDto == null) throw new IllegalArgumentException("Sem dados de utilizador para o árbitro");
        int nif = utilizadorDto.getNif();

        if (!(100000000 <= nif && nif <= 999999999)) throw new IllegalArgumentException("O nif do árbitro tem de ter 9 dígitos");
        if (!isFilled(utilizadorDto.getNome())) throw new IllegalArgumentException("O nome do árbitro é obrigatório");
    }

    private boolean isFilled(String field) {
        return field != null && field.length() > 0;
    }
    
}
