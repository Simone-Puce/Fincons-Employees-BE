package com.fincons.service.employeeService;

import com.fincons.dto.CertificateDTO;
import com.fincons.entity.Certificate;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CertificateService {

    List<CertificateDTO> getAllCertificates();

    List<CertificateDTO> getAllCertificateActivate();

    CertificateDTO getCertificateById(Long id) throws ServiceException;

    Certificate addCertificate(CertificateDTO certificateDTO);

    Certificate updateCertificate(Long id, CertificateDTO certificateDTO);

    void deleteCertificate(Long id) throws ServiceException;
}
