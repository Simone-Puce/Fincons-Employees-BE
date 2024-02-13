package com.fincons.mapper;


import com.fincons.entity.File;
import com.fincons.dto.FileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    @Autowired
    private ModelMapper modelMapperStandard;

    @Autowired
    private ModelMapper modelMapperSkipFile64;

    public FileDTO mapFileToFileDto(File file) {
        return modelMapperStandard.map(file, FileDTO.class);
    }
    public File mapFileDtotoFile(FileDTO fileDto) {
        return modelMapperStandard.map(fileDto, File.class);
    }
    public FileDTO mapFileToFileDtoWithoutFile64(File file) {
        return modelMapperSkipFile64.map(file, FileDTO.class);
    }
    public File mapFileDtoToFileWithoutFile64(FileDTO fileDto) {
        return modelMapperSkipFile64.map(fileDto, File.class);
    }

}
