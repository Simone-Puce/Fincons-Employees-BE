package com.fincons.dto;

import java.util.List;

public class ImportFileDTO {
    private final List<EmployeeDTO> employeeList;
    private final List<ErrorDetailDTO> errorList;

    public ImportFileDTO(List<EmployeeDTO> employeeList, List<ErrorDetailDTO> errorList) {
        this.employeeList = employeeList;
        this.errorList = errorList;
    }

    public List<EmployeeDTO> getEmployeeList() {
        return employeeList;
    }

    public List<ErrorDetailDTO> getErrorList() {
        return errorList;
    }
}
