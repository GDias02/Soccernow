package pt.ul.fc.css.soccernow.handlers;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.entities.campeonatos.Campeonato;
import pt.ul.fc.css.soccernow.filters.campeonatos.CampeonatoPredicate;
import pt.ul.fc.css.soccernow.filters.campeonatos.CampeonatoPredicatesBuilder;
import pt.ul.fc.css.soccernow.mappers.campeonatos.CampeonatoMapper;
import pt.ul.fc.css.soccernow.mappers.jogos.JogoMapper;
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
  @Transactional
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
    if (id != campeonatoDto.getId()) return false;
    return true;
  }

  /**
   * Deletes campeonato by id.
   *
   * @param id Campeonato id
   * @return Deleted CampeonatoDto, null if not found, or with id -1 if conflict
   */
  @Transactional
  public CampeonatoDto removerCampeonato(Long id) {
    Campeonato campeonato = campeonatoRepository.findById(id).get();
    if (campeonato == null) return null;
    if (validatorRemoverCampeonato(id, campeonato)) {
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

  public List<CampeonatoDto> search(String search) {
    CampeonatoPredicatesBuilder builder = new CampeonatoPredicatesBuilder();

    if (search != null) {
      Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
      Matcher matcher = pattern.matcher(search + ",");
      while (matcher.find()) {
        builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
      }
    }

    BooleanExpression exp = builder.build(CampeonatoPredicate::new);

    List<Campeonato> campeonatos = new ArrayList<>();
    campeonatoRepository.findAll(exp).forEach(campeonatos::add);

    return CampeonatoMapper.manyCampeonatosToDtos(campeonatos);
  }

  public List<JogoDto> getJogadoresByCampeonatoId (Long id) {
    Campeonato campeonato = campeonatoRepository.findById(id).get();
    return campeonato.getJogos().stream().map(JogoMapper::jogoToDto).toList();
  }
}
