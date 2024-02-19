package com.fincons.service.employeeService.impl;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.CertificateEmployee;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.repository.CertificateEmployeeRepository;
import com.fincons.service.employeeService.CertificateEmployeeService;
import com.fincons.service.pdfCertificate.IPdfGeneratorApi;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CertificateEmployeeServiceImpl implements CertificateEmployeeService {

    Logger logger = LoggerFactory.getLogger(CertificateEmployeeServiceImpl.class);

    @Autowired
    private CertificateEmployeeRepository certificateEmployeeRepository;

    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;
    @Autowired
    private IPdfGeneratorApi iPdfGeneratorApi;

    @Override
    public List<CertificateEmployeeDTO> getAllCertificatesEmployees() throws RuntimeException{
        List<CertificateEmployee> certificateEmployees = certificateEmployeeRepository.findAll();
        return certificateEmployeeMapper.mapCertificateEmployeeListToCertificateEmployeeDtoList(certificateEmployees);
    }

    @Override
    public CertificateEmployeeDTO getCertificateEmployeeById(Long id) throws ServiceException {
        CertificateEmployee findCertificateEmployee = certificateEmployeeRepository.findById(id).orElse(null);
        if (findCertificateEmployee == null){
            throw new ServiceException("Certificate not found");
        }
        return certificateEmployeeMapper.mapCertificateEmployeeToCertificateEmployeeDto(findCertificateEmployee);
    }
    @Override
    public CertificateEmployee addCertificateEmployee(CertificateEmployeeDTO certificateEmployeeDTO) {
        CertificateEmployee savedCertificateEmployee = certificateEmployeeMapper.mapCertificateEmployeeDtoToCertificateEmployee(certificateEmployeeDTO);
        return certificateEmployeeRepository.save(savedCertificateEmployee);
    }

    @Override
    public CertificateEmployee updateCertificateEmployee(Long id, CertificateEmployeeDTO certificateEmployeeDTO) {
        CertificateEmployee findCertificateEmployee = certificateEmployeeRepository.findById(id).orElse(null);
        if (findCertificateEmployee == null){
            throw new ServiceException("Certificate not found");
        }
        CertificateEmployee certificateEmployee = certificateEmployeeMapper.mapCertificateEmployeeDtoToCertificateEmployee(certificateEmployeeDTO);
        return certificateEmployeeRepository.save(certificateEmployee);
    }
    @Override
    public void deleteCertificateEmployee(Long id) throws RuntimeException{
        CertificateEmployee certificateEmployee = certificateEmployeeRepository.findById(id).orElse(null);
        if (certificateEmployee == null){
            throw new RuntimeException("Not found CertificateEmployee with this id");
        }
        certificateEmployeeRepository.delete(certificateEmployee);
    }

    @Override
    public List<CertificateEmployee> listCertificateEmployeeByPreviousMonth(LocalDate dateFrom, LocalDate dateTo){
        logger.info("{}-------------{}", dateFrom, dateTo);
        List<CertificateEmployee> list = certificateEmployeeRepository.listCertificateEmployeeByDateRange(dateFrom, dateTo);
        logger.info("-------------- {}",  list.size());
        return list;
    }

    @Override
    public byte[] downloadListCertificateEmployeeByPreviousMonth(LocalDate dateFrom, LocalDate dateTo) throws ServiceException, IOException {
        List<CertificateEmployee> list = certificateEmployeeRepository.listCertificateEmployeeByDateRange(dateFrom, dateTo);
        if (list.isEmpty()) {
            throw new ServiceException("CertificationEmployee is empty for previous month");
        }
        return iPdfGeneratorApi.generate(list);
    }

}
