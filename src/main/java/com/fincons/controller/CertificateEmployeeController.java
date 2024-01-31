package com.fincons.controller;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.entity.CertificateEmployee;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.service.employeeService.CertificateEmployeeService;
import com.fincons.service.employeeService.ICreateRandomCertificateEmployee;
import com.fincons.service.pdfCertificate.PdfCertificateEmployee;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company-employee-management")
public class CertificateEmployeeController {
    @Autowired
    private CertificateEmployeeService certificateEmployeeService;
    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;
    @Autowired
    private PdfCertificateEmployee pdfCertificateEmployee;
    @Autowired
    private ICreateRandomCertificateEmployee iCreateRandomCertificateEmployee;

    @GetMapping("${certificate-employee.list.uri}")
    public List<CertificateEmployeeDTO> getAllCertificateEmployee(){
        return certificateEmployeeService.getAllCertificatesEmployees();
    }
    @PostMapping("${certificate-employee.add.uri}")
    public CertificateEmployee addCertificate(@RequestBody CertificateEmployeeDTO certificateEmployeeDTO){
        return certificateEmployeeService.addCertificateEmployee(certificateEmployeeDTO);
    }
    @GetMapping("${certificate-employee.find-by-id.uri}/{id}")
    public CertificateEmployeeDTO getCertificateEmployeeById(@PathVariable Long id) throws ServiceException {
        return certificateEmployeeService.getCertificateEmployeeById(id);
    }
    @PutMapping("${certificate-employee.update.uri}/{id}")
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
    public void generatePdfFile(HttpServletResponse response, @RequestParam LocalDate dateFrom,@RequestParam LocalDate dateTo) throws DocumentException, IOException {
        List<CertificateEmployeeDTO> listOfCertificateEmployee = certificateEmployeeService.listCertificateEmployeeByPreviousMonth(dateFrom, dateTo);
        pdfCertificateEmployee.generate(listOfCertificateEmployee, response);
    }

    @GetMapping("${certificate-employee.download-pdf.uri}")
    public void generateFilePdf(HttpServletResponse response, LocalDate dateFrom, LocalDate dateTo) throws DocumentException, IOException, URISyntaxException {
        certificateEmployeeService.downloadListCertificateEmployeeByPreviousMonth(response, dateFrom, dateTo );
    }

    @PostMapping("${certificate-employee.random-certificate-employee.uri}")
    public void generateRandomCertificateEmployee() {
        iCreateRandomCertificateEmployee.CreateCertificateEmployee();
    }


}
