package com.fincons.service.employeeService;

import com.fincons.entity.Department;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    ResponseEntity<Object> getDepartmentById(long id);
    ResponseEntity<Object> getAllDepartment();
    ResponseEntity<Object> createDepartment(Department department);
    ResponseEntity<Object> updateDepartmentById(long id, Department department) throws Exception;
    ResponseEntity<Object> deleteDepartmentById(long id);
    ResponseEntity<Object> getDepartmentEmployeesFindByIdDepartment(long id);



}
