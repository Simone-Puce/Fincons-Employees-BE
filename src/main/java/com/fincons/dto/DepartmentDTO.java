package com.fincons.dto;


import java.util.List;

public class DepartmentDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private List<EmployeeDTO> employees;

    public DepartmentDTO(Long id, String name, String address, String city, List<EmployeeDTO> employees) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.employees = employees;
    }

    public DepartmentDTO(Long id, String name, String address, String city) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public DepartmentDTO() {

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
