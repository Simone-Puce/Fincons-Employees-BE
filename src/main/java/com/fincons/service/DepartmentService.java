package com.fincons.service;

import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    Department saveDepartment(Department department);

    Department findById(long id);

    List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(long idDepartment);
}
