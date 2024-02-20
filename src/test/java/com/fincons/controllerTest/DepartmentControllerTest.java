package com.fincons.controllerTest;

import com.fincons.controller.DepartmentController;
import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.repository.DepartmentRepository;
import com.fincons.utility.GenericResponse;
import com.fincons.utility.ValidateFields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

import static com.fincons.utilityTest.DepartmentBuilder.getDepartment;
import static com.fincons.utilityTest.DepartmentBuilder.getDepartments;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DepartmentControllerTest {

    @Autowired
    private DepartmentController departmentController;
    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    public void testGetDepartmentByCodeSuccess() {

        Department department = getDepartment();
        String departmentCode = "code1";
        when(departmentRepository.findDepartmentByDepartmentCode(departmentCode)).thenReturn(department);
        ResponseEntity<GenericResponse<DepartmentDTO>> response = departmentController.getDepartmentByCode(departmentCode);

        DepartmentDTO departmentDTO = departmentController.modelMapperDepartment.mapToDTO(department);

        //Assert if the mapper was successful
        assertThat(departmentDTO.getDepartmentCode()).isEqualTo(department.getDepartmentCode());
        assertThat(departmentDTO.getName()).isEqualTo(department.getName());
        assertThat(departmentDTO.getAddress()).isEqualTo(department.getAddress());
        assertThat(departmentDTO.getCity()).isEqualTo(department.getCity());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getBody().getStatus());
    }
    @Test
    public void testGetDepartmentByCodeFailedBadRequest() {

        String departmentCodeEmpty = "";
        ResponseEntity<GenericResponse<DepartmentDTO>> response = departmentController.getDepartmentByCode(departmentCodeEmpty);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());

        String departmentCodeNull = null;
        ResponseEntity<GenericResponse<DepartmentDTO>> response2 = departmentController.getDepartmentByCode(departmentCodeNull);
        Assertions.assertNotNull(response2.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
    }
    @Test
    public void testGetDepartmentByCodeFailedNotFound() {

        String departmentCode = "code1";
        when(departmentRepository.findDepartmentByDepartmentCode(departmentCode)).thenReturn(null);
        ResponseEntity<GenericResponse<DepartmentDTO>> response = departmentController.getDepartmentByCode(departmentCode);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getBody().getStatus());
    }
    @Test
    public void testGetAllDepartmentSuccess() {
        when(departmentRepository.findAll()).thenReturn(getDepartments());
        ResponseEntity<GenericResponse<List<DepartmentDTO>>> response = departmentController.getAllDepartment();

        List<DepartmentDTO> departmentDTOs = response.getBody().getData().stream().toList();

        assertThat(getDepartments().size()).isEqualTo(departmentDTOs.size());

        int iterations = 0;
        for (int i = 0; i < departmentDTOs.size(); i++) {
            assertThat(getDepartments().get(i).getDepartmentCode()).isEqualTo(departmentDTOs.get(i).getDepartmentCode());
            assertThat(getDepartments().get(i).getName()).isEqualTo(departmentDTOs.get(i).getName());
            assertThat(getDepartments().get(i).getCity()).isEqualTo(departmentDTOs.get(i).getCity());
            iterations++;
        }
        System.out.println(iterations + " iterations");
        System.out.println(getDepartments().size() + " departments");
        System.out.println(departmentDTOs.size() + " DTOS");

        assertThat(iterations).isEqualTo(departmentDTOs.size());
        assertThat(iterations).isEqualTo(getDepartments().size());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getBody().getStatus());
    }



















    @Test
    public void testGetDepartmentByCodeBadRequest() {
        ResponseEntity<GenericResponse<DepartmentDTO>> response1 = departmentController.getDepartmentByCode("");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, (response1.getBody()).getStatus());

        ResponseEntity<GenericResponse<DepartmentDTO>> response2 = departmentController.getDepartmentByCode(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, (response2.getBody()).getStatus());
    }
    @Test
    public void testGetDepartmentByCodeNotFound() {
        String departmentCode = "code1";
        when(departmentRepository.findDepartmentByDepartmentCode(departmentCode)).thenReturn(null);
        ResponseEntity<GenericResponse<DepartmentDTO>> response = departmentController.getDepartmentByCode("code1");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, Objects.requireNonNull(response.getBody()).getStatus());
    }
    @Test
    void testValidateSingleField(){
        //TEST for verify field empty
        String departmentCodeEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> {
            ValidateFields.validateSingleField(departmentCodeEmpty);
        });
        //TEST for verify field null
        assertThrows(IllegalArgumentException.class, () -> {
            ValidateFields.validateSingleField(null);
        });
    }

}

