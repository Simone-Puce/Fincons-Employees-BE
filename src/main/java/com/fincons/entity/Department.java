package com.fincons.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_code", nullable = false)
    private String departmentCode;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String city;

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.LAZY)
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

    public Department(Long id, String departmentCode, String name, String address, String city) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.name = name;
        this.address = address;
        this.city = city;
    }
    public Department(String departmentCode, String name, String address, String city) {
        this.departmentCode = departmentCode;
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
