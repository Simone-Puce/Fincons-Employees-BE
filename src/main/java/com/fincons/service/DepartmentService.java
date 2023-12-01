package com.fincons.service;

import com.fincons.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    Department saveDepartment(Department department);

    Department findById(long id);
}
