package com.fincons.service.employeeService;

import com.fincons.entity.Department;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    Department getDepartmentById(String departmentId);
    List<Department> getAllDepartment();
    Department createDepartment(DepartmentDTO departmentDTO);
    Department updateDepartmentById(String departmentId, DepartmentDTO departmentDTO);
    void deleteDepartmentById(String departmentId);
    List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(String departmentId);



}
