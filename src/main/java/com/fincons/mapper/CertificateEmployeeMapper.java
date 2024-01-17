package com.fincons.mapper;

import com.fincons.dto.CertificateDTO;
import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.Certificate;
import com.fincons.entity.CertificateEmployee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CertificateEmployeeMapper {
    public CertificateEmployeeDTO mapCertificateEmployeeToCertificateEmployeeDto(CertificateEmployee certificateEmployee){
        return new CertificateEmployeeDTO(
                certificateEmployee.getId(),
                certificateEmployee.getCertificate(),
                certificateEmployee.getEmployee(),
                certificateEmployee.getAchieved()
        );
    }

    public CertificateEmployee mapCertificateEmployeeDtoToCertificateEmployee(CertificateEmployeeDTO certificateEmployeeDTO){
        return new CertificateEmployee(
                certificateEmployeeDTO.getId(),
                certificateEmployeeDTO.getCertificate(),
                certificateEmployeeDTO.getEmployee(),
                certificateEmployeeDTO.getAchieved()
        );
    }

    public List<CertificateEmployeeDTO> mapCertificateEmployeeListToCertificateEmployeeDtoList(List<CertificateEmployee> certificateEmployee){
        return certificateEmployee.stream()
                .map(this::mapCertificateEmployeeToCertificateEmployeeDto)
                .collect(Collectors.toList());
    }
}
