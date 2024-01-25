package com.fincons.service.employeeService;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<Object> getDepartmentById(String idDepartment);
    ResponseEntity<Object> getAllDepartment();
    ResponseEntity<Object> createDepartment(DepartmentDTO departmentDTO);
    ResponseEntity<Object> updateDepartmentById(String idDepartment, DepartmentDTO departmentDTO);
    ResponseEntity<Object> deleteDepartmentById(String idDepartment);
    ResponseEntity<Object> getDepartmentEmployeesFindByIdDepartment(String idDepartment);



}
