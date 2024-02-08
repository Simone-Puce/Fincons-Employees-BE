package com.fincons.mapper;

import com.fincons.dto.CertificateDTO;
import com.fincons.entity.Certificate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class CertificateMapper {
    public CertificateDTO mapCertificateToCertificateDto(Certificate certificate){
        return new CertificateDTO(
                certificate.getId(),
                certificate.getName(),
                certificate.isActivate(),
                certificate.getEmployees()
        );
    }

    public Certificate mapCertificateDtoToCertificate(CertificateDTO certificateDTO){
        return new Certificate(
                certificateDTO.getId(),
                certificateDTO.getName(),
                certificateDTO.isActivate()
        );
    }

    public List<CertificateDTO> mapCertificateListToCertificateDtoList(List<Certificate> certificateList){
        return certificateList.stream()
                .map(this::mapCertificateToCertificateDto)
                .collect(Collectors.toList());
    }
}
