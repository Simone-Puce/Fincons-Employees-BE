package com.fincons.entity.dto;

import org.springframework.beans.factory.annotation.Autowired;
public class DepartmentEmployees {


    private String departmentName;
    private String employeeName;

    public DepartmentEmployees(String departmentName, String employeeName) {
        this.departmentName = departmentName;
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getemployeeName() {
        return employeeName;
    }

    public void setemployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
