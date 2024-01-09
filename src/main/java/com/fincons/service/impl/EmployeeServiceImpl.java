package com.fincons.service.impl;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.mapper.EmployeeProjectMapper;
import com.fincons.mapper.ProjectMapper;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EmployeeProjectMapper employeeProjectMapper;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;


    @Override
    public ResponseEntity<EmployeeDTO> findById(long id) {

        Employee existingEmployee = getEmployeeById(id);
        EmployeeDTO employeeDTO = employeeMapper.mapEmployee(existingEmployee);
        return ResponseEntity.ok(employeeDTO);
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
        validateEmployeeFields(employee);

        List<Employee> employees = employeeRepository.findAll();
        //Condition if there are employee with same firstName && lastName && birthDate
        checkForDuplicateEmployee(employee, employees);


        EmployeeDTO employeeDTO = employeeMapper.mapEmployee(employee);
        employeeRepository.save(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @Override
    public ResponseEntity<EmployeeDTO> update(long id, Employee employee) {

        EmployeeDTO employeeDTO;
        Employee existingEmployee = getEmployeeById(id);

        //Condition for not have null attributes
        validateEmployeeFields(employee);

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setBirthDate(employee.getBirthDate());
        existingEmployee.setStartDate(employee.getStartDate());
        existingEmployee.setEndDate(employee.getEndDate());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setRole(employee.getRole());

        employeeRepository.save(existingEmployee);

        employeeDTO = employeeMapper.mapEmployee(employee);

        return ResponseEntity.ok(employeeDTO);
    }


    @Override
    public ResponseEntity<EmployeeDTO> deleteById(long id) {

        getEmployeeById(id);
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> findAllEmployeeProjects(long id) {

        getEmployeeById(id);
        List<ProjectDTO> newListProjects = new ArrayList<>();

        List<Project> projects = employeeRepository.findProjectByEmployeeId(id);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("The id: " + id + " doesn't work in any project");
        } else {
            for (Project project : projects) {
                ProjectDTO projectDTO = projectMapper.mapProjectWithoutEmployees(project);
                newListProjects.add(projectDTO);
            }
        }
        return ResponseEntity.ok(newListProjects);
    }

    @Override
    public ResponseEntity<List<EmployeeProjectDTO>> getAllEmployeeProject() {
        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();
        if (employeeProject.isEmpty()) {
            throw new IllegalArgumentException("The table is empty");
        }
        return ResponseEntity.ok(employeeProject);
    }

    @Override
    public ResponseEntity<EmployeeProjectDTO> addEmployeeProject(long idEmployee, long idProject) {

        Employee existingEmployee = getEmployeeById(idEmployee);

        Project existingProject = projectServiceImpl.getProjectById(idProject);

        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();

        for (EmployeeProjectDTO employeeProjectDTO : employeeProject) {
            if (idEmployee == employeeProjectDTO.getIdEmployee() && idProject == employeeProjectDTO.getIdProject()) {
                throw new IllegalArgumentException("The relationship already exists");
            }
        }
        existingEmployee.getProjects().add(existingProject);
        employeeRepository.save(existingEmployee);
        EmployeeProjectDTO employeeProjectDTO = employeeProjectMapper.mapEmployeeProject(existingEmployee, existingProject);
        return ResponseEntity.ok(employeeProjectDTO);
    }

    @Override
    public EmployeeProjectDTO updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO) {

        //Check if the fields are correct
        validateEmployeeProjectFields(employeeProjectDTO);

        //Check if the relationship idEmployee+idProject exist
        List <EmployeeProjectDTO> employeesProjectDTOS = validateEmployeeProjectRelationship(idEmployee, idProject);

        //Check if the relationship employeeProjectDTO exist
        for(EmployeeProjectDTO employeeProject : employeesProjectDTOS){
            if (Objects.equals(employeeProject.getIdEmployee(), employeeProjectDTO.getIdEmployee()) &&
                    Objects.equals(employeeProject.getIdProject(), employeeProjectDTO.getIdProject())) {
                throw new IllegalArgumentException("The relationship with ID employee: "+ employeeProjectDTO.getIdEmployee() + " and ID project: " + employeeProjectDTO.getIdProject() + " already exists");
            }
        }

        //Delete the relationship
        Employee oldEmployee = employeeRepository.findById(idEmployee);
        Project oldProject = projectRepository.findById(idProject);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);

        //Check if exist the Employee and the Project
        Employee newEmployee = getEmployeeById(employeeProjectDTO.getIdEmployee());
        Project newProject = projectServiceImpl.getProjectById(employeeProjectDTO.getIdProject());

        //Save the new relationship
        newEmployee.getProjects().add(newProject);
        employeeRepository.save(newEmployee);

        return mapEmployeeToDTO(employeeProjectDTO);
    }

    @Override
    public ResponseEntity<EmployeeDTO> deleteEmployeeProject(long idEmployee, long idProject) {

        //Check if the relationship idEmployee+idProject exist
        validateEmployeeProjectRelationship(idEmployee, idProject);

        //Delete relationship
        Employee oldEmployee = employeeRepository.findById(idEmployee);
        Project oldProject = projectRepository.findById(idProject);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);
        return ResponseEntity.noContent().build();
    }

    // Metodo per la mappatura dell'Employee a EmployeeProjectDTO
    private EmployeeProjectDTO mapEmployeeToDTO(EmployeeProjectDTO targetDTO) {
        return new EmployeeProjectDTO(targetDTO.getIdEmployee(), targetDTO.getIdProject());
    }

    private void validateEmployeeProjectFields(EmployeeProjectDTO employeeProjectDTO){
        if(employeeProjectDTO.getIdEmployee() == null || employeeProjectDTO.getIdProject() == null){
            throw new IllegalArgumentException("The fields of the employee can't be null or empty");
        }
    }

    private List<EmployeeProjectDTO> validateEmployeeProjectRelationship(long idEmployee, long idProject) {
        //The employeeProject must be exist
        List<EmployeeProjectDTO> employeesProjects = employeeRepository.getAllEmployeeProject();
        for (EmployeeProjectDTO employeeProject : employeesProjects) {
            if (Objects.equals(employeeProject.getIdEmployee(), idEmployee) &&
                    Objects.equals(employeeProject.getIdProject(), idProject)) {
                return employeesProjects;
            }
        }
        throw new IllegalArgumentException("Relationship with ID Employee: "+ idEmployee+ " and ID Project: " + idProject + " don't exists");
    }

    private Employee getEmployeeById(long id){
        Employee existingEmployee = employeeRepository.findById(id);

        if (existingEmployee == null){
            throw new ResourceNotFoundException("Employee with ID: " + id + " not found");
        }
        return existingEmployee;
    }

    private void validateEmployeeFields(Employee employee) {
        //In this conditional miss "endDate" because can be null
        //If one field is true run Exception
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty() ||
                employee.getLastName() == null || employee.getLastName().isEmpty() ||
                employee.getGender() == null || employee.getGender().isEmpty() ||
                Objects.isNull(employee.getBirthDate()) ||
                Objects.isNull(employee.getStartDate()) ||
                Objects.isNull(employee.getDepartment()) ||
                Objects.isNull(employee.getRole())) {
            throw new IllegalArgumentException("The fields of the employee can't be null or empty");
        }
    }
    private void checkForDuplicateEmployee(Employee employee, List<Employee> employees) {
        for (Employee employee1 : employees) {
            if (employee1.getFirstName().equals(employee.getFirstName()) &&
                    employee1.getLastName().equals(employee.getLastName()) &&
                    Objects.equals(employee1.getBirthDate(), employee.getBirthDate())) {
                throw new IllegalArgumentException("Firstname, lastname, and birthdate can't be the same");
            }
        }
    }
}
