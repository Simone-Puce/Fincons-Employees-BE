package com.fincons.service.employeeService.impl;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.DepartmentService;
import com.fincons.utility.ValidateSingleField;
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
    public Department getDepartmentByCode(String departmentCode) {
        ValidateSingleField.validateSingleField(departmentCode);
        return validateDepartmentByCode(departmentCode);
    }

    @Override
    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
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
    public Department updateDepartmentByCode(String departmentCode, DepartmentDTO departmentDTO) {

        ValidateSingleField.validateSingleField(departmentCode);
        //Condition for not have null attributes
        validateDepartmentFields(departmentDTO);

        List<Department> departments = departmentRepository.findAll();

        //Check if the specified ID exists
        Department department = validateDepartmentByCode(departmentCode);

        List<Department> departmentsWithoutDepartmentCodeChosed = new ArrayList<>();

        for (Department d : departments ) {
            if(!Objects.equals(d.getDepartmentCode(), departmentCode)){
                departmentsWithoutDepartmentCodeChosed.add(d);
            }
        }

        //Set the new instance department
        department.setDepartmentCode(departmentDTO.getDepartmentCode());
        department.setName(departmentDTO.getName());
        department.setAddress(departmentDTO.getAddress());
        department.setCity(departmentDTO.getCity());

        if(departmentsWithoutDepartmentCodeChosed.isEmpty()){
            departmentRepository.save(department);
        }
        else {
            for (Department d : departmentsWithoutDepartmentCodeChosed ) {
                if(d.getDepartmentCode().equals(department.getDepartmentCode())){
                    throw new DuplicateException("This code: " + departmentDTO.getDepartmentCode() + " is already taken" );
                } else if (d.getName().equals(department.getName())) {
                    throw new DuplicateException("This name " + departmentDTO.getName() + " is already taken");
                }
            }
            departmentRepository.save(department);
        }
        return department;
    }

    @Override
    public void deleteDepartmentByCode(String departmentCode) {
        ValidateSingleField.validateSingleField(departmentCode);
        Department department = validateDepartmentByCode(departmentCode);
        departmentRepository.deleteById(department.getId());
    }

    @Override
    public List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByCodeDepartment(String departmentCode) {
        ValidateSingleField.validateSingleField(departmentCode);
        Department department = validateDepartmentByCode(departmentCode);

        return departmentRepository.getDepartmentEmployeesFindByIdDepartment(department.getId());
    }

    public  Department validateDepartmentByCode(String departmentCode){
        Department existingDepartment = departmentRepository.findDepartmentByDepartmentCode(departmentCode);

        if(Objects.isNull(existingDepartment)){
            throw new ResourceNotFoundException("Department with code: " + departmentCode + " not found");
        }
        return existingDepartment;
    }

    public void validateDepartmentFields(DepartmentDTO departmentDTO){
        //If one field is true run Exception
        if (Strings.isEmpty(departmentDTO.getDepartmentCode())||
                Strings.isEmpty(departmentDTO.getName())||
                Strings.isEmpty(departmentDTO.getAddress()) ||
                Strings.isEmpty(departmentDTO.getCity())) {
            throw new IllegalArgumentException("The fields of the Department can't be null or empty");
        }
    }
    public void checkForDuplicateDepartment(DepartmentDTO departmentDTO, List<Department> departments){
        for (Department department1 : departments) {
            if (department1.getName().equals(departmentDTO.getName())) {
                throw new DuplicateException("Department with the same name, already exists");
            } else if (department1.getDepartmentCode().equals(departmentDTO.getDepartmentCode())) {
                throw new DuplicateException("Department with the same code, already exists");

            }
        }
    }



}