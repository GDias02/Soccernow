package pt.ul.fc.css.soccernow.handlers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

public class JogadorHandler implements IJogadorHandler {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Override
    public JogadorDto registarJogador(JogadorDto jogadorDto) {
        if (!validInput(jogadorDto)) return null;

        UtilizadorDto utilizadorDto = jogadorDto.getUtilizador();

        if (utilizadorDto.getId() != null 
            || !jogadorRepository.findByNif(utilizadorDto.getNif()).isEmpty()) return null;

        EstatisticaJogadorDto estatisticaJogadorDto = new EstatisticaJogadorDto();
        estatisticaJogadorDto.setGolos(new HashSet<>());
        estatisticaJogadorDto.setCartoes(new HashSet<>());
        jogadorDto.setEstatisticas(estatisticaJogadorDto);

        Jogador jogador = JogadorMapper.dtoToJogador(jogadorDto);
        Jogador savedJogador = jogadorRepository.save(jogador);

        utilizadorDto.setId(savedJogador.getId());
        jogadorDto.setUtilizador(utilizadorDto);

        return jogadorDto;
    }

    @Override
    public JogadorDto verificarJogador(int nif) {
        Optional<Jogador> maybeJogador = jogadorRepository.findByNif(nif);
        return maybeJogador.isEmpty() ? null : JogadorMapper.jogadorToDto(maybeJogador.get());
    }

    @Override
    public JogadorDto removerJogador(int nif) {
        Optional<Jogador> maybeJogador = jogadorRepository.findByNifAndDelete(nif);
        return maybeJogador.isEmpty() ? null : JogadorMapper.jogadorToDto(maybeJogador.get());
    }

    @Override
    public JogadorDto atualizarJogador(JogadorDto jogadorDto) {
        if (!validInput(jogadorDto)) return null;

        UtilizadorDto utilizadorDto = jogadorDto.getUtilizador();
        Long id = utilizadorDto.getId();
        if (id == null || jogadorRepository.findById(id).isEmpty()
            || !validInputEstatisticas(jogadorDto.getEstatisticas())) return null;

        Jogador jogador = JogadorMapper.dtoToJogador(jogadorDto);
        jogadorRepository.save(jogador);

        return jogadorDto;
    }

    @Override
    public Set<JogadorDto> buscarJogadores() {
        return jogadorRepository.findAll().stream().map(JogadorMapper::jogadorToDto).collect(Collectors.toSet());
    }

    private boolean validInput(JogadorDto jogadorDto) {
        if (jogadorDto == null) return false;

        UtilizadorDto utilizadorDto = jogadorDto.getUtilizador();
        if (utilizadorDto == null) return false;
        int nif = utilizadorDto.getNif();

        return (100000000 <= nif && nif <= 999999999)
            && isFilled(utilizadorDto.getNome())
            && isFilled(utilizadorDto.getContacto())
            && jogadorDto.getPosicaoPreferida() != null;
    }

    private boolean isFilled(String field) {
        return field != null && field.length() > 0;
    }

    private boolean validInputEstatisticas(EstatisticaJogadorDto estatisticaJogadorDto) {
        if (estatisticaJogadorDto == null) return false;
        
        Set<GoloDto> golos = estatisticaJogadorDto.getGolos();
        Set<CartaoDto> cartoes = estatisticaJogadorDto.getCartoes();

        for (GoloDto golo : golos)
            if (golo.getQuando() == null)
                return false;

        for (CartaoDto cartao : cartoes)
            if (cartao.getQuando() == null || cartao.getCor() == null)
                return false;
        
        return true;
    }
    
}
