package pt.ul.fc.css.soccernow.handlers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
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
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.LocalDto;
import pt.ul.fc.css.soccernow.dto.jogos.MoradaDto;
import pt.ul.fc.css.soccernow.dto.jogos.SelecaoDto;
import pt.ul.fc.css.soccernow.exceptions.jogos.CriarJogoException;

@SpringBootTest(classes = SoccerNowApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@Order(2)
public class JogoHandlerTest {

  private final LocalDto localTeste =
      new LocalDto(
          0L,
          "Estadio Teste",
          "Proprietario Teste",
          999,
          new MoradaDto("Cidade Teste", "Estado Teste", "Pais Teste"));
  private final LocalDateTime dataTeste = LocalDateTime.of(2025, 01, 01, 8, 0, 0);
  private final SelecaoDto s1 = null;

  @Autowired private JogoHandler jogoHandler;
  @Autowired private ArbitroHandler arbitroHandler;
  @Autowired private JogadorHandler jogadorHandler;
  @Autowired private EquipaHandler equipaHandler;

  @Test
  @Order(1)
  @Rollback(true)
  @DisplayName("Create Jogo requer um Local e uma Data")
  @Transactional
  public void testCreateJogo1() {
    JogoDto jogoDto = new JogoDto();
    jogoDto.setLocalDto(localTeste);
    jogoDto.setDiaEHora(dataTeste);

    Exception exception =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage = exception.getMessage();
    assertTrue(!actualMessage.isBlank());

    Exception exception2 =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage2 = exception2.getMessage();
    assertTrue(!actualMessage2.isBlank());

    jogoDto.setDiaEHora(dataTeste);
    Exception exception3 =
        assertThrows(
            CriarJogoException.class,
            () -> {
              jogoHandler.createJogo(jogoDto);
            });
    String actualMessage3 = exception3.getMessage();
    assertTrue(!actualMessage3.isBlank());
  }

  @Test
  @Order(2)
  @Rollback(false)
  @DisplayName("Create Jogo at the same time and place as another.")
  @Transactional
  public void testCreateJogo2() {}

  @Test
  @Order(3)
  @Rollback(false)
  @DisplayName("Create Jogo with attached campeonato")
  @Transactional
  public void testCreateJogoCampeonato() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo so pode ser feito sobre um Jogo que nao esteja não 'TERMINADO'")
  @Transactional
  public void testRegistarResultadoJogo1() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName("Registar Resultado de Jogo gera um Jogo 'TERMINADO'")
  @Transactional
  public void testRegistarResultadoJogo2() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName("Registar Resultado de Jogo requer pelo menos um Arbitro no Jogo")
  @Transactional
  public void testRegistarResultadoJogo3() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName("Registar Resultado de Jogo requer 10 jogadores, 5 em cada selecao")
  @Transactional
  public void testRegistarResultadoJogo4() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo requer que nenhum jogador esteja simultaneamente em ambas as"
          + " selecoes.")
  @Transactional
  public void testRegistarResultadoJogo5() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo requer que nenhum jogador esteja simultaneamente noutro jogo que"
          + " ocorre no mesmo dia.")
  @Transactional
  public void testRegistarResultadoJogo6() {}

  @Test
  @Order(4)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo requer que nenhum arbitro esteja simultaneamente noutro jogo que"
          + " ocorre no mesmo dia.")
  @Transactional
  public void testRegistarResultadoJogo7() {}

  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName(
      "Registar Resultado de Jogo de Campeonato requer que Arbitro Principal tenha Certificado")
  @Transactional
  public void testRegistarJogoCampeonatoInvalido() {}

  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName("Cartao so pode ser registado no mesmo dia e após o inicio do Jogo")
  @Transactional
  public void testRegistarCartaoInvalido() {}

  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName("Golo so pode ser registado no mesmo dia e após o inicio do Jogo")
  @Transactional
  public void testRegistarGoloInvalido() {}

  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName("Equipa vencedora eh a que marcou mais golos.")
  @Transactional
  public void testRegistarResultadoJogo8() {}

  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName("Placar do Jogo representa o numero de golos marcados por cada equipa.")
  @Transactional
  public void testRegistarResultadoJogo9() {}

  /*
  @Test
  @Order(5)
  @Rollback(false)
  @DisplayName("Update Arbitro nif and certificado")
  @Transactional
  public void testUpdateArbitro() {
      Long id = 2L;
      int nif = 222222222;
      String nome = "Ana";
      String contacto = "911111111";
      UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
      CertificadoDto certificadoDto = new CertificadoDto(true);
      ArbitroDto arbitroDto = new ArbitroDto(utilizadorDto, certificadoDto);

      ArbitroDto responseDto = arbitroHandler.atualizarArbitro(arbitroDto);

      assertNotNull(responseDto);

      UtilizadorDto responseUtilizador = responseDto.getUtilizador();
      assertNotNull(responseUtilizador);
      assertEquals(id, responseUtilizador.getId());
      assertEquals(nif, responseUtilizador.getNif());
      assertEquals(nome, responseUtilizador.getNome());
      assertEquals(contacto, responseUtilizador.getContacto());

      CertificadoDto responseCertificado = responseDto.getCertificado();
      assertNotNull(responseCertificado);
      assertTrue(responseCertificado.isExistsCertificado());
  }

  @Test
  @Order(6)
  @Rollback(false)
  @DisplayName("Update Arbitro to have invalid nif")
  @Transactional
  public void testUpdateInvalidNifArbitro() {
      Long id = 2L;
      int nif = 222;
      String nome = "Ana";
      String contacto = "911111111";
      UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
      CertificadoDto certificadoDto = new CertificadoDto(false);
      ArbitroDto arbitroDto = new ArbitroDto(utilizadorDto, certificadoDto);

      ArbitroDto responseDto = arbitroHandler.atualizarArbitro(arbitroDto);

      assertNull(responseDto);
  }

  @Test
  @Order(7)
  @Rollback(false)
  @DisplayName("Get Arbitro by nif")
  @Transactional
  public void testGetArbitro() {
      Long id = 2L;
      int nif = 222222222;
      String nome = "Ana";
      String contacto = "911111111";

      ArbitroDto responseDto = arbitroHandler.verificarArbitro(nif);

      assertNotNull(responseDto);

      UtilizadorDto responseUtilizador = responseDto.getUtilizador();
      assertNotNull(responseUtilizador);
      assertEquals(id, responseUtilizador.getId());
      assertEquals(nif, responseUtilizador.getNif());
      assertEquals(nome, responseUtilizador.getNome());
      assertEquals(contacto, responseUtilizador.getContacto());

      CertificadoDto responseCertificado = responseDto.getCertificado();
      assertNotNull(responseCertificado);
      assertTrue(responseCertificado.isExistsCertificado());
  }

  @Test
  @Order(8)
  @Rollback(false)
  @DisplayName("Get Arbitro by non-existent nif")
  @Transactional
  public void testGetInvalidArbitro() {
      int nif = 111111111;

      ArbitroDto responseDto = arbitroHandler.verificarArbitro(nif);

      assertNull(responseDto);
  }

  @Test
  @Order(9)
  @Rollback(false)
  @DisplayName("Delete Arbitro")
  @Transactional
  public void testDeleteArbitro() {
      int nif = 222222222;

      arbitroHandler.removerArbitro(nif);
      ArbitroDto responseDto = arbitroHandler.verificarArbitro(nif);

      assertNull(responseDto);
  }
      */
}
