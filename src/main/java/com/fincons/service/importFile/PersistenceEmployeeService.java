package com.fincons.service.importFile;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.exception.EmailException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface PersistenceEmployeeService {
    void addIfNotPresent(EmployeeDTO employee, List<ErrorDetailDTO> duplicatedResultList) throws RuntimeException;
}
