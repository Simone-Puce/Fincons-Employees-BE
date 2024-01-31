package com.fincons.service.employeeService;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.CertificateEmployee;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@Service
public interface CertificateEmployeeService {
    List<CertificateEmployeeDTO> getAllCertificatesEmployees();
    CertificateEmployeeDTO getCertificateEmployeeById(Long id) throws ServiceException;

    CertificateEmployee addCertificateEmployee(CertificateEmployeeDTO certificateEmployeeDTO);

    CertificateEmployee updateCertificateEmployee(Long id, CertificateEmployeeDTO certificateEmployeeDTO);

    void deleteCertificateEmployee(Long id);

    List<CertificateEmployeeDTO> listCertificateEmployeeByPreviousMonth(LocalDate dateFrom, LocalDate dateTo) throws ServiceException;

    void downloadListCertificateEmployeeByPreviousMonth(HttpServletResponse response, LocalDate dateFrom, LocalDate dateTo) throws ServiceException, IOException, URISyntaxException;
}
