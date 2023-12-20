package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "employee")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    //@NotNull
    @Column(name = "gender")
    private String gender;


    @Column(name = "birth_date"/*, nullable = false,*/)
    private LocalDate birthDate;

    //@DateTimeFormat(pattern = "dd-MM-yyyy") Questo è comunicante con il front-end non con il database


    @Column(name = "start_date")
    private LocalDate startDate;


    @Column(name = "end_date")
    private LocalDate endDate;
    @ManyToOne
    @JsonBackReference(value = "department-employee")
    @JoinColumn(name = "id_department") //Questa è la foreign key che verrà collegata con l'id di project, è la foreign key
    private Department department;           //questo "project"

    @ManyToOne
    @JsonBackReference(value = "role-employee")
    @JoinColumn(name = "id_role")
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value= "employee-project")
    @JsonIgnore
    @JoinTable(name = "employee_project",
            joinColumns = {
                @JoinColumn(name = "id_employee", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "id_project", referencedColumnName = "id")
            }
    )

    private Set<Project> projects; //Lui punta alla tabella project


    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String gender, LocalDate birthDate, LocalDate startDate, LocalDate endDate, Department department, Role role, Set<Project> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.role = role;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }


}
