package com.fincons.serviceTest;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Position;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.impl.DepartmentServiceImpl;
import org.apache.logging.log4j.util.Strings;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testGetDepartmentByIdExist() {

        // Configures the behavior of the mock for repository
        long departmentId = 3L;
        Department existingDepartment = new Department(3L, "name", "address", "city");
        Position position = new Position(3L, "Web Developer", 1500.0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse("1942-03-10", formatter);
        LocalDate date2 = LocalDate.parse("1942-03-10", formatter);
        LocalDate date3 = LocalDate.parse("1942-03-10", formatter);

        Employee employee = new Employee(5L, "Carlo", "Verdone", "carlo.verdone@gmail.com" , "Male", date1, date2, date3 , existingDepartment, position);
        List<Employee> newListEmployee = new ArrayList<>();
        newListEmployee.add(employee);
        existingDepartment.setEmployees(newListEmployee);
        //Il test emula un findById e dice cosa vuole che ritorni
        when(departmentRepository.findById(departmentId)).thenReturn(existingDepartment);

        // Finally run the method from service and save response
        ResponseEntity<Object> response = departmentService.getDepartmentById(departmentId);

        // Verify if they have been called 1 time
        verify(departmentRepository, times(1)).findById(departmentId);

        //Verifico che l'ID chiesto dall'utente corrisponda con l'ID dell'oggetto in repository
        Assertions.assertThat(existingDepartment.getId()).isEqualTo(departmentId);

        //Verifico che i rimanenti parametri non siano null e vuoti
        Assertions.assertThat(existingDepartment.getName()).isNotNull().isNotBlank();
        Assertions.assertThat(existingDepartment.getAddress()).isNotNull().isNotBlank();
        Assertions.assertThat(existingDepartment.getCity()).isNotNull().isNotBlank();

        // Assert if object response was successfully
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetDepartmentAll() {
        List<Department> departments = new ArrayList<>(2);
        Department department1 = new Department(1L, "name1", "address1", "city1");
        Department department2 = new Department(2L, "name2", "address2", "city2");
        departments.add(department1);
        departments.add(department2);

        when(departmentRepository.findAll()).thenReturn(departments);

        ResponseEntity<Object> response = departmentService.getAllDepartment();

        verify(departmentRepository, times(1)).findAll();

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testCreateDepartmentCorrectly() {
        List<Department> departments = new ArrayList<>(2);
        Department department1 = new Department(1L, "name1", "address1", "city1");
        Department department2 = new Department(2L, "name2", "address2", "city2");
        Department department3 = new Department(3L, "name3", "address3", "city3");
        departments.add(department1);
        departments.add(department2);

        when(departmentRepository.findAll()).thenReturn(departments);
        ResponseEntity<Object> response = departmentService.createDepartment(department3);

        verify(departmentRepository, times(1)).findAll();
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void testUpdateDepartmentCorrectly() {
        List<Department> departments = new ArrayList<>(2);
        Department department1 = new Department(1L, "name1", "address1", "city1");
        Department department2 = new Department(2L, "name2", "address2", "city2");
        Department department3 = new Department(3L, "name3", "address3", "city3");
        departments.add(department1);
        departments.add(department2);

        Long departmentId = 1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department1));

        when(departmentRepository.findAll()).thenReturn(departments);
        ResponseEntity<Object> response = departmentService.createDepartment(department3);

    }
    @Test
    void testValidateDepartById(){
        long departmentId = 2L;
        when(departmentRepository.findById(departmentId)).thenReturn(null);

        //Gestisco il fatto che genera un eccezione
        assertThrows(ResourceNotFoundException.class, () -> {
            // Finally run the method from service and save response
            departmentService.getDepartmentById(departmentId);
        });
    }
    @Test
    void testCheckForDuplicateDepartment() {
        List<Department> departmentsRep = new ArrayList<>(2);
        Department department1 = new Department(1L, "name1", "address1", "city1");
        Department department2 = new Department(2L, "name2", "address2", "city2");
        Department departmentInput = new Department(3L, "name2", "address3", "city3");
        departmentsRep.add(department1);
        departmentsRep.add(department2);
        assertThrows(IllegalArgumentException.class, () -> {
            departmentService.checkForDuplicateDepartment(departmentInput, departmentsRep);
        });
    }
    @Test
    void testValidateDepartmentFields() {
        //This test checks for all other methods, so you don't need to test it again in the other fields
        Department departmentInput1 = new Department("", "", "");
        assertThrows(IllegalArgumentException.class, () -> {
            departmentService.validateDepartmentFields(departmentInput1);
        });
        Department departmentInput2 = new Department(null, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> {
            departmentService.validateDepartmentFields(departmentInput2);
        });
    }
    /*
    private void generateAndTestCombinations() {
        String[] nameOptions = {null, "", "validName"};
        String[] addressOptions = {null, "", "validAddress"};
        String[] cityOptions = {null, "", "validCity"};

        for (String name : nameOptions) {
            for (String address : addressOptions) {
                for (String city : cityOptions) {
                    //Almeno uno dei campi deve essere null or empty
                    if ((Strings.isEmpty(name)) || (Strings.isEmpty(address)) || Strings.isEmpty(city)) {
                        Department departmentUser = new Department(name, address, city);
                        //System.out.println(departmentUser.getName() + " " + departmentUser.getAddress() + " " + departmentUser.getCity());
                        assertThrows(IllegalArgumentException.class, () -> {
                            departmentService.validateDepartmentFields(departmentUser);
                        });
                    }
                }
            }
        }
    }
     */
}
