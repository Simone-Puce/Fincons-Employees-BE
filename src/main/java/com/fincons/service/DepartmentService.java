package com.fincons.service;

import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    Department save(Department department);

    Department findById(long id);

    List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(long idDepartment);
    Department update(long id, Department department);

    void deleteById(long id);


}
