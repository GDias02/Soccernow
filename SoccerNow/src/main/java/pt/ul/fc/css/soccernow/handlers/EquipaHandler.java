package pt.ul.fc.css.soccernow.handlers;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;
import pt.ul.fc.css.soccernow.mappers.equipas.EquipaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EquipaHandler implements IEquipaHandler {

    @Autowired
    private EquipaRepository equipaRepository;

    @Autowired
    private EquipaMapper equipaMapper;


    @Override
    public EquipaDto verificarEquipa(long id) {
        Optional<IEquipa> e = equipaRepository.findById(id);
        if (e.isEmpty())
            return null;
        else 
            return mapToDto(e.get());
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
            equipaRepository.save(mapToEntity(equipaDto));
        }
        return valid ? verificarEquipa(id) : null;
    }

    @Override
    public List<EquipaDto> verificarEquipas() {
        return mapManyToDto(equipaRepository.findAll());
    }

    @Override
    public EquipaDto registarEquipa(EquipaDto equipaDto) {
        boolean valid = isValid(equipaDto);
        if (valid)}{
            IEquipa equipa = mapToEntity(equipaDto);
            IEquipa savedEquipa = equipaRepository.save(equipa);
            equipaDto.setId(savedEquipa.getId());
        }
        return valid ? equipaDto : null;
    }

    private boolean isValid(EquipaDto equipaDto) {

        return false;
    }

}
