package com.fincons.controller;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.CertificateEmployee;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.service.employeeService.CertificateEmployeeService;
import com.fincons.service.employeeService.ICreateRandomCertificateEmployee;
import com.fincons.service.pdfCertificate.PdfGeneratorService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/company-employee-management")
public class CertificateEmployeeController {
    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;
    @Autowired
    private ICreateRandomCertificateEmployee iCreateRandomCertificateEmployee;
    @Autowired
    private CertificateEmployeeService certificateEmployeeService;

    @GetMapping("${certificate-employee.list}")
    public List<CertificateEmployeeDTO> getAllCertificateEmployee(){
        return certificateEmployeeService.getAllCertificatesEmployees();
    }
    @PostMapping("${certificate-employee.add}")
    public CertificateEmployee addCertificate(@RequestBody CertificateEmployeeDTO certificateEmployeeDTO){
        return certificateEmployeeService.addCertificateEmployee(certificateEmployeeDTO);
    }
    @GetMapping("${certificate-employee.find-by-id}/{id}")
    public CertificateEmployeeDTO getCertificateEmployeeById(@PathVariable Long id) throws ServiceException {
        return certificateEmployeeService.getCertificateEmployeeById(id);
    }
    @PutMapping("${certificate-employee.update}/{id}")
    public CertificateEmployee updateCertificateEmployee(@PathVariable Long id, @RequestBody CertificateEmployeeDTO certificateEmployeeDTO){
        return certificateEmployeeService.updateCertificateEmployee(id, certificateEmployeeDTO);
    }

    @DeleteMapping("${certificate-employee.delete}/{id}")
    public void deleteCertificateEmployee(@PathVariable Long id){
        certificateEmployeeService.deleteCertificateEmployee(id);
    }

    @GetMapping("${certificate-employee.list-month-previous}")
    public List<CertificateEmployee> listMonthPrevious(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo){
        return certificateEmployeeService.listCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
    }

    @GetMapping("${certificate-employee.export-to-pdf}")
    public byte[] generatePdfFile(@RequestParam LocalDate dateFrom,@RequestParam LocalDate dateTo) {
        List<CertificateEmployee> listOfCertificateEmployee = certificateEmployeeService.listCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
        return pdfGeneratorService.generate(listOfCertificateEmployee);
    }

    @GetMapping("${certificate-employee.download-pdf}")
    public byte[] generateFilePdf(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo) throws IOException {
        return certificateEmployeeService.downloadListCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
    }

    @PostMapping("${certificate-employee.random-certificate-employee}")
    public void generateRandomCertificateEmployee() {
        iCreateRandomCertificateEmployee.CreateCertificateEmployee();
    }


}
