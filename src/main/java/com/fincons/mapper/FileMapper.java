package com.fincons.mapper;

import com.fincons.entity.File;
import com.fincons.dto.FileDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class FileMapper {

    public File mapFileDtotoFile(FileDTO fileDto) {
        File file = new File();
        file.setName(fileDto.getName());
        file.setExtension(fileDto.getExtension());
        file.setDescription(fileDto.getDescription());
        file.setFile64(fileDto.getFile64());
        file.setEmpId(fileDto.getEmployeeId());
        return file;
    }

    public FileDTO mapFileToFileDto(File file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setName(file.getName());
        fileDTO.setExtension(file.getExtension());
        fileDTO.setDescription(file.getDescription());
        fileDTO.setFile64(file.getFile64());
        fileDTO.setEmployeeId(file.getEmpId());  //without mapping the Employee Object for the empId, it works
        return fileDTO;
    }

    public List<FileDTO> mapFileListToFileDtoList(List<File> fileList) {
        return fileList.stream().map(this::mapFileToFileDto).collect(Collectors.toList());
    }
}