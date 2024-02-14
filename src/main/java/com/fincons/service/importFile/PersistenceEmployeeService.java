package com.fincons.service.importFile;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.entity.Employee;

import java.util.List;

public interface PersistenceEmployeeService {
    void addIfNotPresent(Employee employeeEntity,EmployeeDTO employeeDTO ,List<ErrorDetailDTO> duplicatedResultList) throws RuntimeException;
}
