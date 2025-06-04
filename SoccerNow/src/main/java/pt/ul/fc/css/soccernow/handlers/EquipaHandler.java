package pt.ul.fc.css.soccernow.handlers;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.entities.equipas.Equipa;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.entities.jogos.Jogo;
import pt.ul.fc.css.soccernow.filters.equipas.EquipaPredicate;
import pt.ul.fc.css.soccernow.filters.equipas.EquipaPredicatesBuilder;
import pt.ul.fc.css.soccernow.mappers.equipas.EquipaMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.repositories.EquipaRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;
import pt.ul.fc.css.soccernow.repositories.JogoRepository;

@Service
public class EquipaHandler implements IEquipaHandler {

  @Autowired private EquipaRepository equipaRepository;

  @Autowired private JogadorRepository jogadorRepository;

  @Autowired private JogoRepository jogoRepository;

  @Override
  @Transactional(readOnly = true)
  public EquipaDto verificarEquipa(Long id) {
    Optional<Equipa> e = equipaRepository.findById(id);
    if (e.isEmpty()) return null;
    else return EquipaMapper.equipaToDto(e.get());
  }

  @Override
  @Transactional
  public EquipaDto removerEquipa(Long id) {
    EquipaDto e = verificarEquipa(id);

    if (e == null) return null;

    boolean valid =
        validatorRemoverEquipa(
            EquipaMapper.dtoToEquipa(e, this.jogadorRepository, this.jogoRepository));
    if (valid) {
      equipaRepository.deleteById(id); // delete
    } else {
      e.setId(-1L); // send error
    }

    return e;
  }

  private boolean validatorRemoverEquipa(Equipa e) {
    // There is only one validation at the moment,
    // If there is a jogo that is after today, then equipa can't be deleted
    List<Jogo> jogos = e.getHistoricoDeJogos();
    jogos.sort(Comparator.comparing(Jogo::getDiaEHora));
    if (!jogos.isEmpty() && jogos.get(jogos.size() - 1).getDiaEHora().isAfter(LocalDateTime.now()))
      return false;
    // future validations...
    return true;
  }

  @Override
  @Transactional
  public EquipaDto atualizarEquipa(Long id, EquipaDto equipaDto) {
    EquipaDto e = verificarEquipa(equipaDto.getId());
    int valid = validatorAtualizarEquipa(id, equipaDto);
    if (e != null && valid == 1) {
      // If it exists and it's valid, then save
      equipaRepository.save(
          EquipaMapper.dtoToEquipa(equipaDto, this.jogadorRepository, this.jogoRepository));
    } else if (e != null && valid == -1) {
      e.setId(-1L);
      return e;
    }
    // GetEquipa(Id) para obter a versão mais atualizada de equipa
    return valid == 1 ? verificarEquipa(id) : null;
  }

  private int validatorAtualizarEquipa(Long id, EquipaDto equipaDto) {
    // Make sure id == equipaDto.getId()
    if (!id.equals(equipaDto.getId())) return -1;

    // Make sure that every previous game is in historico
    List<Jogo> historicoOriginal =
        equipaRepository.findById(equipaDto.getId()).get().getHistoricoDeJogos();
    for (Jogo jogo : historicoOriginal) {
      // If the jogo was cancelado ou agendado it may be removed
      if (jogo.getEstadoDeJogo() == EstadoDeJogo.CANCELADO) continue;
      // If the jogo is not in histórico de jogos, this is not a valid request
      if (!equipaDto.getHistoricoDeJogos().contains(jogo.getId())) return -1;
    }
    // future validations...
    return 1;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EquipaDto> verificarEquipas() {
    return EquipaMapper.manyEquipasToDtos(equipaRepository.findAll());
  }

  @Override
  @Transactional
  public EquipaDto registarEquipa(EquipaDto equipaDto) {
    boolean valid = validatorRegistarEquipa(equipaDto);
    Equipa savedEquipa = null;
    if (valid) {
      Equipa equipa =
          EquipaMapper.dtoToEquipa(equipaDto, this.jogadorRepository, this.jogoRepository);
      savedEquipa = equipaRepository.save(equipa);
    }
    return EquipaMapper.equipaToDto(savedEquipa);
  }

  private boolean validatorRegistarEquipa(EquipaDto equipaDto) {
    // Future validations might be added, for now there none
    return true;
  }

  public List<EquipaDto> search(String search) {
    EquipaPredicatesBuilder builder = new EquipaPredicatesBuilder();

    if (search != null) {
      Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
      Matcher matcher = pattern.matcher(search + ",");
      while (matcher.find()) {
        builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
      }
    }
    BooleanExpression exp = builder.build(EquipaPredicate::new);

    List<Equipa> equipas = new ArrayList<>();
    equipaRepository.findAll(exp).forEach(equipas::add);

    return EquipaMapper.manyEquipasToDtos(equipas);
  }

  @Transactional(readOnly = true)
  public Set<JogadorDto> buscarJogadoresDeEquipa(Long equipaId) {
    Optional<Equipa> e = equipaRepository.findById(equipaId);
    if (e.isEmpty()) return new HashSet<>();
    return e.get().getJogadores().stream()
            .map(JogadorMapper::jogadorToDto)
            .collect(Collectors.toSet());
  }

  @Transactional(readOnly = true)
  public List<Jogo> buscarJogosDeEquipa(Long equipaId) {
    Optional<Equipa> e = equipaRepository.findById(equipaId);
    if (e.isEmpty()) return new ArrayList<>();
    return e.get().getHistoricoDeJogos();
  }
}
