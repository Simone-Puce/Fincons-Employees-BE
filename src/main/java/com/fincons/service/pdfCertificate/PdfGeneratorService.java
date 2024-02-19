package com.fincons.service.pdfCertificate;

import com.fincons.entity.CertificateEmployee;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class PdfGeneratorService implements IPdfGeneratorApi {

    @Override
    public byte[] generate(List<CertificateEmployee> certificateEmployeeList) throws DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, stream);
        document.open();

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

        for (CertificateEmployee certificateEmployee: certificateEmployeeList){
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
        return stream.toByteArray();
    }
}
