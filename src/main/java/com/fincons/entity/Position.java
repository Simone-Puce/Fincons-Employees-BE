package com.fincons.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Double salary;

    @OneToMany(
            mappedBy = "position",
            fetch = FetchType.LAZY)
    @JsonManagedReference(value = "position-employee")
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Position() {
    }

    public Position(Long id, String name, Double salary) {
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


}
