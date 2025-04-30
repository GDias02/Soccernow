package pt.ul.fc.css.soccernow.handlers;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.equipas.IEquipa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EquipaHandler implements IEquipaHandler {

    @Autowired
    private EquipaRepository equipaRepository;

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
        if (e != null){
            //If it exists, then save
            equipaRepository.save(mapToEntity(equipaDto));
        }
        return verificarEquipa(id);
    }

    @Override
    public List<EquipaDto> verificarEquipas() {
        return mapManyToDto(equipaRepository.findAll());
    }

    @Override
    public EquipaDto registarEquipa(EquipaDto equipaDto) {
        IEquipa equipa = mapToEntity(equipaDto);

        IEquipa savedEquipa = equipaRepository.save(equipa);

        equipaDto.setId(savedEquipa.getId());
        
        return equipaDto;
    }

    private EquipaDto mapToDto(IEquipa e){
        EquipaDto = new EquipaDto();

        return null;
    }

    private IEquipa mapToEntity(EquipaDto e){
        IEquipa equipa = new Equipa(e.getNome());
        equipa.addJogos(e.getHistoricoDeJogos());
        equipa.addJogadores(e.getJogadores());
        if (e.getId() != null){
            equipa.setId(e.getId());
        }
        return equipa;
    }

    private List<IEquipa> mapManyToEntity(List<EquipaDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    private List<EquipaDto> mapManyToDto(List<IEquipa> entityList){
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
