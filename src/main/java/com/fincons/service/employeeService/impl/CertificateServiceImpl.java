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
    public List<CertificateDTO> getAllCertificates() {
        List<Certificate> certificates = certificateRepository.findAll();
        return certificateMapper.mapCertificateListToCertificateDtoList(certificates);
    }

    @Override
    public List<CertificateDTO> getAllCertificateActivate() {
        List<Certificate> certificateActivate = certificateRepository.certificateListTrue();
        return certificateMapper.mapCertificateListToCertificateDtoList(certificateActivate);
    }
    @Override
    public CertificateDTO getCertificateById(Long id) throws ServiceException {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate == null) {
            throw new ServiceException("Certificate not found for this id");
        }
        return certificateMapper.mapCertificateToCertificateDto(findCertificate);
    }

    @Override
    public Certificate addCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = certificateMapper.mapCertificateDtoToCertificate(certificateDTO);
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate updateCertificate(Long id, CertificateDTO certificateDTO) {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate == null){
            throw new ServiceException("Certificate not found for this id");
        }
        Certificate certificate = certificateMapper.mapCertificateDtoToCertificate(certificateDTO);
        return certificateRepository.save(certificate);
    }

    @Override
    public void deleteCertificate(Long id) throws ServiceException {
        Certificate findCertificate = certificateRepository.findById(id).orElse(null);
        if (findCertificate == null){
            throw new ServiceException("Certificate not found for this id");
        }
        certificateRepository.delete(findCertificate);
    }
}
