package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Position;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.DuplicateNameException;
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
    private EmployeeMapper modelMapperEmployee;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EmployeeProjectMapper employeeProjectMapper;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Autowired
    private DepartmentServiceImpl departmentServiceImpl;

    @Autowired
    private PositionServiceImpl positionServiceImpl;


    @Autowired
    public void EmployeeService(EmployeeMapper modelMapperEmployee) {
        this.modelMapperEmployee = modelMapperEmployee;

    }
    @Override
    public ResponseEntity<Object> getEmployeeById(String idEmployee) {

        Employee existingEmployee = validateEmployeeById(idEmployee);
        EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(existingEmployee);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found employee with ID " + idEmployee + ".",
                (HttpStatus.OK),
                employeeDTO);
    }

    @Override
    public ResponseEntity<Object> getEmployeeByEmail(String email) {
        Employee existingEmployee = validateEmployeeByEmail(email);
        EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(existingEmployee);

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
                EmployeeDTO employeeDTO = modelMapperEmployee.mapToDTO(employee);
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
    public ResponseEntity<Object> createEmployee(EmployeeDTO employeeDTO) {

        //Condition for not have null attributes
        validateEmployeeFields(employeeDTO);

        List<Employee> employees = employeeRepository.findAll();
        //Condition if there are employee with same firstName && lastName && birthDate
        checkForDuplicateEmployee(employeeDTO, employees);

        //Save uuid for DTO
        String idDepartmentUuid = employeeDTO.getDepartmentId();
        String idPositionUuid = employeeDTO.getPositionId();

        Department department;
        department = departmentServiceImpl.validateDepartmentById(employeeDTO.getDepartmentId());
        employeeDTO.setDepartmentId(department.getId().toString());

        Position position;
        position = positionServiceImpl.validatePositionById(employeeDTO.getPositionId());
        employeeDTO.setPositionId(position.getId().toString());


        Employee employee = modelMapperEmployee.mapToEntity(employeeDTO);

        employeeRepository.save(employee);

        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setDepartmentId(idDepartmentUuid);
        employeeDTO.setPositionId(idPositionUuid);


        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Employee with ID "+ employeeDTO.getEmployeeId() +" has been successfully updated!",
                (HttpStatus.OK), employeeDTO);
    }

    @Override
    public ResponseEntity<Object> updateEmployeeById(String idEmployee, EmployeeDTO employeeDTO) {

        //Condition for not have null attributes
        validateEmployeeFields(employeeDTO);

        List<Employee> employees = employeeRepository.findAll();

        //Check if the specified ID exists
        Employee existingEmployee = validateEmployeeById(idEmployee);

        //Save uuid for DTO
        String idDepartmentUuid = employeeDTO.getDepartmentId();
        String idPositionUuid = employeeDTO.getPositionId();


        existingEmployee.setEmployeeId(idEmployee);
        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setGender(employeeDTO.getGender());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setBirthDate(employeeDTO.getBirthDate());
        existingEmployee.setStartDate(employeeDTO.getStartDate());
        existingEmployee.setEndDate(employeeDTO.getEndDate());


        Department department;
        department = departmentServiceImpl.validateDepartmentById(employeeDTO.getDepartmentId());
        existingEmployee.setDepartment(department);

        Position position;
        position = positionServiceImpl.validatePositionById(employeeDTO.getPositionId());
        existingEmployee.setPosition(position);

        List<Employee> employeesWithoutEmployeeIdChosed = new ArrayList<>();

        //Lista senza employee scelto
        for(Employee e : employees){
            if(!Objects.equals(e.getEmployeeId(), idEmployee)){
                employeesWithoutEmployeeIdChosed.add(e);
            }
        }
        if(employeesWithoutEmployeeIdChosed.isEmpty()){
            employeeRepository.save(existingEmployee);
            employeeDTO.setEmployeeId(existingEmployee.getEmployeeId());
        }
        else {
            for (Employee s : employeesWithoutEmployeeIdChosed) {
                if (s.getEmail().equals(existingEmployee.getEmail())) {
                    throw new IllegalArgumentException("This email: " + existingEmployee.getEmail()  +" already exist");
                } else {
                    employeeRepository.save(existingEmployee);
                    employeeDTO.setEmployeeId(existingEmployee.getEmployeeId());
                }
            }
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Employee with ID "+ idEmployee +" has been successfully updated!",
                (HttpStatus.OK),
                employeeDTO);
    }


    @Override
    public ResponseEntity<Object> deleteEmployeeById(String idEmployee) {

        Employee employee = validateEmployeeById(idEmployee);
        employeeRepository.deleteById(employee.getId());
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Employee with ID "+ idEmployee +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    @Override
    public ResponseEntity<Object> findAllEmployeeProjects(String idEmployee) {

        validateEmployeeById(idEmployee);
        List<ProjectDTO> newListProjects = new ArrayList<>();

        List<Project> projects = employeeRepository.findProjectByEmployeeId(idEmployee);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("The ID " + idEmployee + " doesn't work in any project.");
        } else {
            for (Project project : projects) {
                ProjectDTO projectDTO = projectMapper.mapToDTO(project);
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
    public ResponseEntity<Object> addEmployeeProject(String idEmployee, String idProject) {

        Employee existingEmployee = validateEmployeeById(idEmployee);

        Project existingProject = projectServiceImpl.validateProjectById(idProject);

        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();

        for (EmployeeProjectDTO employeeProjectDTO : employeeProject) {
            if (Objects.equals(idEmployee,employeeProjectDTO.getEmployeeId()) && (Objects.equals(idProject, employeeProjectDTO.getProjectId()))) {
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
    public ResponseEntity<Object> updateEmployeeProject(String idEmployee, String idProject, EmployeeProjectDTO employeeProjectDTO) {

        //Check if the fields are correct
        validateEmployeeProjectFields(employeeProjectDTO);

        //Check if the relationship idEmployee+idProject exist
        List<EmployeeProjectDTO> employeesProjectDTOS = validateEmployeeProjectRelationship(idEmployee, idProject);

        //Check if the relationship employeeProjectDTO exist
        for(EmployeeProjectDTO employeeProject : employeesProjectDTOS){
            if (Objects.equals(employeeProject.getEmployeeId(), employeeProjectDTO.getEmployeeId()) &&
                    Objects.equals(employeeProject.getProjectId(), employeeProjectDTO.getProjectId())) {
                throw new IllegalArgumentException("The relationship with ID employee: "+ employeeProjectDTO.getEmployeeId() + " and ID project: " + employeeProjectDTO.getProjectId() + " already exists.");
            }
        }

        //Delete the relationship
        Employee oldEmployee = employeeRepository.findEmployeeByEmployeeId(idEmployee);
        Project oldProject = projectRepository.findByProjectId(idProject);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);

        //Check if exist the Employee and the Project
        Employee newEmployee = validateEmployeeById(employeeProjectDTO.getEmployeeId());
        Project newProject = projectServiceImpl.validateProjectById(employeeProjectDTO.getProjectId());

        //Save the new relationship
        newEmployee.getProjects().add(newProject);
        employeeRepository.save(newEmployee);

        employeeProjectDTO.setLastName(newEmployee.getLastName());
        employeeProjectDTO.setNameProject(newProject.getName());

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Relationship updated between employee with ID " + idEmployee + " and project with ID " + idProject + ". " +
                        "Updated details for employee with ID " + employeeProjectDTO.getEmployeeId() + " and project with ID " + employeeProjectDTO.getProjectId() + ".",

                (HttpStatus.OK),
                employeeProjectDTO
        );
    }

    @Override
    public ResponseEntity<Object> deleteEmployeeProject(String idEmployee, String idProject) {

        //Check if the relationship idEmployee+idProject exist
        validateEmployeeProjectRelationship(idEmployee, idProject);

        //Delete relationship
        Employee oldEmployee = employeeRepository.findEmployeeByEmployeeId(idEmployee);
        Project oldProject = projectRepository.findByProjectId(idProject);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Relationship deleted between employee with ID " + idEmployee + " and project with ID " + idProject + ".",
                (HttpStatus.OK),
                null
        );
    }




    private void validateEmployeeProjectFields(EmployeeProjectDTO employeeProjectDTO){
        if(employeeProjectDTO.getEmployeeId() == null || employeeProjectDTO.getProjectId() == null){
            throw new IllegalArgumentException("The fields of the employee can't be null or empty.");
        }
    }

    private List<EmployeeProjectDTO> validateEmployeeProjectRelationship(String idEmployee, String idProject) {
        //The employeeProject must be exist
        List<EmployeeProjectDTO> employeesProjects = employeeRepository.getAllEmployeeProject();
        for (EmployeeProjectDTO employeeProject : employeesProjects) {
            if (Objects.equals(employeeProject.getEmployeeId(), idEmployee) &&
                    Objects.equals(employeeProject.getProjectId(), idProject)) {
                return employeesProjects;
            }
        }
        throw new IllegalArgumentException("Relationship with ID Employee: "+ idEmployee+ " and ID Project: " + idProject + " don't exists.");
    }

    private Employee validateEmployeeById(String idEmployee){
        Employee existingEmployee = employeeRepository.findEmployeeByEmployeeId(idEmployee);

        if (existingEmployee == null){
            throw new ResourceNotFoundException("Employee with ID: " + idEmployee + " not found.");
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

    private void validateEmployeeFields(EmployeeDTO employeeDTO) {
        //In this conditional miss "endDate" because can be null
        //If one field is true run Exception
        if(Strings.isEmpty(employeeDTO.getFirstName()) ||
                Strings.isEmpty(employeeDTO.getLastName()) ||
                Strings.isEmpty(employeeDTO.getGender()) ||
                Strings.isEmpty(employeeDTO.getEmail()) ||
                Objects.isNull(employeeDTO.getBirthDate()) ||
                Objects.isNull(employeeDTO.getStartDate()) ||
                Objects.isNull(employeeDTO.getDepartmentId()) ||
                Objects.isNull(employeeDTO.getPositionId())) {
            throw new IllegalArgumentException("The fields of the employee can't be null or empty.");
        }
    }

    private void checkForDuplicateEmployee(EmployeeDTO employeeDTO, List<Employee> employees) {
        for (Employee employee1 : employees) {
            if (employee1.getEmail().equals(employeeDTO.getEmail()))  {
                throw new DuplicateNameException("Employee with this email is already taken.");
            }
        } //Manage in feature email
    }
}
