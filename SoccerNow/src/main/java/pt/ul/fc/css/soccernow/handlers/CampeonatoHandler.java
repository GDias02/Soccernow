package pt.ul.fc.css.soccernow.handlers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.mappers.campeonatos.CampeonatoMapper;
import pt.ul.fc.css.soccernow.repositories.CampeonatoRepository;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

/** Handler for Campeonato operations. */
@Service
public class CampeonatoHandler implements ICampeonatoHandler {

  @Autowired private CampeonatoRepository campeonatoRepository;
  @Autowired private JogoRepository jogoRepository;
  @Autowired private EquipaRepository equipaRepository;

  /**
   * Gets all campeonatos.
   *
   * @return List of CampeonatoDto
   */
  @Transactional(readOnly = true)
  public List<CampeonatoDto> verificarCampeonatos() {
    return CampeonatoMapper.manyCampeonatosToDtos(campeonatoRepository.findAll());
  }

  /**
   * Gets campeonato by id.
   *
   * @param id Campeonato id
   * @return CampeonatoDto or null if not found
   */
  @Transactional(readOnly = true)
  public CampeonatoDto verificarCampeonato(Long id) {
    Optional<Campeonato> campeonato = campeonatoRepository.findById(id);
    if (campeonato.isEmpty()) return null;
    else return CampeonatoMapper.campeonatoToDto(campeonato.get());
  }

  /**
   * Creates a new campeonato.
   *
   * @param campeonatoDto Campeonato details
   * @return Created CampeonatoDto or null if invalid
   */
  @Transactional
  public CampeonatoDto criarCampeonato(CampeonatoDto campeonatoDto) {
    boolean valid = validatorCriarCampeonato(campeonatoDto);
    Campeonato savedCampeonato = null;
    if (valid) {
      Campeonato campeonato =
          CampeonatoMapper.dtoToCampeonato(campeonatoDto, equipaRepository, jogoRepository);
      savedCampeonato = campeonatoRepository.save(campeonato);
    }
    return CampeonatoMapper.campeonatoToDto(savedCampeonato);
  }

  private boolean validatorCriarCampeonato(CampeonatoDto campeonatoDto) {
    // Future validations might be added, for now there none
    return true;
  }

  /**
   * Updates an existing campeonato.
   *
   * @param id Campeonato id
   * @param campeonatoDto Updated details
   * @return Updated CampeonatoDto, null if not found, or with id -1 if conflict
   */
  public CampeonatoDto atualizarCampeonato(Long id, CampeonatoDto campeonatoDto) {
    if (!validatorAtualizarCampeonato(id, campeonatoDto)) {
      campeonatoDto.setId(-1L);
      return campeonatoDto;
    }
    if (verificarCampeonato(id) == null) return null;
    campeonatoRepository.save(
        CampeonatoMapper.dtoToCampeonato(campeonatoDto, equipaRepository, jogoRepository));
    return verificarCampeonato(id);
  }

  private boolean validatorAtualizarCampeonato(Long id, CampeonatoDto campeonatoDto) {
    // TODO Keep on adding more business rules
    if (!(id == campeonatoDto.getId())) return false;
    return true;
  }

  /**
   * Deletes campeonato by id.
   *
   * @param id Campeonato id
   * @return Deleted CampeonatoDto, null if not found, or with id -1 if conflict
   */
  public CampeonatoDto removerCampeonato(Long id) {
    Campeonato campeonato = campeonatoRepository.findById(id).get();
    if (campeonato == null) return null;
    if (!validatorRemoverCampeonato(id, campeonato)) {
      campeonatoRepository.deleteById(id);
    } else {
      campeonato.setId(-1L);
    }
    return CampeonatoMapper.campeonatoToDto(campeonato);
  }

  private boolean validatorRemoverCampeonato(Long id, Campeonato campeonato) {
    // TODO keep on adding business rules
    return true;
  }
}
