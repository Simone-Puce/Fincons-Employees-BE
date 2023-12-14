package com.fincons.services;

import com.fincons.entities.File;
import com.fincons.models.FileDTO;


import java.io.IOException;
import java.util.List;


public interface FileServiceApi {

    FileDTO getFileById(Long id);

    File uploadFile(FileDTO newFile);

    String downloadFile(Long id) throws IOException;

    List<FileDTO> getAllFiles();

}
