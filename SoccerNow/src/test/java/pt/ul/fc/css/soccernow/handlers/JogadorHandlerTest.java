package pt.ul.fc.css.soccernow.handlers;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarJogadorException;

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

        JogadorDto responseDto = assertDoesNotThrow(() -> jogadorHandler.registarJogador(jogadorPostDto));

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

        assertThrows(RegistarJogadorException.class, () -> jogadorHandler.registarJogador(jogadorPostDto));
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

        assertThrows(RegistarJogadorException.class, () -> jogadorHandler.registarJogador(jogadorPostDto));
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

        JogadorPostDto responseDto = assertDoesNotThrow(() -> jogadorHandler.atualizarJogador(jogadorDto));

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

        assertThrows(AtualizarJogadorException.class, () -> jogadorHandler.atualizarJogador(jogadorDto));
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

        JogadorDto responseDto = assertDoesNotThrow(() -> jogadorHandler.verificarJogador(nif));

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

        assertThrows(NotFoundException.class, () -> jogadorHandler.verificarJogador(nif));
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

        assertDoesNotThrow(() -> jogadorHandler.removerJogador(nif));
        assertThrows(NotFoundException.class, () -> jogadorHandler.verificarJogador(nif));
    }
}
