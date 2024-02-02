package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JsonBackReference(value = "file-employee")
    @JoinColumn(name = "employee_id")
    private Employee empId;


    public File() {}


    public File( String file64, String name, String extension, String description, Employee empId) {
        this.file64 = file64;
        this.name = name;
        this.extension = extension;
        this.description = description;
        this.empId = empId;
    }


}

