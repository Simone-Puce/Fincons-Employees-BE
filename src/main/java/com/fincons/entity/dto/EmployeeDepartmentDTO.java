package com.fincons.entity.dto;

public class FindByIdEmployeeForProjectDTO {


    private String departmentName;
    private String employeeName;

    public FindByIdEmployeeForProjectDTO(String departmentName, String employeeName) {
        this.departmentName = departmentName;
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
