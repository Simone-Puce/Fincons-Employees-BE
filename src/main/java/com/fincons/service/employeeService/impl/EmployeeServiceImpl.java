package com.fincons.service.employeeService.impl;

import com.fincons.dto.EmployeeDTO;
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
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.utility.ValidateSingleField;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmployeeProjectMapper employeeProjectMapper;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Autowired
    private DepartmentServiceImpl departmentServiceImpl;

    @Autowired
    private PositionServiceImpl positionServiceImpl;


    @Override
    public Employee getEmployeeById(String employeeId) {
        ValidateSingleField.validateSingleField(employeeId);
        return validateEmployeeById(employeeId);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        ValidateSingleField.validateSingleField(email);
        return validateEmployeeByEmail(email);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {

        //Condition for not have null attributes
        validateEmployeeFields(employeeDTO);

        List<Employee> employees = employeeRepository.findAll();
        //Condition if there are employee with same firstName && lastName && birthDate
        checkForDuplicateEmployee(employeeDTO, employees);

        Department department;
        department = departmentServiceImpl.validateDepartmentById(employeeDTO.getDepartmentId());
        employeeDTO.setDepartmentId(department.getId().toString());

        Position position;
        position = positionServiceImpl.validatePositionById(employeeDTO.getPositionId());
        employeeDTO.setPositionId(position.getId().toString());


        Employee employee = modelMapperEmployee.mapToEntity(employeeDTO);

        employeeRepository.save(employee);

        return employee;
    }

    @Override
    public Employee updateEmployeeById(String employeeId, EmployeeDTO employeeDTO) {

        ValidateSingleField.validateSingleField(employeeId);
        //Condition for not have null attributes
        validateEmployeeFields(employeeDTO);

        List<Employee> employees = employeeRepository.findAll();

        //Check if the specified ID exists
        Employee employee = validateEmployeeById(employeeId);


        employee.setEmployeeId(employeeId);
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setEmail(employeeDTO.getEmail());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setStartDate(employeeDTO.getStartDate());
        employee.setEndDate(employeeDTO.getEndDate());


        Department department;
        department = departmentServiceImpl.validateDepartmentById(employeeDTO.getDepartmentId());
        employee.setDepartment(department);

        Position position;
        position = positionServiceImpl.validatePositionById(employeeDTO.getPositionId());
        employee.setPosition(position);

        List<Employee> employeesWithoutEmployeeIdChosed = new ArrayList<>();

        for(Employee e : employees){
            if(!Objects.equals(e.getEmployeeId(), employeeId)){
                employeesWithoutEmployeeIdChosed.add(e);
            }
        }
        if(employeesWithoutEmployeeIdChosed.isEmpty()){
            employeeRepository.save(employee);
        }
        else {
            for (Employee s : employeesWithoutEmployeeIdChosed) {
                if (s.getEmail().equals(employee.getEmail())) {
                    throw new IllegalArgumentException("This email: " + employee.getEmail()  +" already exist");
                } else {
                    employeeRepository.save(employee);
                    break;
                }
            }
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(String employeeId) {
        ValidateSingleField.validateSingleField(employeeId);
        Employee employee = validateEmployeeById(employeeId);
        employeeRepository.deleteById(employee.getId());
    }

    @Override
    public List<Project> findAllEmployeeProjects(String employeeId) {

        ValidateSingleField.validateSingleField(employeeId);
        validateEmployeeById(employeeId);
        List<Project> projects = employeeRepository.findProjectByEmployeeId(employeeId);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("The ID " + employeeId + " doesn't work in any project.");
        }
        return projects;
    }

    @Override
    public List<EmployeeProjectDTO> getAllEmployeeProject() {
        return employeeRepository.getAllEmployeeProject();
    }

    @Override
    public EmployeeProjectDTO addEmployeeProject(String employeeId, String projectId) {

        ValidateSingleField.validateSingleField(employeeId);
        ValidateSingleField.validateSingleField(projectId);
        Employee existingEmployee = validateEmployeeById(employeeId);

        Project existingProject = projectServiceImpl.validateProjectById(projectId);

        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();

        for (EmployeeProjectDTO employeeProjectDTO : employeeProject) {
            if (Objects.equals(employeeId,employeeProjectDTO.getEmployeeId()) && (Objects.equals(projectId, employeeProjectDTO.getProjectId()))) {
                throw new DuplicateNameException("The relationship already exists");
            }
        }
        existingEmployee.getProjects().add(existingProject);
        employeeRepository.save(existingEmployee);
        return employeeProjectMapper.mapEmployeeProject(existingEmployee, existingProject);
    }

    @Override
    public EmployeeProjectDTO updateEmployeeProject(String employeeId, String projectId, EmployeeProjectDTO employeeProjectDTO) {

        //Check if the fields are correct
        ValidateSingleField.validateSingleField(employeeId);
        ValidateSingleField.validateSingleField(projectId);
        validateEmployeeProjectFields(employeeProjectDTO);

        //Check if the relationship employeeId+projectId exist
        List<EmployeeProjectDTO> employeesProjectDTOS = validateEmployeeProjectRelationship(employeeId, projectId);

        //Check if the relationship employeeProjectDTO exist
        for(EmployeeProjectDTO employeeProject : employeesProjectDTOS){
            if (Objects.equals(employeeProject.getEmployeeId(), employeeProjectDTO.getEmployeeId()) &&
                    Objects.equals(employeeProject.getProjectId(), employeeProjectDTO.getProjectId())) {
                throw new DuplicateNameException("The relationship with ID employee: "+ employeeProjectDTO.getEmployeeId() + " and ID project: " + employeeProjectDTO.getProjectId() + " already exists.");
            }
        }

        //Delete the relationship
        Employee oldEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        Project oldProject = projectRepository.findProjectByProjectId(projectId);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);

        //Check if exist the Employee and the Project
        Employee newEmployee = validateEmployeeById(employeeProjectDTO.getEmployeeId());
        Project newProject = projectServiceImpl.validateProjectById(employeeProjectDTO.getProjectId());

        //Save the new relationship
        newEmployee.getProjects().add(newProject);
        employeeRepository.save(newEmployee);

        employeeProjectDTO.setLastNameEmployee(newEmployee.getLastName());
        employeeProjectDTO.setNameProject(newProject.getName());
        return employeeProjectDTO;
    }

    @Override
    public void deleteEmployeeProject(String employeeId, String projectId) {

        //Check if the relationship employeeId+projectId exist
        ValidateSingleField.validateSingleField(employeeId);
        ValidateSingleField.validateSingleField(projectId);
        validateEmployeeProjectRelationship(employeeId, projectId);

        //Delete relationship
        Employee oldEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);
        Project oldProject = projectRepository.findProjectByProjectId(projectId);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);
    }




    private void validateEmployeeProjectFields(EmployeeProjectDTO employeeProjectDTO){
        if(employeeProjectDTO.getEmployeeId() == null || employeeProjectDTO.getProjectId() == null){
            throw new IllegalArgumentException("The fields of the employee can't be null or empty.");
        }
    }
    private List<EmployeeProjectDTO> validateEmployeeProjectRelationship(String employeeId, String projectId) {
        //The employeeProject must be exist
        List<EmployeeProjectDTO> employeesProjects = employeeRepository.getAllEmployeeProject();
        for (EmployeeProjectDTO employeeProject : employeesProjects) {
            if (Objects.equals(employeeProject.getEmployeeId(), employeeId) &&
                    Objects.equals(employeeProject.getProjectId(), projectId)) {
                return employeesProjects;
            }
        }
        throw new ResourceNotFoundException("Relationship with ID Employee: "+ employeeId+ " and ID Project: " + projectId + " don't exists.");
    }

    public Employee validateEmployeeById(String employeeId){
        Employee existingEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);

        if (existingEmployee == null){
            throw new ResourceNotFoundException("Employee with ID: " + employeeId + " not found.");
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

    @Override
    public boolean employeeExists(Employee employee) {
        boolean employeeExists = employeeRepository.existsByEmail(employee.getEmail());
        if(employeeExists){
            return true;
        }else{
            return  false;
        }
    }


    public Employee addEmployeeFromFile(Employee employee) {
     return employeeRepository.save(employee);
    }
}
