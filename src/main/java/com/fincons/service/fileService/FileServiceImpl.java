package com.fincons.service.fileService;

import com.fincons.entity.File;
import com.fincons.exceptions.ResourceNotFoundException;
import com.fincons.mapper.FileMapper;
import com.fincons.dto.FileDTO;
import com.fincons.repository.FileRepository;
import com.fincons.utility.DecodingFile;
import com.fincons.utility.EncodingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;


@Service
public class FileServiceImpl implements FileServiceApi {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private EncodingFile encodingFile;

    @Autowired
    private DecodingFile decodingFile;

    @Override
    public File uploadFile(FileDTO fileDto) {
        File file = fileMapper.mapFileDtotoFile(fileDto);
        return fileRepository.save(file);
    }

    @Override
    public FileDTO viewFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not exist with id: " + id));
        return fileMapper.mapFileToFileDto(file);
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Long id) throws IOException {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not exist with id: " + id));
        return decodingFile.decodeString(fileMapper.mapFileToFileDto(file).getFile64(), file);
    }

    @Override
    public List<FileDTO> getAllFiles() {
        List<File> fileList = fileRepository.findAll();
        return fileMapper.mapFileListToFileDtoList(fileList);
    }

}