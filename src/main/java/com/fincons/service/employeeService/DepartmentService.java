package com.fincons.service.employeeService;

import com.fincons.dto.DepartmentDTO;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.entity.Department;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    Department getDepartmentByCode(String departmentCode);
    List<Department> getAllDepartment();
    Department createDepartment(DepartmentDTO departmentDTO);
    Department updateDepartmentByCode(String departmentCode, DepartmentDTO departmentDTO);
    void deleteDepartmentByCode(String departmentCode);
    List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByCodeDepartment(String departmentCode);



}
