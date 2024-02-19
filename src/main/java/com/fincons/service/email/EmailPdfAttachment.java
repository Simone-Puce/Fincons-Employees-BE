package com.fincons.service.email;

import com.fincons.entity.CertificateEmployee;
import com.fincons.entity.Employee;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.repository.CertificateEmployeeRepository;
import com.fincons.repository.EmployeeRepository;
import com.fincons.service.pdfCertificate.IPdfGeneratorApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class EmailPdfAttachment implements IEmailPdfAttachment {

    Logger logger = LoggerFactory.getLogger(EmailPdfAttachment.class);
    public static final Predicate<Employee> emailNotEmpty = employee -> !employee.getEmail().isEmpty();
    public static final Predicate<Employee> emailCorrectFormat = employee -> employee.getEmail().matches("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

    public static final String EMAIL_SUBJECT_PDF = "Certificazioni consegiute";
    public static final String EMAIL_CONTENT_PDF = "Nel PDF in allegato è riportato l'elenco con i dipendenti che hanno conseguito una certificazione il mese precedente";
    public static final String LOGO = "/images/logo.png";

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailContentBuilder emailContentBuilder;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private IPdfGeneratorApi iPdfGeneratorApi;

    @Autowired
    private CertificateEmployeeRepository certificateEmployeeRepository;
    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;

    @Override
    public void sendPdfAttachment() throws IllegalArgumentException, IOException {
        List<CertificateEmployee> certificateEmployeeList = certificateEmployeeRepository
                .listCertificateEmployeeByDateRange(
                        LocalDate.of(2000,10,10),
                        LocalDate.of(2023,12,31)
                );
        byte[] pdf = iPdfGeneratorApi.generate(certificateEmployeeList);
        employeeRepository.findAll()
                .forEach(employee -> {
                    Map<String, Object> emailContent = new HashMap<>();
                    Map<String, Resource> inLineResource = new HashMap<>();
                    Map<String, Resource> attachmentResource = new HashMap<>();
                    emailContent.put("personalizedTextSub", EMAIL_CONTENT_PDF);
                    inLineResource.put("logo", new ClassPathResource(LOGO));
                    attachmentResource.put("Certifications.pdf", new ByteArrayResource(pdf));
                    String htmlContent = emailContentBuilder.buildEmailContent(emailContent, "certifications-pdf-template");
                    emailSender.sendEmail(employee.getEmail(), EMAIL_SUBJECT_PDF, htmlContent, inLineResource, attachmentResource);
                    logger.info("Email sent to {}", employee.getFirstName());
                });
    }



}
