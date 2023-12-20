package com.fincons.services;

import com.fincons.entities.File;
import com.fincons.exceptions.ResourceNotFoundException;
import com.fincons.mappers.FileMapper;
import com.fincons.models.FileDTO;
import com.fincons.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.ClassUtils.isPresent;


@Service
public class FileServiceImpl implements FileServiceApi {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;


    @Override
    public File uploadFile(FileDTO fileDto) {
        File file = fileMapper.mapFileDtotoFile(fileDto);

        return fileRepository.save(file);
    }

    @Override
    public FileDTO getFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not exist with id: " + id));


        return fileMapper.mapFileToFileDto(file);
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Long id) throws IOException {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not exist with id: " + id));
        return decodeString(fileMapper.mapFileToFileDto(file).getFile64(), file);
    }

    @Override
    public List<FileDTO> getAllFiles() {
        List<File> fileList = fileRepository.findAll();
        return fileMapper.mapFileListToFileDtoList(fileList);
    }


    //Encoding a file into a base64 string
    public String encodeFile(String filePath) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
       return (Base64.getMimeEncoder().encodeToString(bytes));
    }


    //Decoding a string from a base64 into a string

    public ResponseEntity<byte[]> decodeString(String encodedFile, File file){
        HttpHeaders headers = new HttpHeaders();
        String pdfExension = "pdf";
        String jpgExtension = "jpg";
        String pngExtension = "png";

        byte[] stringDecoded = Base64.getMimeDecoder().decode(encodedFile);

        if (pdfExension.equals(file.getExtension())){

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