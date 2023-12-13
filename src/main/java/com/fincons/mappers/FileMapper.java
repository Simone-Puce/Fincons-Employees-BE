package com.fincons.mappers;

import com.fincons.entities.File;
import com.fincons.models.FileDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class FileMapper {

    public File mapFileDtotoFile(FileDTO fileDto) {
        return new File(fileDto.getFile64(), fileDto.getName(), fileDto.getDescription(), fileDto.getEmployeeId() );
    }

    public FileDTO mapFileToFileDto(File file) {
        return new FileDTO(file.getFile64(), file.getName(), file.getDescription(), file.getEmployee());
    }

    public List<FileDTO> mapFileListToFileDtoList(List<File> fileList) {
        return fileList.stream().map(this::mapFileToFileDto).collect(Collectors.toList());
    }
}
