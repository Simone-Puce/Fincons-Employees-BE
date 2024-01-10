package com.fincons.service.employeeService;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<Object> getDepartmentById(long id);
    ResponseEntity<Object> getAllDepartment();
    ResponseEntity<Object> createDepartment(Department department);
    ResponseEntity<Object> updateDepartmentById(long id, Department department);
    ResponseEntity<Object> deleteDepartmentById(long id);

    ResponseEntity<Object> getDepartmentEmployeesFindByIdDepartment(long id);



}
