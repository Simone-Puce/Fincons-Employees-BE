package com.fincons.service.employeeService.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincons.Handler.ResponseHandler;
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
import com.fincons.service.employeeService.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
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
    public ResponseEntity<Object> getEmployeeById(long id) {

        Employee existingEmployee = validateEmployeeById(id);
        EmployeeDTO employeeDTO = employeeMapper.mapEmployeeToEmployeeDto(existingEmployee);


        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found employee with ID " + id + ".",
                (HttpStatus.OK),
                employeeDTO);
    }

    @Override
    public ResponseEntity<Object> getEmployeeByEmail(String email) {
        Employee existingEmployee = validateEmployeeByEmail(email);
        EmployeeDTO employeeDTO = employeeMapper.mapEmployeeToEmployeeDto(existingEmployee);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found employee with email " + email + ".",
                (HttpStatus.OK),
                employeeDTO);
    }

    @Override
    public ResponseEntity<Object> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> newListEmployee = new ArrayList<>();
        //Check if the list of employee is empty
        for (Employee employee : employees) {
            if (employee != null) {
                EmployeeDTO employeeDTO = employeeMapper.mapEmployeeToEmployeeDto(employee);
                newListEmployee.add(employeeDTO);
            } else {
                throw new IllegalArgumentException("There aren't Employees");
            }
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found " + newListEmployee.size() +
                        (newListEmployee.size() == 1 ? " employee" : " employees") + " in the list.",
                (HttpStatus.OK),
                newListEmployee);
    }

    @Override
    public ResponseEntity<Object> createEmployee(Employee employee) {

        //Condition for not have null attributes
        validateEmployeeFields(employee);

        List<Employee> employees = employeeRepository.findAll();
        //Condition if there are employee with same firstName && lastName && birthDate
        checkForDuplicateEmployee(employee, employees);


        EmployeeDTO employeeDTO = employeeMapper.mapEmployeeToEmployeeDto(employee);
        employeeRepository.save(employee);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Employee with ID "+ employee.getId() +" has been successfully updated!",
                (HttpStatus.OK), employeeDTO);
    }

    @Override
    public ResponseEntity<Object> updateEmployeeById(long id, Employee employee) {

        //Condition for not have null attributes
        validateEmployeeFields(employee);

        EmployeeDTO employeeDTO;


        List<Employee> employees = employeeRepository.findAll();
        //Condition if there are employee with same firstName && lastName && birthDate


        //Check if the specified ID exists
        Employee existingEmployee = validateEmployeeById(id);


        existingEmployee.setId(id);
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setBirthDate(employee.getBirthDate());
        existingEmployee.setStartDate(employee.getStartDate());
        existingEmployee.setEndDate(employee.getEndDate());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setPosition(employee.getPosition());



        List<String> employeesWithoutEmployeeIdChosed = new ArrayList<>();

        for(Employee e : employees){
            if(e.getId() != id ){
                employeesWithoutEmployeeIdChosed.add(e.getEmail());
            }
        }
        if(employeesWithoutEmployeeIdChosed.isEmpty()){
            employeeRepository.save(existingEmployee);
        }
        for (String s : employeesWithoutEmployeeIdChosed) {
            if(s.equals(existingEmployee.getEmail())){
                throw new IllegalArgumentException("Email exist yet");
            }else{
                employeeRepository.save(existingEmployee);
            }
        }
        employeeDTO = employeeMapper.mapEmployeeToEmployeeDto(employee);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Employee with ID "+ id +" has been successfully updated!",
                (HttpStatus.OK),
                employeeDTO);
    }


    @Override
    public ResponseEntity<Object> deleteEmployeeById(long id) {

        getEmployeeById(id);
        employeeRepository.deleteById(id);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Employee with ID "+ id +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    @Override
    public ResponseEntity<Object> findAllEmployeeProjects(long id) {

        getEmployeeById(id);
        List<ProjectDTO> newListProjects = new ArrayList<>();

        List<Project> projects = employeeRepository.findProjectByEmployeeId(id);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("The ID " + id + " doesn't work in any project.");
        } else {
            for (Project project : projects) {
                ProjectDTO projectDTO = projectMapper.mapProjectWithoutEmployees(project);
                newListProjects.add(projectDTO);
            }
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: This Employee works in " + newListProjects.size() +
                        (newListProjects.size() == 1 ? " project." : " projects."),
                (HttpStatus.OK),
                newListProjects);
    }

    @Override
    public ResponseEntity<Object> getAllEmployeeProject() {
        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();
        if (employeeProject.isEmpty()) {
            throw new IllegalArgumentException("The table is empty.");
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found "+ employeeProject.size() + " relationship in the search.",
                (HttpStatus.OK),
                employeeProject);
    }

    @Override
    public ResponseEntity<Object> addEmployeeProject(long idEmployee, long idProject) {

        Employee existingEmployee = validateEmployeeById(idEmployee);

        Project existingProject = projectServiceImpl.validateProjectById(idProject);

        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();

        for (EmployeeProjectDTO employeeProjectDTO : employeeProject) {
            if (idEmployee == employeeProjectDTO.getIdEmployee() && idProject == employeeProjectDTO.getIdProject()) {
                throw new IllegalArgumentException("The relationship already exists");
            }
        }
        existingEmployee.getProjects().add(existingProject);
        employeeRepository.save(existingEmployee);
        EmployeeProjectDTO employeeProjectDTO = employeeProjectMapper.mapEmployeeProject(existingEmployee, existingProject);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Addition of relationship between employee with ID: " + idEmployee + " and project with ID: " + idProject,
                (HttpStatus.OK),
                employeeProjectDTO);
    }

    @Override
    public ResponseEntity<Object> updateEmployeeProject(long idEmployee, long idProject, EmployeeProjectDTO employeeProjectDTO) {

        //Check if the fields are correct
        validateEmployeeProjectFields(employeeProjectDTO);

        //Check if the relationship idEmployee+idProject exist
        List <EmployeeProjectDTO> employeesProjectDTOS = validateEmployeeProjectRelationship(idEmployee, idProject);

        //Check if the relationship employeeProjectDTO exist
        for(EmployeeProjectDTO employeeProject : employeesProjectDTOS){
            if (Objects.equals(employeeProject.getIdEmployee(), employeeProjectDTO.getIdEmployee()) &&
                    Objects.equals(employeeProject.getIdProject(), employeeProjectDTO.getIdProject())) {
                throw new IllegalArgumentException("The relationship with ID employee: "+ employeeProjectDTO.getIdEmployee() + " and ID project: " + employeeProjectDTO.getIdProject() + " already exists.");
            }
        }

        //Delete the relationship
        Employee oldEmployee = employeeRepository.findById(idEmployee);
        Project oldProject = projectRepository.findById(idProject);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);

        //Check if exist the Employee and the Project
        Employee newEmployee = validateEmployeeById(employeeProjectDTO.getIdEmployee());
        Project newProject = projectServiceImpl.validateProjectById(employeeProjectDTO.getIdProject());

        //Save the new relationship
        newEmployee.getProjects().add(newProject);
        employeeRepository.save(newEmployee);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Relationship updated between employee with ID " + idEmployee + " and project with ID " + idProject + ". " +
                        "Updated details for employee with ID " + employeeProjectDTO.getIdEmployee() + " and project with ID " + employeeProjectDTO.getIdProject() + ".",

                (HttpStatus.OK),
                mapEmployeeToDTO(employeeProjectDTO)
        );
    }

    @Override
    public ResponseEntity<Object> deleteEmployeeProject(long idEmployee, long idProject) {

        //Check if the relationship idEmployee+idProject exist
        validateEmployeeProjectRelationship(idEmployee, idProject);

        //Delete relationship
        Employee oldEmployee = employeeRepository.findById(idEmployee);
        Project oldProject = projectRepository.findById(idProject);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Relationship deleted between employee with ID " + idEmployee + " and project with ID " + idProject + ".",
                (HttpStatus.OK),
                null
        );
    }


    private EmployeeProjectDTO mapEmployeeToDTO(EmployeeProjectDTO targetDTO) {
        return new EmployeeProjectDTO(targetDTO.getIdEmployee(), targetDTO.getIdProject());
    }

    private void validateEmployeeProjectFields(EmployeeProjectDTO employeeProjectDTO){
        if(employeeProjectDTO.getIdEmployee() == null || employeeProjectDTO.getIdProject() == null){
            throw new IllegalArgumentException("The fields of the employee can't be null or .");
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
        throw new IllegalArgumentException("Relationship with ID Employee: "+ idEmployee+ " and ID Project: " + idProject + " don't exists.");
    }

    private Employee validateEmployeeById(long id){
        Employee existingEmployee = employeeRepository.findById(id);

        if (existingEmployee == null){
            throw new ResourceNotFoundException("Employee with ID: " + id + " not found.");
        }
        return existingEmployee;
    }
    private Employee validateEmployeeByEmail(String email){
        Employee existingEmployee = employeeRepository.findByEmail(email);

        if (existingEmployee == null){
            throw new ResourceNotFoundException("Employee with Email: " + email + " not found.");
        }
        return existingEmployee;
    }

    private void validateEmployeeFields(Employee employee) {
        //In this conditional miss "endDate" because can be null
        //If one field is true run Exception
        if(Strings.isEmpty(employee.getFirstName()) ||
                Strings.isEmpty(employee.getLastName()) ||
                Strings.isEmpty(employee.getGender()) ||
                Strings.isEmpty(employee.getEmail()) ||
                Objects.isNull(employee.getBirthDate()) ||
                Objects.isNull(employee.getStartDate()) ||
                Objects.isNull(employee.getDepartment()) ||
                Objects.isNull(employee.getPosition())) {
            throw new IllegalArgumentException("The fields of the employee can't be null or empty.");
        }
    }

    private void checkForDuplicateEmployee(Employee employee, List<Employee> employees) {
        for (Employee employee1 : employees) {
            if (employee1.getEmail().equals(employee.getEmail()))  {
                throw new IllegalArgumentException("Employee with this email is already taken.");
            }
        } //Manage in feature email
    }
}
