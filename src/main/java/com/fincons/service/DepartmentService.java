package com.fincons.service;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<DepartmentDTO> findById(long id);
    ResponseEntity<List<DepartmentDTO>> findAll();
    ResponseEntity<DepartmentDTO> save(Department department);
    ResponseEntity<DepartmentDTO> update(long id, Department department);
    ResponseEntity<DepartmentDTO> deleteById(long id);

    ResponseEntity<List<EmployeeDepartmentDTO>> getDepartmentEmployeesFindByIdDepartment(long id);



}
