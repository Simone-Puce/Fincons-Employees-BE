package com.fincons.controllers;

import com.fincons.entities.File;
import com.fincons.models.FileDTO;
import com.fincons.repositories.FileRepository;
import com.fincons.services.FileServiceApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v2/")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileServiceApi fileServiceApi;

    @GetMapping("/file")
    public List<FileDTO> getAllFiles() {
        return fileServiceApi.getAllFiles();
    }

    @PostMapping(value = "/upload-file", consumes = MediaType.APPLICATION_JSON_VALUE)
    public File uploadFile(@RequestBody FileDTO fileDTO) {
        return fileServiceApi.uploadFile(fileDTO);
    }

    @GetMapping("/view-file/{id}")
    public FileDTO getFileById(@PathVariable Long id){
        return fileServiceApi.getFileById(id);
    }

    @GetMapping("/download-file/{id}")
    public String downloadFile(@PathVariable Long id) throws IOException {
        return fileServiceApi.downloadFile(id);
    }

}
