package com.fincons.service.employeeService.impl;

import com.fincons.dto.CertificateDTO;
import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.Certificate;
import com.fincons.entity.CertificateEmployee;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.repository.CertificateEmployeeRepository;
import com.fincons.service.employeeService.CertificateEmployeeService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CertificateEmployeeServiceImpl implements CertificateEmployeeService {

    Logger logger = LoggerFactory.getLogger(CertificateEmployeeServiceImpl.class);

    @Autowired
    private CertificateEmployeeRepository certificateEmployeeRepository;

    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;

    @Override
    public ResponseEntity<Object> getAllCertificatesEmployees() {
        List<CertificateEmployee> certificateEmployees = certificateEmployeeRepository.findAll();
        //List<CertificateEmployeeDTO> certificateEmployeeDTOs = certificateEmployeeMapper.mapCertificateEmployeeListToCertificateEmployeeDtoList(certificateEmployees);
        List<CertificateEmployeeDTO> certificateEmployeeDTOS = new ArrayList<>();
        for (CertificateEmployee ce: certificateEmployees) {
            CertificateEmployeeDTO ceDTO = certificateEmployeeMapper.mapCertificateEmployeeToCertificateEmployeeDto(ce);
            certificateEmployeeDTOS.add(ceDTO);
        }
        return new ResponseEntity<>(certificateEmployeeDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getCertificateEmployeeById(Long id) throws ServiceException {
        CertificateEmployee findCertificateEmployee = certificateEmployeeRepository.findById(id).orElse(null);
        if (findCertificateEmployee == null){
            throw new ServiceException("Certificate not found");
        }
        CertificateEmployeeDTO certificateEmployeeDTO = certificateEmployeeMapper.mapCertificateEmployeeToCertificateEmployeeDto(findCertificateEmployee);
        return new ResponseEntity<>(certificateEmployeeDTO, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Object> addCertificateEmployee(CertificateEmployeeDTO certificateEmployeeDTO) {
        CertificateEmployee savedCertificateEmployee = certificateEmployeeMapper.mapCertificateEmployeeDtoToCertificateEmployee(certificateEmployeeDTO);
        savedCertificateEmployee =certificateEmployeeRepository.save(savedCertificateEmployee);
        CertificateEmployeeDTO saveCertificateEmployeeDTO = certificateEmployeeMapper.mapCertificateEmployeeToCertificateEmployeeDto(savedCertificateEmployee);
        return new ResponseEntity<>(saveCertificateEmployeeDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> updateCertificateEmployee(Long id, CertificateEmployeeDTO certificateEmployeeDTO) {
        CertificateEmployee findCertificateEmployee = certificateEmployeeRepository.findById(id).orElse(null);
        if (findCertificateEmployee == null){
            throw new ServiceException("Certificate not found");
        }
        CertificateEmployee certificateEmployee = certificateEmployeeMapper.mapCertificateEmployeeDtoToCertificateEmployee(certificateEmployeeDTO);
        certificateEmployee = certificateEmployeeRepository.save(certificateEmployee);
        CertificateEmployeeDTO responseDTO = certificateEmployeeMapper.mapCertificateEmployeeToCertificateEmployeeDto(certificateEmployee);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteCertificateEmployee(Long id) throws ServiceException{
        CertificateEmployee certificateEmployee = certificateEmployeeRepository.findById(id).orElse(null);
        if (certificateEmployee == null) {
            throw new ServiceException("Certificate not found");
        }
        certificateEmployeeRepository.delete(certificateEmployee);
        return new ResponseEntity<>("Certificate employee deleted", HttpStatus.OK);
    }


}
