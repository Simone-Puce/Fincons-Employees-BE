package com.fincons.serviceTest;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.impl.DepartmentServiceImpl;
import com.fincons.utilityTest.DepartmentBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.fincons.utilityTest.DepartmentBuilder.getDepartment;
import static com.fincons.utilityTest.DepartmentBuilder.getDepartmentWithoutId;
import static com.fincons.utilityTest.DepartmentBuilder.getDepartments;
import static com.fincons.utilityTest.DepartmentBuilder.getDepartmentsEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentServiceImpl departmentServiceImpl;

    @MockBean
    private DepartmentRepository departmentRepositoryMocked;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testGetDepartmentByCodeSuccess() {
        Department departmentMocked = getDepartment();
        String departmentCode = "code1";
        when(departmentRepositoryMocked.findDepartmentByDepartmentCode(departmentCode)).thenReturn(departmentMocked);
        Department department = departmentServiceImpl.getDepartmentByCode(departmentCode);

        Assertions.assertNotNull(department);
        assertThat(departmentMocked.getId()).isEqualTo(department.getId());
        assertThat(departmentMocked.getDepartmentCode()).isEqualTo(department.getDepartmentCode());
        assertThat(departmentMocked.getName()).isEqualTo(department.getName());
        assertThat(departmentMocked.getAddress()).isEqualTo(department.getAddress());
        assertThat(departmentMocked.getCity()).isEqualTo(department.getCity());
    }
    @Test
    void testGetDepartmentByCodeFailed() {

        String departmentCode = "code1";

        when(departmentRepositoryMocked.findDepartmentByDepartmentCode(departmentCode)).thenReturn(null);
        assertThatThrownBy(() -> departmentServiceImpl.getDepartmentByCode(departmentCode))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(departmentCode);
    }
    @Test
    void testGetAllDepartmentSuccess() {
        when(departmentRepositoryMocked.findAll()).thenReturn(getDepartments());
        List<Department> departments = departmentServiceImpl.getAllDepartment();
        //Assert is not empty
        assertThat(departments.size()).isNotZero();
    }
    @Test
    void testGetAllDepartmentFailed() {
        when(departmentRepositoryMocked.findAll()).thenReturn(getDepartmentsEmpty());
        List<Department> departments = departmentServiceImpl.getAllDepartment();
        assertThat(departments.size()).isZero();
    }
    @Test
    void testCreateDepartmentSuccess(){

        Department department = departmentServiceImpl.createDepartment(getDepartment());

        assertThat(department.getDepartmentCode()).isEqualTo(getDepartment().getDepartmentCode());
        assertThat(department.getName()).isEqualTo(getDepartment().getName());
        assertThat(department.getAddress()).isEqualTo(getDepartment().getAddress());
        assertThat(department.getCity()).isEqualTo(getDepartment().getCity());

    }






















    @Test
    void testValidateDepartmentByCode() {
        String departmentCode = "code1";
        when(departmentRepositoryMocked.findDepartmentByDepartmentCode(departmentCode)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            departmentServiceImpl.getDepartmentByCode(departmentCode);
        });
    }

    @Test
    void testValidateDepartmentFields() {
        //This test checks for all other methods, so you don't need to test it again in the other fields
        //TEST for verify empty fields
        DepartmentDTO departmentInput1 = new DepartmentDTO("", "", "", "");
        assertThrows(IllegalArgumentException.class, () -> {
            departmentServiceImpl.validateDepartmentFields(departmentInput1);
        });
        //TEST for verify null fields
        DepartmentDTO departmentInput2 = new DepartmentDTO(null, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> {
            departmentServiceImpl.validateDepartmentFields(departmentInput2);
        });
    }

    @Test
    void testCheckForDuplicateDepartment() {

        List<Department> departments = DepartmentBuilder.getDepartments();

        DepartmentDTO departmentInputForCode = new DepartmentDTO("code1", "name3", "address3", "city3");
        DepartmentDTO departmentInputForName = new DepartmentDTO("code3", "name1", "address3", "city3");

        doReturn(departments.get(0)).when(departmentRepositoryMocked).findDepartmentByDepartmentCode("code1");
        doReturn(departments.get(1)).when(departmentRepositoryMocked).findDepartmentByName("name1");

        assertThrows(DuplicateException.class, () -> {
            departmentServiceImpl.checkForDuplicateDepartment(departmentInputForCode.getDepartmentCode(), departmentInputForName.getName());
        });
        assertThrows(DuplicateException.class, () -> {
            departmentServiceImpl.checkForDuplicateDepartment(departmentInputForCode.getDepartmentCode(), departmentInputForName.getName());
        });
    }


}
    /*
    @Test
    void testGetDepartmentByIdExist() {

        // Configures the behavior of the mock for repository
        String departmentId = "testing-uuid";
        Department existingDepartment = new Department(1L ,"testing-uuid", "name", "address", "city");
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

        when(departmentController.getDepartmentById(departmentId)).thenReturn(existingDepartment);

        // Finally run the method from service and save response
        ResponseEntity<Object> response = departmentService.getDepartmentById(departmentId);

        // Verify if they have been called 1 time
        verify(departmentRepositoryMocked, times(1)).findById(departmentId);

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

        when(departmentRepositoryMocked.findAll()).thenReturn(departments);

        ResponseEntity<Object> response = departmentService.getAllDepartment();

        verify(departmentRepositoryMocked, times(1)).findAll();

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

        when(departmentRepositoryMocked.findAll()).thenReturn(departments);
        ResponseEntity<Object> response = departmentService.createDepartment(department3);

        verify(departmentRepositoryMocked, times(1)).findAll();
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
        when(departmentRepositoryMocked.findById(departmentId)).thenReturn(Optional.of(department1));

        when(departmentRepositoryMocked.findAll()).thenReturn(departments);
        ResponseEntity<Object> response = departmentService.createDepartment(department3);

    }
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


