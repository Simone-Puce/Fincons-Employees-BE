package com.fincons.controller;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.service.employeeService.CertificateEmployeeService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company-employee-management")
public class CertificateEmployeeController {
    @Autowired
    private CertificateEmployeeService certificateEmployeeService;
    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;

    @GetMapping("${certificate-employee.list.uri}")
    public ResponseEntity<Object> getAllCertificateEmployee(){
        return certificateEmployeeService.getAllCertificatesEmployees();
    }
    @PostMapping("${certificate-employee.add.uri}")
    public ResponseEntity<Object> addCertificate(@RequestBody CertificateEmployeeDTO certificateEmployeeDTO){
        return certificateEmployeeService.addCertificateEmployee(certificateEmployeeDTO);
    }
    @GetMapping("${certificate-employee.find-by-id.uri}/{id}")
    public ResponseEntity<Object> getCertificateEmployeeById(@PathVariable Long id) throws ServiceException {
        return certificateEmployeeService.getCertificateEmployeeById(id);
    }
    @PutMapping("${certificate-employee.update.uri}/{id}")
    public ResponseEntity<Object> updateCertificateEmployee(@PathVariable Long id, @RequestBody CertificateEmployeeDTO certificateEmployeeDTO){
        return certificateEmployeeService.updateCertificateEmployee(id, certificateEmployeeDTO);
    }

    @DeleteMapping("${certificate-employee.delete.uri}/{id}")
    public ResponseEntity<Object> deleteCertificateEmployee(@PathVariable Long id) throws ServiceException {
        return certificateEmployeeService.deleteCertificateEmployee(id);
    }
}
