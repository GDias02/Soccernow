package pt.ul.fc.css.soccernow.mappers.utilizadores;

import pt.ul.fc.css.soccernow.dto.utilizadores.CertificadoDto;
import pt.ul.fc.css.soccernow.entities.utilizadores.Certificado;

public class CertificadoMapper {
    
    public static CertificadoDto certificadoToDto(Certificado certificado) {
        CertificadoDto certificadoDto = new CertificadoDto();
        certificadoDto.setExistsCertificado(certificado.isExistsCertificado());

        return certificadoDto;
    }

    public static Certificado dtoToCertificado(CertificadoDto certificadoDto) {
        Certificado certificado = new Certificado();
        certificado.setExistsCertificado(certificadoDto.isExistsCertificado());

        return certificado;
    }
}
