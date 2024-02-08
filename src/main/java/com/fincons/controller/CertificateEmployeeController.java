package com.fincons.controller;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.service.employeeService.CertificateEmployeeService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("${certificateEmployee.uri}")
public class CertificateEmployeeController {
    @Autowired
    private CertificateEmployeeService certificateEmployeeService;
    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;
    @Autowired
    private PdfCertificateEmployee pdfCertificateEmployee;
    @Autowired
    private ICreateRandomCertificateEmployee iCreateRandomCertificateEmployee;

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

    @DeleteMapping("${certificate-employee.delete.uri}/{id}")
    public void deleteCertificateEmployee(@PathVariable Long id){
        certificateEmployeeService.deleteCertificateEmployee(id);
    }



    @GetMapping("${certificate-employee.list-month-previous.uri}")
    public List<CertificateEmployeeDTO> listMonthPrevious(LocalDate dateFrom, LocalDate dateTo){
        return certificateEmployeeService.listCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
    }

    @GetMapping("${certificate-employee.export-to-pdf.uri}")
    public void generatePdfFile(@RequestParam LocalDate dateFrom,@RequestParam LocalDate dateTo) throws DocumentException, IOException {
        List<CertificateEmployeeDTO> listOfCertificateEmployee = certificateEmployeeService.listCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
        pdfCertificateEmployee.generate(listOfCertificateEmployee);
    }

    @GetMapping("${certificate-employee.download-pdf.uri}")
    public File generateFilePdf(LocalDate dateFrom, LocalDate dateTo) throws IOException {
        certificateEmployeeService.downloadListCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
        return null;
    }

    @PostMapping("${certificate-employee.random-certificate-employee.uri}")
    public void generateRandomCertificateEmployee() {
        iCreateRandomCertificateEmployee.CreateCertificateEmployee();
    }


}
