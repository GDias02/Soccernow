package pt.ul.fc.css.soccernow.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaArbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Certificado;
import pt.ul.fc.css.soccernow.entities.utilizadores.QArbitro;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarArbitroException;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaArbitroMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.ArbitroPostMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.CertificadoMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

@Service
public class ArbitroHandler implements IArbitroHandler {

  @Autowired private ArbitroRepository arbitroRepository;

  @Autowired private JogadorRepository jogadorRepository;

  @Autowired private EstatisticasHandler estatisticasHandler;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public ArbitroPostDto registarArbitro(ArbitroPostDto arbitroDto) throws RegistarArbitroException {
    try {
      validInput(arbitroDto);
    } catch (IllegalArgumentException e) {
      throw new RegistarArbitroException(e.getMessage());
    }

    UtilizadorDto utilizadorDto = arbitroDto.getUtilizador();
    int nif = utilizadorDto.getNif();
    if (!jogadorRepository.findByNif(nif).isEmpty() || !arbitroRepository.findByNif(nif).isEmpty())
      throw new RegistarArbitroException("Já existe/existiu um utilizador com esse nif");

    Arbitro arbitro = ArbitroPostMapper.dtoToArbitro(arbitroDto);
    arbitro.setId(0L);

    Arbitro savedArbitro = new Arbitro();
    try {
      savedArbitro = arbitroRepository.save(arbitro);
    } catch (IllegalArgumentException e) {
      throw new RegistarArbitroException("Erro a registar árbitro: " + e.getMessage());
    }

    ArbitroPostDto responseDto = ArbitroPostMapper.arbitroToDto(savedArbitro);

    return responseDto;
  }

  @Override
  @Transactional
  public ArbitroDto verificarArbitro(int nif) throws VerificarArbitroException, NotFoundException {
    if (!(100000000 <= nif && nif <= 999999999))
      throw new VerificarArbitroException("O nif do árbitro tem de ter 9 dígitos");

    Optional<Arbitro> maybeArbitro = arbitroRepository.findAliveByNif(nif);
    if (maybeArbitro.isEmpty()) throw new NotFoundException("O árbitro não existe");

    Arbitro arbitro = maybeArbitro.get();
    ArbitroDto arbitroDto = ArbitroMapper.arbitroToDto(arbitro);
    EstatisticaArbitro estatisticas = estatisticasHandler.criarEstatisticaArbitro(arbitroDto);
    arbitroDto.setEstatisticas(EstatisticaArbitroMapper.estatisticaArbitroToDto(estatisticas));
    
    return arbitroDto;
  }

  @Override
  @Transactional
  public void removerArbitro(int nif) throws RemoverArbitroException, NotFoundException {
    if (!(100000000 <= nif && nif <= 999999999))
      throw new RemoverArbitroException("O nif do árbitro tem de ter 9 dígitos");

    Optional<Arbitro> maybeArbitro = arbitroRepository.findAliveByNif(nif);
    if (maybeArbitro.isEmpty()) throw new NotFoundException("O árbitro não existe");

    arbitroRepository.deleteByNif(nif);
  }

  @Override
  @Transactional
  public ArbitroPostDto atualizarArbitro(ArbitroPostDto arbitroDto)
      throws AtualizarArbitroException, NotFoundException {
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
    if ((!maybeDuplicate.isEmpty() && !id.equals(maybeDuplicate.get().getId()))
        || !jogadorRepository.findByNif(nif).isEmpty())
      throw new AtualizarArbitroException("Já existe/existiu um utilizador com esse nif");

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

    ArbitroPostDto responseDto = ArbitroPostMapper.arbitroToDto(updatedArbitro);
    return responseDto;
  }

  @Transactional
  public Set<ArbitroDto> buscarArbitros() {
      Set<ArbitroDto> arbitroDtos = arbitroRepository.findAliveAll().stream().map(ArbitroMapper::arbitroToDto).collect(Collectors.toSet());
      for (ArbitroDto arbitroDto : arbitroDtos) {
          EstatisticaArbitro estatisticas = estatisticasHandler.criarEstatisticaArbitro(arbitroDto);
          arbitroDto.setEstatisticas(EstatisticaArbitroMapper.estatisticaArbitroToDto(estatisticas));
      }
      return arbitroDtos;
  }

  private void validInput(ArbitroPostDto arbitroDto) throws IllegalArgumentException {
    if (arbitroDto == null) throw new IllegalArgumentException("Sem dados para o árbitro");

    UtilizadorDto utilizadorDto = arbitroDto.getUtilizador();
    if (utilizadorDto == null)
      throw new IllegalArgumentException("Sem dados de utilizador para o árbitro");
    int nif = utilizadorDto.getNif();

    if (!(100000000 <= nif && nif <= 999999999))
      throw new IllegalArgumentException("O nif do árbitro tem de ter 9 dígitos");
    if (!isFilled(utilizadorDto.getNome()))
      throw new IllegalArgumentException("O nome do árbitro é obrigatório");
  }

  private boolean isFilled(String field) {
    return field != null && field.length() > 0;
  }

  public Set<ArbitroDto> filtrarArbitros(String nome, String jogos, String cartoes) {
    if (nome == null) nome = "";
    if (jogos == null) jogos = "";
    if (cartoes == null) cartoes = "";

    QArbitro arbitro = QArbitro.arbitro;
    JPAQuery<Arbitro> query = new JPAQuery<>(entityManager);

    FetchableQueryBase<Arbitro, JPAQuery<Arbitro>> fetchable = 
        (FetchableQueryBase<Arbitro, JPAQuery<Arbitro>>) query.from(arbitro)
            .where(
                arbitro.deleted.eq(false),
                arbitro.nome.containsIgnoreCase(nome)
            )
            .distinct();

    List<Arbitro> arbitros = fetchable.fetch();

    Set<ArbitroDto> arbitroDtos = arbitros.stream().map(ArbitroMapper::arbitroToDto).collect(Collectors.toSet());
    Set<ArbitroDto> arbitrosFinaisDtos = new HashSet<>();
    for (ArbitroDto arbitroDto : arbitroDtos) {
        EstatisticaArbitro estatisticas = estatisticasHandler.criarEstatisticaArbitro(arbitroDto);
        if (filtroEstatisticas(estatisticas, jogos, cartoes)) {
            arbitroDto.setEstatisticas(EstatisticaArbitroMapper.estatisticaArbitroToDto(estatisticas));
            arbitrosFinaisDtos.add(arbitroDto);
        }
    }
    return arbitrosFinaisDtos;
  }

  private boolean filtroEstatisticas(EstatisticaArbitro estatisticas, String jogosStr, String cartoesStr) {
    if (jogosStr.matches("\\d+")) {
        int jogos = Integer.parseInt(jogosStr);
        if (estatisticas.getJogos() != jogos) return false;
    }
    if (cartoesStr.matches("\\d+")) {
        int cartoes = Integer.parseInt(cartoesStr);
        if (estatisticas.getCartoes().size() != cartoes) return false;
    }
    return true;
  }
}
