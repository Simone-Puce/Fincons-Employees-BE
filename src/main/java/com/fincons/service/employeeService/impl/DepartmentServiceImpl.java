package com.fincons.service.employeeService.impl;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.exception.DuplicateException;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.DepartmentService;
import com.fincons.utility.ValidateFields;
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


    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department getDepartmentByCode(String departmentCode) {
        return validateDepartmentByCode(departmentCode);
    }

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public Department createDepartment(Department department) {

        checkForDuplicateDepartment(department.getDepartmentCode(), department.getName());

        departmentRepository.save(department);

        return department;
    }
    @Override
    public Department updateDepartmentByCode(String departmentCode, Department department) {

        List<Department> departments = departmentRepository.findAll();

        //Check if the specified CODE exists
        Department departmentExisting = validateDepartmentByCode(departmentCode);

        List<Department> departmentsWithoutDepartmentCodeChosed = new ArrayList<>();

        for (Department d : departments) {
            if (!Objects.equals(d.getDepartmentCode(), departmentCode)) {
                departmentsWithoutDepartmentCodeChosed.add(d);
            }
        }

        //Set the new instance department
        departmentExisting.setDepartmentCode(department.getDepartmentCode());
        departmentExisting.setName(department.getName());
        departmentExisting.setAddress(department.getAddress());
        departmentExisting.setCity(department.getCity());

        if (departmentsWithoutDepartmentCodeChosed.isEmpty()) {
            departmentRepository.save(departmentExisting);
        } else {
            for (Department d : departmentsWithoutDepartmentCodeChosed) {
                if (d.getDepartmentCode().equals(departmentExisting.getDepartmentCode())) {
                    throw new DuplicateException("code: " + departmentCode, "code: " + department.getDepartmentCode());
                } else if (d.getName().equals(departmentExisting.getName())) {
                    throw new DuplicateException("name: " + departmentExisting.getName(), "name: " + department.getName());
                }
            }
            departmentRepository.save(departmentExisting);
        }
        return departmentExisting;
    }

    @Override
    public void deleteDepartmentByCode(String departmentCode) {
        Department department = validateDepartmentByCode(departmentCode);
        departmentRepository.deleteById(department.getId());
    }

    @Override
    public List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByCodeDepartment(String departmentCode) {
        ValidateFields.validateSingleField(departmentCode);
        Department department = validateDepartmentByCode(departmentCode);

        return departmentRepository.getDepartmentEmployeesFindByIdDepartment(department.getId());
    }

    public Department validateDepartmentByCode(String departmentCode) {
        Department existingDepartment = departmentRepository.findDepartmentByDepartmentCode(departmentCode);

        if (Objects.isNull(existingDepartment)) {
            throw new ResourceNotFoundException("Error: Department with code " + departmentCode + " not found");
        }
        return existingDepartment;
    }

    public void validateDepartmentFields(DepartmentDTO departmentDTO) {
        //If one field is true run Exception
        if (Strings.isEmpty(departmentDTO.getDepartmentCode()) ||
                Strings.isEmpty(departmentDTO.getName()) ||
                Strings.isEmpty(departmentDTO.getAddress()) ||
                Strings.isEmpty(departmentDTO.getCity())) {
            throw new IllegalArgumentException("Error: The fields of the Department can't be null or empty");
        }
    }

    public void checkForDuplicateDepartment(String departmentCode, String departmentName) {

        Department departmentByCode = departmentRepository.findDepartmentByDepartmentCode(departmentCode);
        Department departmentByName = departmentRepository.findDepartmentByName(departmentName);
        if (!Objects.isNull(departmentByCode)) {
            throw new DuplicateException("code: " + departmentCode, "code: " + departmentByCode.getDepartmentCode());
        }
        if (!Objects.isNull(departmentByName)) {
            throw new DuplicateException("name: " + departmentName, "name: "+ departmentByName.getName());
        }
    }
}