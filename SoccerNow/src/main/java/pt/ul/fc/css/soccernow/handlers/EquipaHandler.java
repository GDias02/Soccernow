package pt.ul.fc.css.soccernow.handlers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.mappers.equipas.EquipaMapper;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;


@Service
public class EquipaHandler implements IEquipaHandler {

    @Autowired
    private EquipaRepository equipaRepository;

    @Override
    public EquipaDto verificarEquipa(long id) {
        Optional<Equipa> e = equipaRepository.findById(id);
        if (e.isEmpty())
            return null;
        else 
            return EquipaMapper.equipaToDto(e.get());
    }

    @Override
    public EquipaDto removerEquipa(long id) {
        EquipaDto e = verificarEquipa(id);
        if (e != null)
            equipaRepository.deleteById(id);
        return e;
    }

    @Override
    public EquipaDto atualizarEquipa(long id, EquipaDto equipaDto) {
        EquipaDto e = verificarEquipa(id);
        boolean valid = isValid(equipaDto);
        if (e != null && valid){
            //If it exists and it's valid, then save
            equipaRepository.save(EquipaMapper.dtoToEquipa(equipaDto));
        }
        return valid ? verificarEquipa(id) : null;
    }

    @Override
    public List<EquipaDto> verificarEquipas() {
        return EquipaMapper.manyEquipasToDtos(equipaRepository.findAll());
    }

    @Override
    public EquipaDto registarEquipa(EquipaDto equipaDto) {
        boolean valid = isValid(equipaDto);
        if (valid){
            Equipa equipa = EquipaMapper.dtoToEquipa(equipaDto);
            Equipa savedEquipa = equipaRepository.save(equipa);
            equipaDto.setId(savedEquipa.getId());
        }
        return valid ? equipaDto : null;
    }

    private boolean isValid(EquipaDto equipaDto) {
        
        return true;
    }

}
