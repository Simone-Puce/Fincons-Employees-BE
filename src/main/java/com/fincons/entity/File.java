package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "files")
public class File {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "file64", length = 20971520) // to work remote
    private String file64;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "description")
    private String description;


    @JsonBackReference(value = "file-employee")
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee empId;


    public File() {}


    public File( String file64, String name, String extension, String description, Employee empId) {
        this.file64 = file64;
        this.name = name;
        this.extension = extension;
        this.description = description;
        this.empId = empId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile64() {
        return file64;
    }

    public void setFile64(String file64) {
        this.file64 = file64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmpId() {
        return empId;
    }

    public void setEmpId(Employee empId) {
        this.empId = empId;
    }
}

