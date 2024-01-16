package com.fincons.service.fileService;

import com.fincons.entity.File;
import com.fincons.dto.FileDTO;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.util.List;


public interface FileServiceApi {

    FileDTO viewFile(Long id);

    File uploadFile(FileDTO newFile);

    ResponseEntity<byte[]> downloadFile(Long id) throws IOException;

    List<FileDTO> getAllFiles();

}
