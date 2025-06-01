package pt.ul.fc.css.soccernow.mappers.jogos;

import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.entities.jogos.Local;
import pt.ul.fc.css.soccernow.entities.jogos.Morada;
import pt.ul.fc.css.soccernow.exceptions.jogos.JogoLocalException;

public class LocalMapper {
  public static Local dtoToLocal(LocalDto l) throws JogoLocalException {
    if (l == null) {
      return null;
    }
    Local local = new Local();
    local.setNome(l.getNome());
    local.setCapacidade(l.getCapacidade());
    local.setProprietario(l.getProprietario());
    local.setMorada(dtoToMorada(l.getMorada()));
    return local;
  }

  private static Morada dtoToMorada(MoradaDto m) {
    if (m == null) {
      return null;
    }
    Morada morada = new Morada();

    morada.setCodigoPostal(m.getCodigoPostal());
    morada.setRua(m.getRua());
    morada.setLocalidade(m.getLocalidade());
    morada.setCidade(m.getCidade());
    morada.setEstado(m.getEstado());
    morada.setPais(m.getPais());
    return morada;
  }

  public static LocalDto localToDto(Local l) {
    if (l == null) {
      return null;
    }
    LocalDto localDto = new LocalDto();
    localDto.setId(l.getId());
    localDto.setNome(l.getNome());
    localDto.setCapacidade(l.getCapacidade());
    localDto.setProprietario(l.getProprietario());
    localDto.setMorada(moradaToDto(l.getMorada()));
    return localDto;
  }

  private static MoradaDto moradaToDto(Morada m) {
    if (m == null) {
      return null;
    }
    MoradaDto moradaDto = new MoradaDto();

    moradaDto.setCodigoPostal(m.getCodigoPostal());
    moradaDto.setRua(m.getRua());
    moradaDto.setLocalidade(m.getLocalidade());
    moradaDto.setCidade(m.getCidade());
    moradaDto.setEstado(m.getEstado());
    moradaDto.setPais(m.getPais());
    return moradaDto;
  }
}
