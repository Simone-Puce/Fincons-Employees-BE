package com.fincons.service.pdfCertificate;

import com.fincons.dto.CertificateEmployeeDTO;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public interface IPdfCertificateEmployee {

    void generate(List<CertificateEmployeeDTO> certificateEmployeeDTOSList, HttpServletResponse response) throws DocumentException, IOException;
}
