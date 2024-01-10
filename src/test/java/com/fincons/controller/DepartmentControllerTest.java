package com.fincons.controller;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.impl.DepartmentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Da notare l'uso di webEnvironment=RANDOM_PORT per avviare il server con una porta casuale (utile per evitare conflitti negli ambienti di test)
 * e l'iniezione della porta con @LocalServerPort.
 *
 **/

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DepartmentControllerTest {

    private DepartmentServiceImpl departmentService;
    @Mock
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @BeforeEach
    void init() {
        departmentRepository = mock(DepartmentRepository.class);

        // Inizializza il servizio con i mock
        departmentService = new DepartmentServiceImpl(departmentRepository);

    }
    @Test
    void testFindById() {

        // Configures the behavior of the mock for repository
        long departmentId = 1L;
        Department existingDepartment = new Department(departmentId, "name", "via", "citta");
        when(departmentRepository.findById(departmentId)).thenReturn(existingDepartment);

        // Configures the behavior of the mock for the mapper
        DepartmentDTO departmentDTO = new DepartmentDTO(existingDepartment.getName(), existingDepartment.getAddress(), existingDepartment.getCity());
        when(departmentMapper.mapDepartmentWithoutEmployee(existingDepartment)).thenReturn(departmentDTO);

        // Finally run the method from service and save response
        ResponseEntity<Object> response = departmentService.getDepartmentById(departmentId);

        // Verify if they have been called 1 time
        verify(departmentRepository, times(1)).findById(departmentId);
        verify(departmentMapper, times(1)).mapDepartmentWithoutEmployee(existingDepartment);

        // Assert if object response was successfully
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void testFindAll(){
        List<Department> departments = new ArrayList<>(2);
        Department department1= new Department(1L, "name1", "address1", "citta1");
        Department department2= new Department(2L, "name2", "address2", "citta2");
        departments.add(department1);
        departments.add(department2);

        when(departmentRepository.findAll()).thenReturn(departments);

        DepartmentDTO departmentDTO1 = new DepartmentDTO(department1.getName(), department1.getAddress(), department1.getCity());
        DepartmentDTO departmentDTO2 = new DepartmentDTO(department2.getName(), department2.getAddress(), department2.getCity());
        System.out.println(departmentDTO1.getName() + departmentDTO1.getAddress() + departmentDTO1.getCity());
        when(departmentMapper.mapDepartment(department1)).thenReturn(departmentDTO1);
        when(departmentMapper.mapDepartment(department2)).thenReturn(departmentDTO2);

        ResponseEntity<Object> response = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();
        verify(departmentMapper, times(1)).mapDepartment(department1);
        verify(departmentMapper, times(1)).mapDepartment(department2);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }



}