package com.fincons.utilities;

import com.fincons.entities.File;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DecodingFile {

    public ResponseEntity<byte[]> decodeString(String encodedFile, File file){
        HttpHeaders headers = new HttpHeaders();
        String pdfExtension = "pdf";
        String jpgExtension = "jpg";
        String pngExtension = "png";

        byte[] stringDecoded = Base64.getMimeDecoder().decode(encodedFile);

        if (pdfExtension.equals(file.getExtension())){

            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(stringDecoded.length);
            headers.setContentDispositionFormData("attachment", file.getName() + ".pdf");

        } else if (jpgExtension.equals(file.getExtension())) {

            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(stringDecoded.length);
            headers.setContentDispositionFormData("attachment", file.getName() + ".jpg");

        } else if (pngExtension.equals(file.getExtension())) {

            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(stringDecoded.length);
            headers.setContentDispositionFormData("attachment", file.getName() + ".png");

        }

        return new ResponseEntity<>(stringDecoded, headers, HttpStatus.OK);
    }

}
