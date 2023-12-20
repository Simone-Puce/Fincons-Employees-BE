package com.fincons.services;

import com.fincons.entities.File;
import com.fincons.models.FileDTO;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.util.List;


public interface FileServiceApi {

    FileDTO getFileById(Long id);

    File uploadFile(FileDTO newFile);

    ResponseEntity<byte[]> downloadFile(Long id) throws IOException;

    List<FileDTO> getAllFiles();

}
