package com.fincons.mapper;


import com.fincons.entity.File;
import com.fincons.dto.FileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelMapper modelMapperFile;

    public FileDTO mapFileToFileDto(File file) {
        return modelMapper.map(file, FileDTO.class);
    }
    public File mapFileDtotoFile(FileDTO fileDto) {
        return modelMapper.map(fileDto, File.class);
    }
    public FileDTO mapFileToFileDtoWithoutFile64(File file) {
        return modelMapperFile.map(file, FileDTO.class);
    }
    public File mapFileDtoToFileWithoutFile64(FileDTO fileDto) {
        return modelMapperFile.map(fileDto, File.class);
    }

}
