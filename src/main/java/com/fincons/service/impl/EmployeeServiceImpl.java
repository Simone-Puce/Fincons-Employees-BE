package com.fincons.service.impl;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.repository.EmployeeRepository;
import com.fincons.repository.ProjectRepository;
import com.fincons.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id);
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
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
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
