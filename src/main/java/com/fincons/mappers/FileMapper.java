package com.fincons.mappers;



import com.fincons.entities.File;
import com.fincons.models.FileDTO;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FileMapper {

    public File mapFileDtotoFile(FileDTO fileDto) {
        return new File(fileDto.getFileId(), fileDto.getFile64(), fileDto.getName(), fileDto.getDescription());
    }

    public FileDTO mapFileToFileDto(File file) {
        return new FileDTO(file.getId(), file.getFile64(), file.getName(), file.getDescription());
    }

    public List<FileDTO> mapFileListToFileDtoList(List<File> fileList) {
        return fileList.stream().map(this::mapFileToFileDto).toList();
    }
}
