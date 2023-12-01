package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
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
    private Date birthDate;

    //@DateTimeFormat(pattern = "dd-MM-yyyy") Questo è comunicante con il front-end non con il database


    @Column(name = "start_date")
    private Date startDate;


    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne
    @JsonBackReference(value = "department-employee")
    @JoinColumn(name = "id_department") //Questa è la foreign key che verrà collegata con l'id di project, è la foreign key
    private Department department;           //questo "project"

    @ManyToOne
    @JsonBackReference(value = "role-employee")
    @JoinColumn(name = "id_role")
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    //JsonManagedReference(value= "employee-project")
    @JsonIgnore
    @JoinTable(name = "employee_project",
            joinColumns = {
                @JoinColumn(name = "id_employee", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "id_project", referencedColumnName = "id")
            }
    )

    private Set<Project> project; //Lui punta alla tabella project


    public Employee() {
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


    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public Set<Project> getProject() {
        return project;
    }

    public void setProject(Set<Project> project) {
        this.project = project;
    }

    public Employee(Long id, String firstName, String lastName, String gender, Date birthDate, Date startDate, Date endDate, Department department, Role role, Set<Project> project) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.role = role;
        this.project = project;
    }
}
