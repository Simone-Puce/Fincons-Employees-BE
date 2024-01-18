package com.fincons.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;



@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String city;

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.LAZY)
    @JsonManagedReference(value = "department-employee")
    @JsonIgnore
    private List<Employee> employees;
    public Department() {
    }

    public Department(Long id, String name, String address, String city) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
    }
    public Department(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getEmployees(), that.getEmployees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getCity(), getEmployees());
    }
}
