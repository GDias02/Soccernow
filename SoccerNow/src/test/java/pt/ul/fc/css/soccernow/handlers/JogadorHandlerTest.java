package pt.ul.fc.css.soccernow.handlers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.soccernow.SoccerNowApplication;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Posicao;

@SpringBootTest(classes = SoccerNowApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@Order(1)
public class JogadorHandlerTest {

    @Autowired
    private JogadorHandler jogadorHandler;
    
    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("Create Jogador")
    @Transactional
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
        assertEquals(nif, responseUtilizador.getNif());
        assertEquals(nome, responseUtilizador.getNome());
        assertEquals(contacto, responseUtilizador.getContacto());

        assertEquals(posicao, responseDto.getPosicaoPreferida());

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Order(2)
    @Rollback(false)
    @DisplayName("Create Jogador with duplicate nif")
    @Transactional
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
    @Order(3)
    @Rollback(false)
    @DisplayName("Create Jogador with invalid nif")
    @Transactional
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
    @Order(4)
    @Rollback(false)
    @DisplayName("Update Jogador nif and posicao")
    @Transactional
    public void testUpdateJogador() {
        Long id = 1L;
        int nif = 222222222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorPostDto jogadorDto = new JogadorPostDto(utilizadorDto, posicao);

        JogadorPostDto responseDto = jogadorHandler.atualizarJogador(jogadorDto);

        assertNotNull(responseDto);

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertEquals(id, responseUtilizador.getId());
        assertEquals(nif, responseUtilizador.getNif());
        assertEquals(nome, responseUtilizador.getNome());
        assertEquals(contacto, responseUtilizador.getContacto());

        assertEquals(posicao, responseDto.getPosicaoPreferida());
    }

    @Test
    @Order(5)
    @Rollback(false)
    @DisplayName("Update Jogador to have invalid nif")
    @Transactional
    public void testUpdateInvalidNifJogador() {
        Long id = 1L;
        int nif = 222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        JogadorPostDto jogadorDto = new JogadorPostDto(utilizadorDto, posicao);

        JogadorPostDto responseDto = jogadorHandler.atualizarJogador(jogadorDto);

        assertNull(responseDto);
    }

    @Test
    @Order(6)
    @Rollback(false)
    @DisplayName("Get Jogador by nif")
    @Transactional
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
        assertEquals(id, responseUtilizador.getId());
        assertEquals(nif, responseUtilizador.getNif());
        assertEquals(nome, responseUtilizador.getNome());
        assertEquals(contacto, responseUtilizador.getContacto());

        assertEquals(posicao, responseDto.getPosicaoPreferida());

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Order(7)
    @Rollback(false)
    @DisplayName("Get Jogador by non-existent nif")
    @Transactional
    public void testGetInvalidJogador() {
        int nif = 111111111;

        JogadorDto responseDto = jogadorHandler.verificarJogador(nif);

        assertNull(responseDto);
    }

    @Test
    @Order(8)
    @Rollback(false)
    @DisplayName("Get all Jogadores")
    @Transactional
    public void testGetAllJogador() {
        Long id = 1L;
        int nif = 222222222;
        String nome = "Ana";
        String contacto = "911111111";
        Posicao posicao = Posicao.FIXO;

        Set<JogadorDto> responseDtos = jogadorHandler.buscarJogadores();

        assertEquals(1, responseDtos.size());

        JogadorDto responseDto = responseDtos.iterator().next();

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertEquals(id, responseUtilizador.getId());
        assertEquals(nif, responseUtilizador.getNif());
        assertEquals(nome, responseUtilizador.getNome());
        assertEquals(contacto, responseUtilizador.getContacto());

        assertEquals(posicao, responseDto.getPosicaoPreferida());

        EstatisticaJogadorDto responseEstatisticas = responseDto.getEstatisticas();
        assertNotNull(responseEstatisticas);
        assertTrue(responseEstatisticas.getGolos().isEmpty());
        assertTrue(responseEstatisticas.getCartoes().isEmpty());
    }

    @Test
    @Order(9)
    @Rollback(false)
    @DisplayName("Delete Jogador")
    @Transactional
    public void testDeleteJogador() {
        int nif = 222222222;

        jogadorHandler.removerJogador(nif);
        JogadorDto responseDto = jogadorHandler.verificarJogador(nif);

        assertNull(responseDto);
    }
}
