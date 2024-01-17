package com.fincons.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PositionDTO {
    private Long id;
    private String name;
    private Double salary;
    private List<EmployeeDTO> employees;

    public PositionDTO(Long id, String name, Double salary, List<EmployeeDTO> employees) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.employees = employees;
    }

    public PositionDTO(Long id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
