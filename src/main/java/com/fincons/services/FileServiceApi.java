package com.fincons.services;

import com.fincons.entities.File;
import com.fincons.models.FileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileServiceApi {

    FileDTO getFileById(Long id);

    File uploadFile(FileDTO newFile);


    List<FileDTO> getAllFiles();


    //Encoding a file into a base64 string
    String encodeFile(String filePath) throws IOException;

    //Decoding a string from a base64 into a string
    String decodeString() throws IOException;

}
