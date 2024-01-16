package com.fincons.serviceTest;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.impl.DepartmentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = DepartmentServiceTest.TestConfig.class)
public class DepartmentServiceTest {


    static class TestConfig {
    }
    private DepartmentServiceImpl departmentService;
    @Mock
    private DepartmentRepository departmentRepository;
    @Spy
    private DepartmentMapper departmentMapper;

    @BeforeEach
    void init() {
        departmentRepository = mock(DepartmentRepository.class);

        // Initialize the service with mocks
        departmentService = new DepartmentServiceImpl(departmentRepository, departmentMapper);

        // Real implementation
        departmentMapper = spy(new DepartmentMapper());
    }

    @Test
    void testGetDepartmentById() {

        // Configures the behavior of the mock for repository
        long departmentId = 3L;
        Department existingDepartment = new Department(5L, "name", "via", "citta");
        when(departmentRepository.findById(departmentId)).thenReturn(existingDepartment);

        // Finally run the method from service and save response
        ResponseEntity<Object> response = departmentService.getDepartmentById(departmentId);

        // Verify if they have been called 1 time
        verify(departmentRepository, times(1)).findById(departmentId);

        Assertions.assertThat(existingDepartment.getId()).isNotNull();
        Assertions.assertThat(existingDepartment.getName()).isNotNull();
        Assertions.assertThat(existingDepartment.getAddress()).isNotNull();
        Assertions.assertThat(existingDepartment.getCity()).isNotNull();

        // Assert if object response was successfully
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void testGetDepartmentAll(){
        List<Department> departments = new ArrayList<>(2);
        Department department1= new Department(1L, "name1", "address1", "citta1");
        Department department2= new Department(2L, "name2", "address2", "citta2");
        departments.add(department1);
        departments.add(department2);

        when(departmentRepository.findAll()).thenReturn(departments);

        ResponseEntity<Object> response = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void testCreateDepartment(){
        List<Department> departments = new ArrayList<>(2);
        Department department1= new Department(1L, "name1", "address1", "citta1");
        Department department2= new Department(2L, "name2", "address2", "citta2");
        departments.add(department1);
        departments.add(department2);

        when(departmentRepository.findAll()).thenReturn(departments);

        ResponseEntity<Object> response = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }



}