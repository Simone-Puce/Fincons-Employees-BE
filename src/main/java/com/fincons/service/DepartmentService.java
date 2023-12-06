package com.fincons.service;

import com.fincons.entity.Department;
import com.fincons.entity.dto.DepartmentEmployeesDTO;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    Department saveDepartment(Department department);

    Department findById(long id);

    List<DepartmentEmployeesDTO> getDepartmentEmployeesFindByIdDepartment(long idDepartment);
}
