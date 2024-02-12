package com.fincons.service.employeeService.impl;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Position;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.DuplicateException;
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
    public Employee getEmployeeBySsn(String ssn) {
        ValidateSingleField.validateSingleField(ssn);
        return validateEmployeeBySsn(ssn);
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

        Employee employee = modelMapperEmployee.mapToEntity(employeeDTO);

        Department department = departmentServiceImpl.validateDepartmentByCode(employeeDTO.getDepartmentCode());
        employee.setDepartment(department);

        Position position = positionServiceImpl.validatePositionByCode(employeeDTO.getPositionCode());
        employee.setPosition(position);

        employeeRepository.save(employee);

        return employee;
    }

    @Override
    public Employee updateEmployeeBySsn(String ssn, EmployeeDTO employeeDTO) {

        ValidateSingleField.validateSingleField(ssn);
        //Condition for not have null attributes
        validateEmployeeFields(employeeDTO);

        List<Employee> employees = employeeRepository.findAll();

        //Check if the specified Ssn exists
        Employee employee = validateEmployeeBySsn(ssn);


        List<Employee> employeesWithoutSsnChosed = new ArrayList<>();

        for (Employee e : employees) {
            if (!Objects.equals(e.getSsn(), ssn)) {
                employeesWithoutSsnChosed.add(e);
            }
        }

        employee.setSsn(employeeDTO.getSsn());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setEmail(employeeDTO.getEmail());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setStartDate(employeeDTO.getStartDate());
        employee.setEndDate(employeeDTO.getEndDate());


        Department department;
        department = departmentServiceImpl.validateDepartmentByCode(employeeDTO.getDepartmentCode());
        employee.setDepartment(department);

        Position position;
        position = positionServiceImpl.validatePositionByCode(employeeDTO.getPositionCode());
        employee.setPosition(position);

        if (employeesWithoutSsnChosed.isEmpty()) {
            employeeRepository.save(employee);
        }
        else {
            for (Employee e : employeesWithoutSsnChosed) {
                //Se uno dei due campi è già stato assegnato mi lancia l'eccezione
                if (e.getSsn().equals(employee.getSsn())) {
                    throw new DuplicateException("This SSN " + employeeDTO.getSsn() + " already taken");
                } else if (e.getEmail().equals(employee.getEmail())) {
                    throw new DuplicateException("This email " + employeeDTO.getEmail() + " already taken");
                }
            }
            employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeBySsn(String ssn) {
        ValidateSingleField.validateSingleField(ssn);
        Employee employee = validateEmployeeBySsn(ssn);
        employeeRepository.deleteById(employee.getId());
    }

    @Override
    public List<Project> findAllEmployeeProjects(String ssn) {

        ValidateSingleField.validateSingleField(ssn);
        validateEmployeeBySsn(ssn);
        List<Project> projects = employeeRepository.findProjectsByEmployeeSsn(ssn);
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("The SSN " + ssn + " doesn't work in any project.");
        }
        return projects;
    }

    @Override
    public List<EmployeeProjectDTO> getAllEmployeeProject() {
        return employeeRepository.getAllEmployeeProject();
    }

    @Override
    public EmployeeProjectDTO addEmployeeProject(String ssn, String projectId) {

        ValidateSingleField.validateSingleField(ssn);
        ValidateSingleField.validateSingleField(projectId);
        Employee existingEmployee = validateEmployeeBySsn(ssn);

        Project existingProject = projectServiceImpl.validateProjectById(projectId);

        List<EmployeeProjectDTO> employeeProject = employeeRepository.getAllEmployeeProject();

        for (EmployeeProjectDTO employeeProjectDTO : employeeProject) {
            if (Objects.equals(ssn, employeeProjectDTO.getSsn()) && (Objects.equals(projectId, employeeProjectDTO.getProjectId()))) {
                throw new DuplicateException("The relationship already exists");
            }
        }
        existingEmployee.getProjects().add(existingProject);
        employeeRepository.save(existingEmployee);
        return employeeProjectMapper.mapEmployeeProject(existingEmployee, existingProject);
    }

    @Override
    public EmployeeProjectDTO updateEmployeeProject(String ssn, String projectId, EmployeeProjectDTO employeeProjectDTO) {

        //Check if the fields are correct
        ValidateSingleField.validateSingleField(ssn);
        ValidateSingleField.validateSingleField(projectId);
        validateEmployeeProjectFields(employeeProjectDTO);

        //Check if the relationship ssn+projectId exist
        List<EmployeeProjectDTO> employeesProjectDTOS = validateEmployeeProjectRelationship(ssn, projectId);

        //Check if the relationship employeeProjectDTO exist
        for (EmployeeProjectDTO employeeProject : employeesProjectDTOS) {
            if (Objects.equals(employeeProject.getSsn(), employeeProjectDTO.getSsn()) &&
                    Objects.equals(employeeProject.getProjectId(), employeeProjectDTO.getProjectId())) {
                throw new DuplicateException("The relationship with SSN employee: " + employeeProjectDTO.getSsn() + " and ID project: " + employeeProjectDTO.getProjectId() + " already exists.");
            }
        }

        //Delete the relationship
        Employee oldEmployee = employeeRepository.findEmployeeBySsn(ssn);
        Project oldProject = projectRepository.findProjectByProjectId(projectId);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);

        //Check if exist the Employee and the Project
        Employee newEmployee = validateEmployeeBySsn(employeeProjectDTO.getSsn());
        Project newProject = projectServiceImpl.validateProjectById(employeeProjectDTO.getProjectId());

        //Save the new relationship
        newEmployee.getProjects().add(newProject);
        employeeRepository.save(newEmployee);

        employeeProjectDTO.setLastNameEmployee(newEmployee.getLastName());
        employeeProjectDTO.setNameProject(newProject.getName());
        return employeeProjectDTO;
    }

    @Override
    public void deleteEmployeeProject(String ssn, String projectId) {

        //Check if the relationship ssn+projectId exist
        ValidateSingleField.validateSingleField(ssn);
        ValidateSingleField.validateSingleField(projectId);
        validateEmployeeProjectRelationship(ssn, projectId);

        //Delete relationship
        Employee oldEmployee = employeeRepository.findEmployeeBySsn(ssn);
        Project oldProject = projectRepository.findProjectByProjectId(projectId);
        oldEmployee.getProjects().remove(oldProject);
        employeeRepository.save(oldEmployee);
    }


    private void validateEmployeeProjectFields(EmployeeProjectDTO employeeProjectDTO) {
        if (employeeProjectDTO.getSsn() == null || employeeProjectDTO.getProjectId() == null) {
            throw new IllegalArgumentException("The fields of the employee can't be null or empty.");
        }
    }

    private List<EmployeeProjectDTO> validateEmployeeProjectRelationship(String ssn, String projectId) {
        //The employeeProject must be exist
        List<EmployeeProjectDTO> employeesProjects = employeeRepository.getAllEmployeeProject();
        for (EmployeeProjectDTO employeeProject : employeesProjects) {
            if (Objects.equals(employeeProject.getSsn(), ssn) &&
                    Objects.equals(employeeProject.getProjectId(), projectId)) {
                return employeesProjects;
            }
        }
        throw new ResourceNotFoundException("Relationship with Ssn Employee: " + ssn + " and ID Project: " + projectId + " don't exists.");
    }

    public Employee validateEmployeeBySsn(String ssn) {
        Employee existingEmployee = employeeRepository.findEmployeeBySsn(ssn);

        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee with SSN: " + ssn + " not found.");
        }
        return existingEmployee;
    }

    private Employee validateEmployeeByEmail(String email) {
        Employee existingEmployee = employeeRepository.findByEmail(email);

        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee with Email: " + email + " not found.");
        }
        return existingEmployee;
    }

    private void validateEmployeeFields(EmployeeDTO employeeDTO) {
        //In this conditional miss "endDate" because can be null
        //If one field is true run Exception
        if (Strings.isEmpty(employeeDTO.getFirstName()) ||
                Strings.isEmpty(employeeDTO.getSsn()) ||
                Strings.isEmpty(employeeDTO.getLastName()) ||
                Strings.isEmpty(employeeDTO.getGender()) ||
                Strings.isEmpty(employeeDTO.getEmail()) ||
                Objects.isNull(employeeDTO.getBirthDate()) ||
                Objects.isNull(employeeDTO.getStartDate()) ||
                Objects.isNull(employeeDTO.getDepartmentCode()) ||
                Objects.isNull(employeeDTO.getPositionCode())) {
            throw new IllegalArgumentException("The fields of the employee can't be null or empty.");
        }
    }

    private void checkForDuplicateEmployee(EmployeeDTO employeeDTO, List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getEmail().equals(employeeDTO.getEmail())) {
                throw new DuplicateException("Employee with this email is already taken.");
            } else if (employee.getSsn().equals(employeeDTO.getSsn())) {
                throw new DuplicateException("Employee with this SSN is already taken.");
            }
        } //Manage in feature email
    }

    @Override
    public boolean employeeExists(Employee employee) {
        boolean employeeExists = employeeRepository.existsByEmail(employee.getEmail());
        if (employeeExists) {
            return true;
        } else {
            return false;
        }
    }


    public Employee addEmployeeFromFile(Employee employee) {
        return employeeRepository.save(employee);
    }
}
