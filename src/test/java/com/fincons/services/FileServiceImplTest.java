package com.fincons.services;


import com.fincons.entities.Employee;
import com.fincons.entities.File;
import com.fincons.mappers.FileMapper;
import com.fincons.models.FileDTO;
import com.fincons.repositories.FileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileRepository repository;

    @Mock
    private FileMapper mapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_succesfully_upload_a_file() {

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
        Mockito.when(mapper.mapFileDtotoFile(dto)).thenReturn(file);
        Mockito.when(repository.save(file)).thenReturn(fileUploaded);
        when(mapper.mapFileToFileDto(fileUploaded)).thenReturn(new FileDTO("file64",
                "name",
                "extension",
                "description",
                new Employee())
        );


        //when
        File uploadedFile = fileService.uploadFile(dto);


        //then
        assertEquals(dto.getFile64(), uploadedFile.getFile64());
        assertEquals(dto.getName(), uploadedFile.getName());
        assertEquals(dto.getExtension(), uploadedFile.getExtension());
        assertEquals(dto.getDescription(), uploadedFile.getDescription());
        //assertEquals(dto.getEmployeeId(), uploadedFile.getEmployee());
    }
}