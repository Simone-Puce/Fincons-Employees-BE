package com.fincons.dto;

public class EmployeeDepartmentDTO {


    private String departmentName;
    private String employeeLastName;

    public EmployeeDepartmentDTO(String departmentName, String employeeLastName) {
        this.departmentName = departmentName;
        this.employeeLastName = employeeLastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }
}
