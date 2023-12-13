package com.fincons.service;



import com.fincons.model.EmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceApi {


    List<EmployeeDto> getAllEmployee();

    Optional<EmployeeDto> getEmployeeById(Long id);

    Optional<EmployeeDto> createEmployee(EmployeeDto employeeDto) throws Exception;

    Optional<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployee(Long id);

}
