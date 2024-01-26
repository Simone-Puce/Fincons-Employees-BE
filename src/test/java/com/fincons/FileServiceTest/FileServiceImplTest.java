package com.fincons.FileServiceTest;


import com.fincons.dto.FileDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.File;
import com.fincons.mapper.FileMapper;
import com.fincons.repository.FileRepository;
import com.fincons.service.fileService.FileServiceImpl;
import org.junit.Before;
import org.junit.Test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)


public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Autowired
    private FileRepository repository;

    @Mock
    private FileMapper mapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_upload_a_file() {

        //given
        FileDTO dto = new FileDTO(
                "file64",
                "name",
                "extension",
                "description",
                new Employee()
        );
        File file = new File(
                "file64",
                "name",
                "extension",
                "description",
                new Employee()
        );
        File fileUploaded = new File(
                "file64",
                "name",
                "extension",
                "description",
                new Employee()
        );
        fileUploaded.setId(1L);
//mock the calls
        when(mapper.mapFileDtotoFile(dto)).thenReturn(file);
        when(repository.save(file)).thenReturn(fileUploaded);
        when(mapper.mapFileToFileDto(fileUploaded)).thenReturn(new FileDTO("file64",
                "name",
                "extension",
                "description",
                new Employee())
        );


        //when
        File uploadedFile = fileService.uploadFile(dto);


        //then
        Assertions.assertEquals(dto.getFile64(), uploadedFile.getFile64());
        Assertions.assertEquals(dto.getName(), uploadedFile.getName());
        Assertions.assertEquals(dto.getExtension(), uploadedFile.getExtension());
        Assertions.assertEquals(dto.getDescription(), uploadedFile.getDescription());
    }

    @Test
    public void FileRepository_Get_All_Returns_More_Than_OneFile(){

        //given

        File file = new File("file64", "name", "extension", "description", new Employee());
        File file2 = new File("file64", "name", "extension", "description", new Employee());

        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        fileList.add(file2);

        //when

        when(repository.saveAll(fileList)).thenReturn(List.of(file, file2));
        when(repository.findAll()).thenReturn(List.of(file, file2));

        List<FileDTO> fileDTOList = fileService.getAllFiles();

        //then
       assertThat(fileList).isNotNull();
       assertThat(fileList.size()).isEqualTo(2);
       Assertions.assertEquals(2, fileDTOList);
       

    }

    @Test
    public void FileRepository_GetFileById_Returns_OneFile(){

        //given

        File file = new File("file64", "name", "extension", "description", new Employee());

        //when

        when(repository.save(file)).thenReturn(file);
        when(repository.findById(1L)).thenReturn(Optional.of(file));

        Optional<File> fileFound = repository.findById(file.getId());

        //then
        assertThat(fileFound).isNotNull();
    }

    @Test
    public void FileRepository_DeleteFileById_Returns_FileIsEmpty(){

        //given

        File file = new File("file64", "name", "extension", "description", new Employee());

        //when
        repository.save(file);
        repository.deleteById(file.getId());

        Optional<File> fileDeleted = repository.findById(file.getId());

        //then
        assertThat(fileDeleted).isEmpty();
    }

}