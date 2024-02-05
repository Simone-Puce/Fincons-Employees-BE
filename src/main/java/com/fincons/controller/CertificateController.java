package com.fincons.controller;

import com.fincons.dto.CertificateDTO;
import com.fincons.mapper.CertificateMapper;
import com.fincons.service.employeeService.CertificateService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company-employee-management")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateMapper certificateMapper;

    @GetMapping("${certificate.list}")
    public ResponseEntity<Object> getAllCertificates() {
        return certificateService.getAllCertificates();
    }
    @PostMapping(value = "${certificate.add}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCertificate(@RequestBody CertificateDTO certificateDTO){
        return certificateService.addCertificate(certificateDTO);
    }
    @GetMapping("${certificate.find-by-id}/{id}")
    public ResponseEntity<Object> getCertificateById(@PathVariable Long id){
        return certificateService.getCertificateById(id);
    }
    @PutMapping("${certificate.update}/{id}")
    public ResponseEntity<Object> updateCertificate(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO) throws ServiceException{
        return certificateService.updateCertificate(id, certificateDTO);
    }
    @DeleteMapping("${certificate.delete}/{id}")
    public ResponseEntity<Object> deleteCertificate(@PathVariable Long id) {
            return certificateService.deleteCertificate(id);
    }

}
