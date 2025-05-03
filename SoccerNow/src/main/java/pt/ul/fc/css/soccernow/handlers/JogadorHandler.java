package pt.ul.fc.css.soccernow.handlers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstatisticaJogador;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
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

    @Override
    @Transactional
    public JogadorDto registarJogador(JogadorPostDto jogadorPostDto) {
        if (jogadorPostDto == null) return null;

        JogadorDto jogadorDto = new JogadorDto(jogadorPostDto);
        if (!validInput(jogadorDto)) return null;

        UtilizadorDto utilizadorDto = jogadorDto.getUtilizador();

        if (utilizadorDto.getId() != 0) return null;

        int nif = utilizadorDto.getNif();
        if (!jogadorRepository.findByNif(nif).isEmpty() || !arbitroRepository.findByNif(nif).isEmpty()) return null;

        Jogador jogador = JogadorMapper.dtoToJogador(jogadorDto);
        Jogador savedJogador = new Jogador();
        try {
            savedJogador = jogadorRepository.save(jogador);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        JogadorDto responseDto = JogadorMapper.jogadorToDto(savedJogador);
        responseDto.setEstatisticas(new EstatisticaJogadorDto(new HashSet<>(), new HashSet<>()));

        return responseDto;
    }

    @Override
    @Transactional
    public JogadorDto verificarJogador(int nif) {
        Optional<Jogador> maybeJogador = jogadorRepository.findByNif(nif);

        JogadorDto jogadorDto = null;
        Jogador jogador = null;
        if (!maybeJogador.isEmpty()) {
            jogador = maybeJogador.get();
            jogadorDto = JogadorMapper.jogadorToDto(jogador);
            EstatisticaJogador estatisticas = estatisticasHandler.criarEstatisticaJogador(jogadorDto);
            jogadorDto.setEstatisticas(EstatisticaJogadorMapper.estatisticaJogadorToDto(estatisticas));
        }
        return jogadorDto;
    }

    @Override
    @Transactional
    public void removerJogador(int nif) {
        Optional<Jogador> maybeJogador = jogadorRepository.findByNif(nif);
        jogadorRepository.deleteByNif(nif);
        if (!maybeJogador.isEmpty()) 
            estatisticasHandler.removerEstatisticaJogador(JogadorMapper.jogadorToDto(maybeJogador.get()));
    }

    @Override
    @Transactional
    public JogadorPostDto atualizarJogador(JogadorPostDto jogadorDto) {
        if (!validPostInput(jogadorDto)) return null;

        UtilizadorDto utilizador = jogadorDto.getUtilizador();

        Long id = utilizador.getId();
        if (id == 0) return null;

        int nif = utilizador.getNif();
        if (!arbitroRepository.findByNif(nif).isEmpty()) return null;

        Optional<Jogador> maybeJogador = jogadorRepository.findById(id);
        if (maybeJogador.isEmpty()) return null;
        Jogador jogador = maybeJogador.get();
        jogador.setNif(utilizador.getNif());
        jogador.setNome(utilizador.getNome());
        jogador.setContacto(utilizador.getContacto());
        jogador.setPosicaoPreferida(jogadorDto.getPosicaoPreferida());

        Jogador updatedJogador = jogadorRepository.save(jogador);
        JogadorPostDto responseDto = JogadorPostMapper.jogadorToDto(updatedJogador);

        return responseDto;
    }

    @Override
    @Transactional
    public Set<JogadorDto> buscarJogadores() {
        Set<JogadorDto> jogadorDtos = jogadorRepository.findAll().stream().map(JogadorMapper::jogadorToDto).collect(Collectors.toSet());
        for (JogadorDto jogadorDto : jogadorDtos) {
            EstatisticaJogador estatisticas = estatisticasHandler.criarEstatisticaJogador(jogadorDto);
            jogadorDto.setEstatisticas(EstatisticaJogadorMapper.estatisticaJogadorToDto(estatisticas));
        }
        return jogadorDtos;
    }

    private boolean validInput(JogadorDto jogadorDto) {
        if (jogadorDto == null) return false;

        UtilizadorDto utilizador = jogadorDto.getUtilizador();
        if (utilizador == null) return false;

        int nif = utilizador.getNif();

        return (100000000 <= nif && nif <= 999999999)
            && isFilled(utilizador.getNome())
            && jogadorDto.getPosicaoPreferida() != null;
    }

    private boolean validPostInput(JogadorPostDto jogadorDto) {
        if (jogadorDto == null) return false;

        UtilizadorDto utilizador = jogadorDto.getUtilizador();
        if (utilizador == null) return false;

        int nif = utilizador.getNif();

        return (100000000 <= nif && nif <= 999999999)
            && isFilled(utilizador.getNome())
            && isFilled(utilizador.getContacto())
            && jogadorDto.getPosicaoPreferida() != null;
    }

    private boolean isFilled(String field) {
        return field != null && field.length() > 0;
    }
    
}
