/* package pt.ul.fc.css.soccernow.entities.utilizadores;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@DataJpaTest
@AutoConfigureTestDatabase()
public class JogadorTest {

    @Autowired
    private JogadorHandler jogadorHandler;

    @BeforeEach
    void setup() {
        jogadorHandler = new JogadorHandler();
    }
    
    @Test
    @Rollback(false)
    @DisplayName("Create Jogador")
    public void testCreateJogador() {
        Long id = 0L;
        int nif = 111111111;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.GUARDA_REDES;
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorPostDto jogadorPostDto = new JogadorPostDto(utilizadorDto, posicao);

        JogadorDto responseDto = jogadorHandler.registarJogador(jogadorPostDto);

        assertNotNull(responseDto);

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertNotEquals(responseUtilizador.getId(), 0L);
        assertEquals(responseUtilizador.getNif(), nif);
        assertEquals(responseUtilizador.getNome(), nome);
        assertEquals(responseUtilizador.getContacto(), contacto);

        assertEquals(responseDto.getPosicaoPreferida(), posicao);

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Rollback(false)
    @DisplayName("Create Jogador with duplicate nif")
    public void testCreateDuplicateNifJogador() {
        Long id = 0L;
        int nif = 111111111;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.GUARDA_REDES;
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorPostDto jogadorPostDto = new JogadorPostDto(utilizadorDto, posicao);

        JogadorDto responseDto = jogadorHandler.registarJogador(jogadorPostDto);

        assertNull(responseDto);
    }

    @Test
    @Rollback(false)
    @DisplayName("Create Jogador with invalid nif")
    public void testCreateInvalidNifJogador() {
        Long id = 0L;
        int nif = 111;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.GUARDA_REDES;
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorPostDto jogadorPostDto = new JogadorPostDto(utilizadorDto, posicao);

        JogadorDto responseDto = jogadorHandler.registarJogador(jogadorPostDto);

        assertNull(responseDto);
    }

    @Test
    @Rollback(false)
    @DisplayName("Update Jogador nif and posicao")
    public void testUpdateJogador() {
        Long id = 1L;
        int nif = 222222222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;
        EstatisticaJogadorDto estatisticaDto = new EstatisticaJogadorDto();
        estatisticaDto.setGolos(new HashSet<>());
        estatisticaDto.setCartoes(new HashSet<>());
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorDto jogadorDto = new JogadorDto(utilizadorDto, posicao, estatisticaDto);

        JogadorDto responseDto = jogadorHandler.atualizarJogador(jogadorDto);

        assertNotNull(responseDto);

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertEquals(responseUtilizador.getId(), id);
        assertEquals(responseUtilizador.getNif(), nif);
        assertEquals(responseUtilizador.getNome(), nome);
        assertEquals(responseUtilizador.getContacto(), contacto);

        assertEquals(responseDto.getPosicaoPreferida(), posicao);

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Rollback(false)
    @DisplayName("Update Jogador to have invalid nif")
    public void testUpdateInvalidNifJogador() {
        Long id = 1L;
        int nif = 222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;
        EstatisticaJogadorDto estatisticaDto = new EstatisticaJogadorDto();
        estatisticaDto.setGolos(new HashSet<>());
        estatisticaDto.setCartoes(new HashSet<>());
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorDto jogadorDto = new JogadorDto(utilizadorDto, posicao, estatisticaDto);

        JogadorDto responseDto = jogadorHandler.atualizarJogador(jogadorDto);

        assertNull(responseDto);
    }

    @Test
    @Rollback(false)
    @DisplayName("Get Jogador by nif")
    public void testGetJogador() {
        Long id = 1L;
        int nif = 222222222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;

        JogadorDto responseDto = jogadorHandler.verificarJogador(nif);

        assertNotNull(responseDto);

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertEquals(responseUtilizador.getId(), id);
        assertEquals(responseUtilizador.getNif(), nif);
        assertEquals(responseUtilizador.getNome(), nome);
        assertEquals(responseUtilizador.getContacto(), contacto);

        assertEquals(responseDto.getPosicaoPreferida(), posicao);

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Rollback(false)
    @DisplayName("Get Jogador by non-existent nif")
    public void testGetInvalidJogador() {
        int nif = 111111111;

        JogadorDto responseDto = jogadorHandler.verificarJogador(nif);

        assertNull(responseDto);
    }

    @Test
    @Rollback(false)
    @DisplayName("Get all Jogadores")
    public void testGetAllJogador() {
        Long id = 1L;
        int nif = 222222222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;

        Set<JogadorDto> responseDtos = jogadorHandler.buscarJogadores();

        assertEquals(responseDtos.size(), 1);

        JogadorDto responseDto = responseDtos.iterator().next();

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertEquals(responseUtilizador.getId(), id);
        assertEquals(responseUtilizador.getNif(), nif);
        assertEquals(responseUtilizador.getNome(), nome);
        assertEquals(responseUtilizador.getContacto(), contacto);

        assertEquals(responseDto.getPosicaoPreferida(), posicao);

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Rollback(false)
    @DisplayName("Delete Jogador")
    public void testDeleteJogador() {
        int nif = 222222222;

        jogadorHandler.removerJogador(nif);
        JogadorDto responseDto = jogadorHandler.verificarJogador(nif);

        assertNull(responseDto);
    }
}
 */