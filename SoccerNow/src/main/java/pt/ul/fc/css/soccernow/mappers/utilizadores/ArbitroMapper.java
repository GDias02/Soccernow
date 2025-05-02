package pt.ul.fc.css.soccernow.mappers.utilizadores;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.UtilizadorDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Arbitro;
import pt.ul.fc.css.soccernow.entities.utilizadores.Certificado;

public class ArbitroMapper {

    public static ArbitroDto arbitroToDto(Arbitro arbitro) {
        ArbitroDto arbitroDto = new ArbitroDto();

        UtilizadorDto utilizador = new UtilizadorDto();
        utilizador.setId(arbitro.getId());
        utilizador.setNif(arbitro.getNif());
        utilizador.setNome(arbitro.getNome());
        utilizador.setContacto(arbitro.getContacto());
        arbitroDto.setUtilizador(utilizador);

        Certificado certificado = arbitro.getCertificado();
        if (certificado != null) {
            CertificadoDto certificadoDto = CertificadoMapper.certificadoToDto(certificado);
            arbitroDto.setCertificado(certificadoDto);
        }

        return arbitroDto;
    }

    public static Arbitro dtoToArbitro(ArbitroDto arbitroDto) {
        Arbitro arbitro = new Arbitro();

        UtilizadorDto utilizador = arbitroDto.getUtilizador();
        arbitro.setId(utilizador.getId());
        arbitro.setNif(utilizador.getNif());
        arbitro.setNome(utilizador.getNome());
        arbitro.setContacto(utilizador.getContacto());

        CertificadoDto certificadoDto = arbitroDto.getCertificado();
        if (certificadoDto != null) {
            Certificado certificado = CertificadoMapper.dtoToCertificado(certificadoDto);
            arbitro.setCertificado(certificado);
        }

        return arbitro;
    }
}
