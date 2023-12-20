package com.fincons.dto;


import java.util.List;

public class RoleDTO {
    private String name;
    private Double salary;
    //@JsonIgnore
    private List<EmployeeDTO> employees;

    public RoleDTO(String name, Double salary, List<EmployeeDTO> employees) {
        this.name = name;
        this.salary = salary;
        this.employees = employees;
    }

    public RoleDTO(String name, Double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}
