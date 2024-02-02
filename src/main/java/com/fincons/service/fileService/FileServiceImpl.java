package com.fincons.service.fileService;

import com.fincons.Handler.ResponseHandler;
import com.fincons.entity.Employee;
import com.fincons.entity.File;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.FileMapper;
import com.fincons.dto.FileDTO;
import com.fincons.repository.FileRepository;
import com.fincons.service.employeeService.impl.EmployeeServiceImpl;
import com.fincons.utility.DecodingFile;
import com.fincons.utility.EncodingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

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

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Override
    public File uploadFile(FileDTO fileDTO) {
        Employee employee = employeeServiceImpl.validateEmployeeById(fileDTO.getEmpId());
        fileDTO.setEmpId(employee.getId().toString());
        File file = fileMapper.mapFileDtotoFile(fileDTO);

        //file.setEmp(employee);
        fileRepository.save(file);
        return file;
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

        List<FileDTO> fileDTOs = new ArrayList<>();
        for(File file : fileList){
            FileDTO fileDTO = fileMapper.mapFileToFileDtoWithoutFile64(file);
            fileDTOs.add(fileDTO);
        }
        return fileDTOs;
    }

    @Override
    public ResponseEntity<Object> deleteFileById(Long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File don't exist with id:" + id));
        fileRepository.delete(file);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: File with ID "+ id +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

}