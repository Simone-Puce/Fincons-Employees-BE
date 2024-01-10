package com.fincons.dto;

import com.fincons.entity.Employee;

import java.util.List;

public class DepartmentDTO {
    private String name;
    private String address;
    private String city;
    private List<EmployeeDTO> employees;

    public DepartmentDTO(String name, String address, String city, List<EmployeeDTO> employees) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.employees = employees;
    }

    public DepartmentDTO(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public DepartmentDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public List<EmployeeDTO> getEmployees() {
        return employees;
    }
    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}
