package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_uuid")
    @UuidGenerator
    private String employeeId;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "gender")
    private String gender;

    @Column(name="email")
    private String email;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JsonBackReference(value = "department-employee")
    @JoinColumn(name = "id_department")
    private Department department;

    @ManyToOne
    @JsonBackReference(value = "position-employee")
    @JoinColumn(name = "id_position")
    private Position position;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST , CascadeType.MERGE})
    @JoinTable(name = "employee_project",
            joinColumns = {
                @JoinColumn(name = "id_employee", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "id_project", referencedColumnName = "id")
            }
    )
    private List<Project> projects;

    @OneToMany(
            mappedBy = "emp",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "file-employee")
    private List<File> fileList;


    public Employee(Long id, String firstName, String lastName, String gender, String email, LocalDate birthDate, LocalDate startDate, LocalDate endDate, Department department, Position position, List<Project> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.position = position;
        this.projects = projects;
    }

    public Employee(Long id, String firstName, String lastName, String gender, String email, LocalDate birthDate, LocalDate startDate, LocalDate endDate, Department department, Position position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.position = position;
    }
}
