package com.fincons.service;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<Object> findById(long id);
    ResponseEntity<Object> findAll();
    ResponseEntity<Object> save(Department department);
    ResponseEntity<Object> update(long id, Department department);
    ResponseEntity<Object> deleteById(long id);

    ResponseEntity<Object> getDepartmentEmployeesFindByIdDepartment(long id);



}
