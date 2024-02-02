package com.fincons.service.employeeService.impl;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.exception.DuplicateNameException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.DepartmentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper modelMapperProject;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper modelMapperProject) {
        this.departmentRepository = departmentRepository;
        this.modelMapperProject = modelMapperProject;
    }

    @Override
    public Department getDepartmentById(String departmentId) {
        return validateDepartmentById(departmentId);
    }

    @Override
    public List<Department> getAllDepartment(){
        List<Department> departments = departmentRepository.findAll();
        if(departments.isEmpty()){
            throw new IllegalArgumentException("There aren't Departments");
        }
        return departments;
    }

    @Override
    public Department createDepartment(DepartmentDTO departmentDTO) {

        //Condition for not have null attributes
        validateDepartmentFields(departmentDTO);

        List<Department> departments = departmentRepository.findAll();
        //Condition if there are departments with name same
        checkForDuplicateDepartment(departmentDTO, departments);

        Department department = modelMapperProject.mapToEntity(departmentDTO);

        departmentRepository.save(department);

        return department;
    }

    @Override
    public Department updateDepartmentById(String departmentId, DepartmentDTO departmentDTO) {
        
        //Condition for not have null attributes
        validateDepartmentFields(departmentDTO);

        List<Department> departments = departmentRepository.findAll();

        //Check if the specified ID exists
        Department department = validateDepartmentById(departmentId);

        department.setDepartmentId(departmentId);
        department.setName(departmentDTO.getName());
        department.setAddress(departmentDTO.getAddress());
        department.setCity(departmentDTO.getCity());

        List<Department> departmentsWithoutDepartmentIdChosed = new ArrayList<>();

        for (Department d : departments ) {
            if(!Objects.equals(d.getDepartmentId(), departmentId)){
                departmentsWithoutDepartmentIdChosed.add(d);
            }
        }
        if(departmentsWithoutDepartmentIdChosed.isEmpty()){
            departmentRepository.save(department);
        }
        else {
            for (Department d : departmentsWithoutDepartmentIdChosed ) {
                if(d.getName().equals(department.getName()) &&
                        d.getAddress().equals(department.getAddress()) &&
                        d.getCity().equals(department.getCity())
                ){
                    throw new DuplicateNameException("The department existing yet");
                }else{
                    departmentRepository.save(department);
                    break;
                }
            }
        }
        return department;
    }

    @Override
    public void deleteDepartmentById(String departmentId) {

        Department department = validateDepartmentById(departmentId);
        departmentRepository.deleteById(department.getId());
    }

    @Override
    public List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(String departmentId) {
        Department department = validateDepartmentById(departmentId);

        List<EmployeeDepartmentDTO> employeeDepartmentDTOList = departmentRepository.getDepartmentEmployeesFindByIdDepartment(department.getId());

        if(employeeDepartmentDTOList.isEmpty()){
            throw new IllegalArgumentException("Department with ID: " + departmentId + " is Empty");
        }
        return employeeDepartmentDTOList;
    }

    public Department validateDepartmentById(String departmentId){
        Department existingDepartment = departmentRepository.findDepartmentByDepartmentId(departmentId);

        if(Objects.isNull(existingDepartment)){
            throw new ResourceNotFoundException("Department with ID: " + departmentId + " not found");
        }
        return existingDepartment;
    }

    public void validateDepartmentFields(DepartmentDTO departmentDTO){
        //If one field is true run Exception
        if (Strings.isEmpty(departmentDTO.getName())||
                Strings.isEmpty(departmentDTO.getAddress()) ||
                Strings.isEmpty(departmentDTO.getCity())) {
            throw new IllegalArgumentException("The fields of the Department can't be null or empty");
        }
    }
    public void checkForDuplicateDepartment(DepartmentDTO departmentDTO, List<Department> departments){
        for (Department department1 : departments) {
            if (department1.getName().equals(departmentDTO.getName())) {
                throw new DuplicateNameException("Department with the same name, already exists");
            }
        }
    }
}
