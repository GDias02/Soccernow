package pt.ul.fc.css.soccernow.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;
import pt.ul.fc.css.soccernow.entities.utilizadores.QJogador;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarJogadorException;
import pt.ul.fc.css.soccernow.mappers.jogos.EstatisticaJogadorMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorPostMapper;
import pt.ul.fc.css.soccernow.repositories.ArbitroRepository;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

@Service
public class JogadorHandler implements IJogadorHandler {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Autowired
    private EstatisticasHandler estatisticasHandler;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public JogadorDto registarJogador(JogadorPostDto jogadorPostDto) throws RegistarJogadorException {
        if (jogadorPostDto == null) throw new RegistarJogadorException("Sem dados para o jogador");

        JogadorDto jogadorDto = new JogadorDto(jogadorPostDto);
        UtilizadorDto utilizadorDto = jogadorDto.getUtilizador();

        try {
            validInput(utilizadorDto);
        } catch (IllegalArgumentException e) {
            throw new RegistarJogadorException(e.getMessage());
        }

        int nif = utilizadorDto.getNif();
        if (!jogadorRepository.findByNif(nif).isEmpty() || !arbitroRepository.findByNif(nif).isEmpty())
            throw new RegistarJogadorException("Já existe/existiu um utilizador com esse nif");

        Jogador jogador = JogadorMapper.dtoToJogador(jogadorDto);
        jogador.setId(0L);

        Jogador savedJogador = new Jogador();
        try {
            savedJogador = jogadorRepository.save(jogador);
        } catch (IllegalArgumentException e) {
            throw new RegistarJogadorException("Erro a registar jogador: " + e.getMessage());
        }

        JogadorDto responseDto = JogadorMapper.jogadorToDto(savedJogador);
        responseDto.setEstatisticas(new EstatisticaJogadorDto(new HashSet<>(), new HashSet<>(), 0));

        return responseDto;
    }

    @Override
    @Transactional
    public JogadorDto verificarJogador(int nif) throws VerificarJogadorException, NotFoundException {
        if (!(100000000 <= nif && nif <= 999999999)) throw new VerificarJogadorException("O nif do jogador tem de ter 9 dígitos");

        Optional<Jogador> maybeJogador = jogadorRepository.findAliveByNif(nif);
        if (maybeJogador.isEmpty()) throw new NotFoundException("O jogador não existe");

        Jogador jogador = maybeJogador.get();
        JogadorDto jogadorDto = JogadorMapper.jogadorToDto(jogador);
        EstatisticaJogador estatisticas = estatisticasHandler.criarEstatisticaJogador(jogadorDto);
        jogadorDto.setEstatisticas(EstatisticaJogadorMapper.estatisticaJogadorToDto(estatisticas));

        return jogadorDto;
    }

    @Override
    @Transactional
    public void removerJogador(int nif) throws RemoverJogadorException, NotFoundException {
        if (!(100000000 <= nif && nif <= 999999999)) throw new RemoverJogadorException("O nif do jogador tem de ter 9 dígitos");

        Optional<Jogador> maybeJogador = jogadorRepository.findAliveByNif(nif);
        if (maybeJogador.isEmpty()) throw new NotFoundException("O jogador não existe");

        estatisticasHandler.removerEstatisticaJogador(JogadorMapper.jogadorToDto(maybeJogador.get()));
        jogadorRepository.deleteByNif(nif);
    }

    @Override
    @Transactional
    public JogadorPostDto atualizarJogador(JogadorPostDto jogadorDto) throws AtualizarJogadorException, NotFoundException {
        if (jogadorDto == null) throw new AtualizarJogadorException("Sem dados para o jogador");

        UtilizadorDto utilizador = jogadorDto.getUtilizador();

        try {
            validInput(utilizador);
        } catch (IllegalArgumentException e) {
            throw new AtualizarJogadorException(e.getMessage());
        }

        Long id = utilizador.getId();
        if (!(id > 0)) throw new AtualizarJogadorException("O id do jogador tem de ser positivo");
        Optional<Jogador> maybeJogador = jogadorRepository.findAliveById(id);
        if (maybeJogador.isEmpty()) throw new NotFoundException("O jogador não existe");

        int nif = utilizador.getNif();
        Optional<Jogador> maybeDuplicate = jogadorRepository.findByNif(nif);
        if ((!maybeDuplicate.isEmpty() && !id.equals(maybeDuplicate.get().getId())) || !arbitroRepository.findByNif(nif).isEmpty())
            throw new AtualizarJogadorException("Já existe/existiu um utilizador com esse nif");

        Jogador jogador = maybeJogador.get();
        jogador.setNif(utilizador.getNif());
        jogador.setNome(utilizador.getNome());
        jogador.setContacto(utilizador.getContacto());
        jogador.setPosicaoPreferida(jogadorDto.getPosicaoPreferida());

        Jogador updatedJogador = new Jogador();
        try {
            updatedJogador = jogadorRepository.save(jogador);
        } catch (IllegalArgumentException e) {
            throw new AtualizarJogadorException("Erro a atualizar jogador: " + e.getMessage());
        }
        
        JogadorPostDto responseDto = JogadorPostMapper.jogadorToDto(updatedJogador);
        return responseDto;
    }

    @Override
    @Transactional
    public Set<JogadorDto> buscarJogadores() {
        Set<JogadorDto> jogadorDtos = jogadorRepository.findAliveAll().stream().map(JogadorMapper::jogadorToDto).collect(Collectors.toSet());
        for (JogadorDto jogadorDto : jogadorDtos) {
            EstatisticaJogador estatisticas = estatisticasHandler.criarEstatisticaJogador(jogadorDto);
            jogadorDto.setEstatisticas(EstatisticaJogadorMapper.estatisticaJogadorToDto(estatisticas));
        }
        return jogadorDtos;
    }

    private void validInput(UtilizadorDto utilizador) throws IllegalArgumentException {
        if (utilizador == null) throw new IllegalArgumentException("Sem dados de utilizador para o jogador");

        int nif = utilizador.getNif();
        String nome = utilizador.getNome();

        if (!(100000000 <= nif && nif <= 999999999)) throw new IllegalArgumentException("O nif do jogador tem de ter 9 dígitos");
        if (!isFilled(nome)) throw new IllegalArgumentException("O nome do jogador é obrigatório");
    }

    private boolean isFilled(String field) {
        return field != null && field.length() > 0;
    }

    public Set<JogadorDto> filtrarJogadores(String nome, String posicao, String golos, String cartoes, String jogos) {
        if (nome == null) nome = "";
        if (posicao == null) posicao = "";
        if (golos == null) golos = "";
        if (cartoes == null) cartoes = "";
        if (jogos == null) jogos = "";

        Posicao pos = null;
        if (posicao.length() > 0) pos = Enum.valueOf(Posicao.class, posicao);

        QJogador jogador = QJogador.jogador;
        JPAQuery<Jogador> query = new JPAQuery<>(entityManager);

        FetchableQueryBase<Jogador, JPAQuery<Jogador>> fetchable = 
            (FetchableQueryBase<Jogador, JPAQuery<Jogador>>) query.from(jogador)
                .where(
                    jogador.deleted.eq(false),
                    jogador.nome.containsIgnoreCase(nome),
                    filtroPosicao(jogador.posicaoPreferida, pos)
                )
                .distinct();

        List<Jogador> jogadores = fetchable.fetch();

        Set<JogadorDto> jogadorDtos = jogadores.stream().map(JogadorMapper::jogadorToDto).collect(Collectors.toSet());
        Set<JogadorDto> jogadoresFinaisDtos = new HashSet<>();
        for (JogadorDto jogadorDto : jogadorDtos) {
            EstatisticaJogador estatisticas = estatisticasHandler.criarEstatisticaJogador(jogadorDto);
            if (filtroEstatisticas(estatisticas, golos, cartoes, jogos)) {
                jogadorDto.setEstatisticas(EstatisticaJogadorMapper.estatisticaJogadorToDto(estatisticas));
                jogadoresFinaisDtos.add(jogadorDto);
            }
        }
        return jogadoresFinaisDtos;
    }

    private BooleanExpression filtroPosicao(EnumPath<Posicao> posicaoPreferida, Posicao pos) {
        if (pos == null) return Expressions.asBoolean(true).isTrue();
        else return posicaoPreferida.eq(pos);
    }

    private boolean filtroEstatisticas(EstatisticaJogador estatisticas, String golosStr, String cartoesStr, String jogosStr) {
        if (golosStr.matches("\\d+")) {
            int golos = Integer.parseInt(golosStr);
            if (estatisticas.getGolos().size() != golos) return false;
        }
        if (cartoesStr.matches("\\d+")) {
            int cartoes = Integer.parseInt(cartoesStr);
            if (estatisticas.getCartoes().size() != cartoes) return false;
        }
        if (jogosStr.matches("\\d+")) {
            int jogos = Integer.parseInt(jogosStr);
            if (estatisticas.getJogos() != jogos) return false;
        }
        return true;
    }

    /* private List<Long> filtroGolos(String golosStr) {
        if (!golosStr.matches("\\d+")) return new ArrayList<>();

        int golos = Integer.parseInt(golosStr);

        QGolo golo = QGolo.golo;
        JPAQuery<Golo> queryGolo = new JPAQuery<>(entityManager);

        List<Long> jogadorIds = queryGolo
                .from(golo)
                .groupBy(golo.marcador, golo.id, golo.marcador.deleted)
                .having(golo.marcador.deleted.eq(false), golo.marcador.count().eq((long) golos))
                .select(golo.marcador.id)
                .fetch();
        
        return jogadorIds;
    } */
}
