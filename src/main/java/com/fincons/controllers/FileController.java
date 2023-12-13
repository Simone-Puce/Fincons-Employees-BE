package com.fincons.controllers;

import com.fincons.entities.File;
import com.fincons.repositories.FileRepository;
import com.fincons.services.FileServiceApi;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    @PostMapping("/upload-file")
    public ResponseEntity<File> uploadFile(@RequestBody String filePath) throws IOException {
        return fileServiceApi.uploadFile(filePath);
    }

    @GetMapping("/view-file/{id}")
    public ResponseEntity<File> viewFile (@PathVariable Long id) {
        //TODO

        return null;
    }

    @GetMapping("file-download/{id}")
    public ResponseEntity<File> downloadFile(@PathVariable Long id){
        //TODO
        return null;
    }

}
