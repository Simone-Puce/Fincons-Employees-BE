package com.fincons.service.employeeService;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.CertificateEmployee;
import org.hibernate.service.spi.ServiceException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface CertificateEmployeeService {
    List<CertificateEmployeeDTO> getAllCertificatesEmployees();
    CertificateEmployeeDTO getCertificateEmployeeById(Long id) throws ServiceException;

    CertificateEmployee addCertificateEmployee(CertificateEmployeeDTO certificateEmployeeDTO);

    CertificateEmployee updateCertificateEmployee(Long id, CertificateEmployeeDTO certificateEmployeeDTO);

    void deleteCertificateEmployee(Long id);

    List<CertificateEmployee> listCertificateEmployeeByPreviousMonth(LocalDate dateFrom, LocalDate dateTo) throws ServiceException;

    byte[] downloadListCertificateEmployeeByPreviousMonth(LocalDate dateFrom, LocalDate dateTo) throws IOException;
}
