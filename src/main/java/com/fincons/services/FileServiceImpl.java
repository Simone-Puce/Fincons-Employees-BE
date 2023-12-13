package com.fincons.services;

import com.fincons.entities.Employee;
import com.fincons.entities.File;
import com.fincons.exceptions.ResourceNotFoundException;
import com.fincons.mappers.FileMapper;
import com.fincons.models.FileDTO;
import com.fincons.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


@Service
public class FileServiceImpl implements FileServiceApi {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;


    @Override
    public FileDTO getFileById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not exist with id: " + id));
        return fileMapper.mapFileToFileDto(file);
    }

    @Override
    public File uploadFile(FileDTO fileDto) {

        File file = fileMapper.mapFileDtotoFile(fileDto);
        return fileRepository.save(file);
    }


    @Override
    public List<FileDTO> getAllFiles() {
        List<File> fileList = fileRepository.findAll();
        return fileMapper.mapFileListToFileDtoList(fileList);
    }


    //Encoding a file into a base64 string
    @Override
    public String encodeFile(String filePath) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        //Take a file from a local folder and encode it
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        //System.out.println(encodedFile);  //print the encoded file
       return (Base64.getMimeEncoder().encodeToString(bytes));
    }


    //Decoding a string from a base64 into a string

    @Override
    public String decodeString() throws IOException {
        Base64.Decoder decoder = Base64.getDecoder(); //decoder for strings
        Base64.Decoder decoder1 = Base64.getMimeDecoder(); //decoder for files
        String stringToDecode = "SGVsbG8gV29ybGQh";
        byte[] stringDecoded = decoder.decode(stringToDecode);
        byte[] decodedBytes = decoder1.decode(encodeFile(stringToDecode));
        return (Arrays.toString(decodedBytes));

    }

}