package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "activate")
    private boolean activate;
    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CertificateEmployee> employees;

    public Certificate() {}

    public Certificate(Long id, String name, boolean activate) {
        this.id = id;
        this.name = name;
        this.activate = activate;
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

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public List<CertificateEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<CertificateEmployee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activate=" + activate +
                ", employees=" + employees +
                '}';
    }
}
