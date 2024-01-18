package com.fincons.service.employeeService.impl;

import com.fincons.dto.CertificateDTO;
import com.fincons.entity.Certificate;
import com.fincons.mapper.CertificateMapper;
import com.fincons.repository.CertificateRepository;
import com.fincons.service.employeeService.CertificateService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CertificateServiceImpl implements CertificateService {

    Logger logger = LoggerFactory.getLogger(CertificateServiceImpl.class);

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private CertificateMapper certificateMapper;

    @Override
    public ResponseEntity<Object> getAllCertificates() {
        List<Certificate> certificates = certificateRepository.findAll();
        List<CertificateDTO> certificateDTOs = certificateMapper.mapCertificateListToCertificateDtoList(certificates);
        if(certificateDTOs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(certificateDTOs, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Object> getCertificateById(Long id) throws ServiceException {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate == null){
            throw new ServiceException("Certificate not found for this id");
        }
        CertificateDTO certificateDTO = certificateMapper.mapCertificateToCertificateDto(findCertificate);
        return new ResponseEntity<>(certificateDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = certificateMapper.mapCertificateDtoToCertificate(certificateDTO);
        certificate = certificateRepository.save(certificate);
        CertificateDTO responseDTO = certificateMapper.mapCertificateToCertificateDto(certificate);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> updateCertificate(Long id, CertificateDTO certificateDTO) {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate == null){
            throw new ServiceException("Certificate not found for this id");
        }
        Certificate certificate = certificateMapper.mapCertificateDtoToCertificate(certificateDTO);
        certificate = certificateRepository.save(certificate);
        CertificateDTO responseDTO = certificateMapper.mapCertificateToCertificateDto(certificate);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteCertificate(Long id) throws ServiceException {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate == null){
            throw new ServiceException("Certificate not found for this id");
        }
        certificateRepository.delete(findCertificate);
        return new ResponseEntity<>("Certificate employee deleted", HttpStatus.OK);
    }
}
