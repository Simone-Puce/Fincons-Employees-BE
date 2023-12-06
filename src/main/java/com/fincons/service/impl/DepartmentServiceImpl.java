package com.fincons.service.impl;

import com.fincons.entity.Department;
import com.fincons.entity.dto.DepartmentEmployeesDTO;
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
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<DepartmentEmployeesDTO> getDepartmentEmployeesFindByIdDepartment(long idDepartment) {
        return departmentRepository.getDepartmentEmployeesFindByIdDepartment(idDepartment);
    }




}
