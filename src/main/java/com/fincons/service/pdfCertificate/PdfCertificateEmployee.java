package com.fincons.service.pdfCertificate;

import com.fincons.dto.CertificateEmployeeDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Service
public class PdfCertificateEmployee implements IPdfCertificateEmployee{

    @Override
    public void generate(List<CertificateEmployeeDTO> certificateEmployeeDTOSList, HttpServletResponse response) throws DocumentException, IOException {
        Path path = Paths.get("C:\\Users\\carlo.vitto\\Desktop\\Fincons-Employees-BE\\src\\main\\resources\\images\\logo.png");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scaleToFit(70, 70);
        document.add(img);

        PdfDate pdfDate =  new PdfDate();
        pdfDate.getW3CDate();
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(18);
        Paragraph paragraph1 = new Paragraph("List of people who obtained a certification last month", fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph1.setSpacingAfter(20);
        document.add(paragraph1);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);

        for (CertificateEmployeeDTO certificateEmployee: certificateEmployeeDTOSList){
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase("Employee: ", font));
            paragraph.add(new Phrase(certificateEmployee.getEmployee().getFirstName(), font));
            paragraph.add(new Phrase(" ", font));
            paragraph.add(new Phrase(certificateEmployee.getEmployee().getLastName(), font));
            paragraph.add(new Phrase("\n", font));
            paragraph.add(new Phrase("Certificate: ", font));
            paragraph.add(new Phrase(certificateEmployee.getCertificate().getName(), font));
            paragraph.add(new Phrase("\n", font));
            paragraph.add(new Phrase("Achieved: ", font));
            paragraph.add(new Phrase(certificateEmployee.getAchieved().toString(), font));
            paragraph.add(new Phrase("\n", font));
            paragraph.add(new Phrase("\n", font));
            document.add(paragraph);
        }
        document.close();
    }
}
