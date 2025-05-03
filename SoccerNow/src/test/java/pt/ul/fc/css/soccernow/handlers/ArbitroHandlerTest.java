package pt.ul.fc.css.soccernow.handlers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;

@SpringBootTest(classes = SoccerNowApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@Order(2)
public class ArbitroHandlerTest {

    @Autowired
    private ArbitroHandler arbitroHandler;
    
    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("Create Arbitro")
    @Transactional
    public void testCreateArbitro() {
        Long id = 0L;
        int nif = 111111111;
        String nome = "Ana";
        String contacto = "911111111";
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        CertificadoDto certificadoDto = new CertificadoDto(false);
        ArbitroDto arbitroDto = new ArbitroDto(utilizadorDto, certificadoDto);

        ArbitroDto responseDto = arbitroHandler.registarArbitro(arbitroDto);

        assertNotNull(responseDto);

        UtilizadorDto responseUtilizador = responseDto.getUtilizador();
        assertNotNull(responseUtilizador);
        assertNotEquals(responseUtilizador.getId(), 0L);
        assertEquals(nif, responseUtilizador.getNif());
        assertEquals(nome, responseUtilizador.getNome());
        assertEquals(contacto, responseUtilizador.getContacto());

        CertificadoDto responseCertificado = responseDto.getCertificado();
        assertNotNull(responseCertificado);
        assertFalse(responseCertificado.isExistsCertificado());
    }

    @Test
    @Order(2)
    @Rollback(false)
    @DisplayName("Create Arbitro with duplicate nif")
    @Transactional
    public void testCreateDuplicateNifArbitro() {
        Long id = 0L;
        int nif = 111111111;
        String nome = "Ana";
        String contacto = "911111111";
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        CertificadoDto certificadoDto = new CertificadoDto(false);
        ArbitroDto arbitroDto = new ArbitroDto(utilizadorDto, certificadoDto);

        ArbitroDto responseDto = arbitroHandler.registarArbitro(arbitroDto);

        assertNull(responseDto);
    }

    @Test
    @Order(3)
    @Rollback(false)
    @DisplayName("Create Arbitro with invalid nif")
    @Transactional
    public void testCreateInvalidNifArbitro() {
        Long id = 0L;
        int nif = 111;
        String nome = "Ana";
        String contacto = "911111111";
        UtilizadorDto utilizadorDto = new UtilizadorDto(id, nif, nome, contacto);
        CertificadoDto certificadoDto = new CertificadoDto(false);
        ArbitroDto arbitroDto = new ArbitroDto(utilizadorDto, certificadoDto);

        ArbitroDto responseDto = arbitroHandler.registarArbitro(arbitroDto);

        assertNull(responseDto);
    }

    @Test
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
    @Rollback(false)
    @DisplayName("Get Arbitro by non-existent nif")
    @Transactional
    public void testGetInvalidArbitro() {
        int nif = 111111111;

        ArbitroDto responseDto = arbitroHandler.verificarArbitro(nif);

        assertNull(responseDto);
    }

    @Test
    @Order(8)
    @Rollback(false)
    @DisplayName("Delete Arbitro")
    @Transactional
    public void testDeleteArbitro() {
        int nif = 222222222;

        arbitroHandler.removerArbitro(nif);
        ArbitroDto responseDto = arbitroHandler.verificarArbitro(nif);

        assertNull(responseDto);
    }
}
