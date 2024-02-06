package com.fincons.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_uuid")
    @UuidGenerator
    private String positionId;

    @Column
    private String name;
    @Column
    private Double salary;

    @OneToMany(
            mappedBy = "position",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
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


    public Position(Long id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}
