package com.fincons.service.impl;

import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department findById(long id) {
        return departmentRepository.findById(id);
    }


    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(long idDepartment) {
        return departmentRepository.getDepartmentEmployeesFindByIdDepartment(idDepartment);
    }

    @Override
    public Department update(long id, Department department) {
        Department existingDepartment = departmentRepository.findById(id);
        if (existingDepartment == null) {
            throw new ResourceNotFoundException("Department with ID: " + id + " not found");
        } else {
            existingDepartment.setName(department.getName());
            existingDepartment.setAddress(department.getAddress());
            existingDepartment.setCity(department.getCity());
        }
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteById(long id) {
        departmentRepository.deleteById(id);
    }

}
