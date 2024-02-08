package com.fincons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fincons.entity.Employee;
import java.util.Set;

public class ProjectDTO {

    private Long id;
    private String name;
    private String area;
    private String priority;
    @JsonIgnore
    private Set<Employee> employees;

    public ProjectDTO(Long id, String name, String area, String priority, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.priority = priority;
        this.employees = employees;
    }

    public ProjectDTO(Long id, String name, String area, String priority) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.priority = priority;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
