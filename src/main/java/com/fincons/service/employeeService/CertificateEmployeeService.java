package com.fincons.service.employeeService;

import com.fincons.dto.CertificateEmployeeDTO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CertificateEmployeeService {
    ResponseEntity<Object> getAllCertificatesEmployees();

    ResponseEntity<Object> getCertificateEmployeeById(Long id) throws ServiceException;

    ResponseEntity<Object> addCertificateEmployee(CertificateEmployeeDTO certificateEmployeeDTO);

    ResponseEntity<Object> updateCertificateEmployee(Long id, CertificateEmployeeDTO certificateEmployeeDTO);

    ResponseEntity<Object> deleteCertificateEmployee(Long id) throws ServiceException;
}
