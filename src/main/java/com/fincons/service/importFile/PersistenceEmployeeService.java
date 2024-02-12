package com.fincons.service.importFile;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import java.util.List;

public interface PersistenceEmployeeService {
    void addIfNotPresent(EmployeeDTO employee, List<ErrorDetailDTO> duplicatedResultList) throws RuntimeException;
}
