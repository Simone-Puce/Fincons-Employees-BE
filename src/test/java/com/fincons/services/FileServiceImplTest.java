package com.fincons.services;



import com.fincons.service.fileService.FileServiceImpl;
import com.fincons.mapper.FileMapper;
import com.fincons.repository.FileRepository;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileRepository fileRepository;


    @Mock
    private FileMapper fileMapper;



    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

  /*  @Test
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


        Mockito.when(fileMapper.mapFileDtotoFile(dto)).thenReturn(file);
        Mockito.when(fileRepository.save(file)).thenReturn(fileUploaded);
        when(fileMapper.mapFileToFileDto(fileUploaded)).thenReturn(new FileDTO(
                "file64",
                "name",
                "extension",
                "description",
                new Employee(
                        "TestName",
                        "TestLastName",
                        "email@test.com")
        ));


        //when
        File uploadedFile = fileService.uploadFile(dto);


        //then
        assertEquals(dto.getFile64(), uploadedFile.getFile64());
        assertEquals(dto.getName(), uploadedFile.getName());
        assertEquals(dto.getExtension(), uploadedFile.getExtension());
        assertEquals(dto.getDescription(), uploadedFile.getDescription());
        //assertEquals(dto.getEmployeeId(), uploadedFile.getEmpId());
    }


    @Test
    public void viewFile_validFileId() {
        // given
        Employee emp = new Employee("TestName","TestLastName","test@email.com");
        emp.setId(1L);
        File file = new File(
                "file64",
                "name",
                "extension",
                "description",
                emp);
        file.setId(1L);

        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        fileList.add(file);

        FileDTO fileDTO = new FileDTO(
                "file64",
                "name",
                "extension",
                "description",
                emp);
        List<FileDTO> fileList2 = new ArrayList<>();
        fileList2.add(fileDTO);
        fileList2.add(fileDTO);


        // when
        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));
        //when(fileRepository.findById(anyLong())).thenReturn(Optional.of(file));


        // then
        assertEquals(fileDTO, fileService.viewFile(file.getId()));
    }*/
}