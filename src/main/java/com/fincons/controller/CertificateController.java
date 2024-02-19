package com.fincons.controller;

import com.fincons.dto.CertificateDTO;
import com.fincons.entity.Certificate;
import com.fincons.mapper.CertificateMapper;
import com.fincons.service.employeeService.CertificateService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company-employee-management")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateMapper certificateMapper;

    @GetMapping("${certificate.list}")
    public List<CertificateDTO> getAllCertificates() {
        return certificateService.getAllCertificates();
    }
    @GetMapping("${certificate.list.activate}")
    public List<CertificateDTO> getAllCertificateActivate() {
        return certificateService.getAllCertificateActivate();
    }
    @PostMapping(value = "${certificate.add}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Certificate addCertificate(@RequestBody CertificateDTO certificateDTO){
        return certificateService.addCertificate(certificateDTO);
    }
    @GetMapping("${certificate.find-by-id}/{id}")
    public CertificateDTO getCertificateById(@PathVariable Long id){
        return certificateService.getCertificateById(id);
    }
    @PutMapping("${certificate.update}/{id}")
    public Certificate updateCertificate(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO) throws ServiceException{
        return certificateService.updateCertificate(id, certificateDTO);
    }
    @DeleteMapping("${certificate.delete}/{id}")
    public void deleteCertificate(@PathVariable Long id) {
            certificateService.deleteCertificate(id);
    }

}
