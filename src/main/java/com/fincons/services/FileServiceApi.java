package com.fincons.services;

import com.fincons.entities.File;
import com.fincons.models.FileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface FileServiceApi {

    FileDTO getFileById(long id);

    FileDTO createFile(FileDTO fileDto);

    FileDTO updateFile(long id, FileDTO fileDto);

    void deleteFile(long id);

    List<FileDTO> getAllFiles();


    //Encoding a file into a base64 string
    String encodeFile(String filePath) throws IOException;

    //Decoding a string from a base64 into a string
    String decodeString() throws IOException;

  
    ResponseEntity<File> uploadFile(@RequestBody String filePath) throws IOException;
}
