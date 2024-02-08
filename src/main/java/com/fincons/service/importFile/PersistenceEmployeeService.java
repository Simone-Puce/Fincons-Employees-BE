package com.fincons.service.importFile;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.exception.EmailException;

import java.util.List;

public interface PersistenceEmployeeService {
    List<ErrorDetailDTO> addIfNotPresent(List<EmployeeDTO> employeeList);
}
