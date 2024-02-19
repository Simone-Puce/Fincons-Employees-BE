package com.fincons.service.pdfCertificate;

import com.fincons.entity.CertificateEmployee;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IPdfGeneratorApi {

    byte[] generate(List<CertificateEmployee> certificateEmployeeList) throws DocumentException, IOException;
}
