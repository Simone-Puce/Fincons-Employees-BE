package com.fincons.controller;

import com.fincons.entity.File;
import com.fincons.dto.FileDTO;
import com.fincons.service.fileService.FileServiceApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:81")
@RestController
@RequestMapping("/company-employee-management")
public class FileController {

    @Autowired
    private FileServiceApi fileServiceApi;

    @GetMapping("${file.list.uri}")
    public List<FileDTO> getAllFiles() {
        return fileServiceApi.getAllFiles();
    }

    @PostMapping(value = "${file.upload.uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public File uploadFile(@RequestBody FileDTO fileDTO) {
        return fileServiceApi.uploadFile(fileDTO);
    }

    @GetMapping("${file.view.uri}/{id}")
    public FileDTO viewFile(@PathVariable Long id){
        return fileServiceApi.viewFile(id);
    }

    @GetMapping("${file.download.uri}/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        return fileServiceApi.downloadFile(id);
    }

    @DeleteMapping("${file.delete.uri}/{id}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Long id) {
        return fileServiceApi.deleteFileById(id);
    }

}
