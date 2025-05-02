package pt.ul.fc.css.soccernow.handlers;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Jogador;
import pt.ul.fc.css.soccernow.mappers.utilizadores.JogadorMapper;
import pt.ul.fc.css.soccernow.repositories.JogadorRepository;

@Service
public class JogadorHandler implements IJogadorHandler {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Override
    public JogadorDto registarJogador(JogadorPostDto jogadorPostDto) {
        if (jogadorPostDto == null) return null;

        JogadorDto jogadorDto = new JogadorDto(jogadorPostDto);
        if (!validInput(jogadorDto)) return null;

        if (jogadorDto.getUtilizador().getId() != 0) return null;

        Jogador jogador = JogadorMapper.dtoToJogador(jogadorDto);
        Jogador savedJogador = jogadorRepository.save(jogador);

        jogadorDto.getUtilizador().setId(savedJogador.getId());

        return jogadorDto;
    }

    @Override
    public JogadorDto verificarJogador(int nif) {
        Optional<Jogador> maybeJogador = jogadorRepository.findByNif(nif);
        return maybeJogador.isEmpty() ? null : JogadorMapper.jogadorToDto(maybeJogador.get());
    }

    @Override
    public void removerJogador(int nif) {
        jogadorRepository.deleteByNif(nif);
    }

    @Override
    public JogadorDto atualizarJogador(JogadorDto jogadorDto) {
        if (!validInput(jogadorDto)) return null;

        UtilizadorDto utilizador = jogadorDto.getUtilizador();

        Long id = utilizador.getId();
        if (id == 0) return null;

        if (jogadorRepository.findById(id).isEmpty() ||
            !validInputEstatisticas(jogadorDto.getEstatisticas().getGolos(), jogadorDto.getEstatisticas().getCartoes()))
            return null;

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

    private boolean validInputEstatisticas(Set<GoloDto> golos, Set<CartaoDto> cartoes) {
        if (golos != null)
            for (GoloDto golo : golos)
                if (golo.getQuando() == null)
                    return false;

        if (cartoes != null)
            for (CartaoDto cartao : cartoes)
                if (cartao.getQuando() == null || cartao.getCor() == null)
                    return false;
        
        return true;
    }
    
}
