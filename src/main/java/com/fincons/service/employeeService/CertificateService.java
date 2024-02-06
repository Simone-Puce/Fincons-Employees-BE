package com.fincons.service.employeeService;

import com.fincons.dto.CertificateDTO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CertificateService {

    ResponseEntity<Object> getAllCertificates();

    ResponseEntity<Object> getCertificateById(Long id) throws ServiceException;

    ResponseEntity<Object> addCertificate(CertificateDTO certificateDTO);

    ResponseEntity<Object> updateCertificate(Long id, CertificateDTO certificateDTO);

    ResponseEntity<Object> deleteCertificate(Long id) throws ServiceException;
}
