package com.fincons.service.impl;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResponseEntity<EmployeeDTO> findById(long id) {
        Employee employee = employeeRepository.findById(id);
        //Check if employee not found
        if (employee != null) {
            EmployeeDTO employeeDTO = employeeMapper.mapEmployee(employee);
            return ResponseEntity.ok(employeeDTO);
        } else {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
    }
    @Override
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> newListEmployee = new ArrayList<>();
        //Check if the list of employee is empty
        for (Employee employee : employees) {
            if (employee != null) {
                EmployeeDTO employeeDTO = employeeMapper.mapEmployee(employee);
                newListEmployee.add(employeeDTO);
            } else {
                throw new IllegalArgumentException("There aren't Employees");
            }
        }
        return ResponseEntity.ok(newListEmployee);
    }
    @Override
    public ResponseEntity<EmployeeDTO> save(Employee employee) {

        //Condition for not have null attributes
        //In this conditional miss "endDate" because can be null
        if (
                employee.getFirstName() != null && !employee.getFirstName().isEmpty() &&
                        employee.getLastName() != null && !employee.getLastName().isEmpty() &&
                        employee.getGender() != null && !employee.getGender().isEmpty() &&
                        Objects.nonNull(employee.getBirthDate()) &&
                        Objects.nonNull(employee.getStartDate()) &&
                        Objects.nonNull(employee.getDepartment()) &&
                        Objects.nonNull(employee.getRole())) {

            List<Employee> employees = employeeRepository.findAll();
            //Condition if there are employee with same firstName && lastName && birthDate
            for (Employee employee1 : employees){
                String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                if(employee1.getFirstName().equals(employee.getFirstName())&&
                        employee1.getLastName().equals(employee.getLastName())&&
                        employee1.getBirthDate().equals(employee.getBirthDate())
                ){
                    throw new IllegalArgumentException("FirstName, LastName and BirthDate can't be same");
                }
            }

        } else {
            throw new IllegalArgumentException("The fields of employee can't be null or empty");
        }
        EmployeeDTO employeeDTO = employeeMapper.mapEmployee(employee);
        employeeRepository.save(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @Override
    public Employee update(long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee with ID: " + id + " not found");
        } else {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setBirthDate(employee.getBirthDate());
            existingEmployee.setStartDate(employee.getStartDate());
            existingEmployee.setEndDate(employee.getEndDate());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setRole(employee.getRole());
        }
        return employeeRepository.save(existingEmployee);
    }


    @Override
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Project> findAllEmployeeProjects(long id) {
        return employeeRepository.findProjectByEmployeeId(id);
    }

    @Override
    public List<EmployeeProjectDTO> getAllEmployeeProject() {
        return employeeRepository.getAllEmployeeProject();
    }

    @Override
    public Employee addEmployeeProject(long idEmployee, long idProject) {
        Employee employee = employeeRepository.findById(idEmployee);
        Project project = projectRepository.findById(idProject);
        employee.getProjects().add(project);
        return employeeRepository.save(employee);

    }

    @Override
    public Employee deleteEmployeeProject(long idEmployee, long idProject) {
        Employee employee = employeeRepository.findById(idEmployee);
        Project project = projectRepository.findById(idProject);
        employee.getProjects().remove(project);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeProjectDTO updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO) {

        List<EmployeeProjectDTO> employeesProjects = employeeRepository.getAllEmployeeProject();
        for (EmployeeProjectDTO employeeProject : employeesProjects) {
            if (employeeProject.getIdEmployee() == idEmployee && employeeProject.getIdProject() == idProject) {

                Employee oldEmployee = employeeRepository.findById(idEmployee);
                Project oldProject = projectRepository.findById(idProject);
                oldEmployee.getProjects().remove(oldProject);
                Employee deleteEmployee = employeeRepository.save(oldEmployee);


                Employee newEmployee = employeeRepository.findById(employeeProjectDTO.getIdEmployee())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeProjectDTO.getIdEmployee()));
                Project newProject = projectRepository.findById(employeeProjectDTO.getIdProject())
                        .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + employeeProjectDTO.getIdProject()));;
                newEmployee.getProjects().add(newProject);
                Employee savedEmployee = employeeRepository.save(newEmployee);
                break;
            }
            else
            {
                throw new ResourceNotFoundException("Employee or Project not found");
            }
        }
        return mapEmployeeToDTO(employeeProjectDTO);
    }
    // Metodo per la mappatura dell'Employee a EmployeeProjectDTO
    private EmployeeProjectDTO mapEmployeeToDTO(EmployeeProjectDTO targetDTO) {
        EmployeeProjectDTO employeeDTO = new EmployeeProjectDTO(targetDTO.getIdEmployee(), targetDTO.getIdProject());
        return employeeDTO;
    }

}
