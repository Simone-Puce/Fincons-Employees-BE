package com.fincons.services;



import com.fincons.models.FileDTO;

import java.io.IOException;
import java.util.List;

public interface FileServiceApi {

    FileDTO getFileById(long id);

    FileDTO createFile(FileDTO fileDto);

    FileDTO updateFile(long id, FileDTO fileDto);

    void deleteFile(long id);

    List<FileDTO> getAllFiles();


    //Encoding a file into a base64 string
    String encodeFile() throws IOException;

    //Decoding a string from a base64 into a string
    String decodeString() throws IOException;
}
