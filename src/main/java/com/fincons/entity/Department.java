package com.fincons.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fincons.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_uuid")
    @UuidGenerator
    private String departmentId;

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
    private List<Employee> employees;


    public Department(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Department(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }


}
