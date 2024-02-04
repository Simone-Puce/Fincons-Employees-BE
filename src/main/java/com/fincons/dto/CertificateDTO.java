package com.fincons.dto;

import com.fincons.entity.CertificateEmployee;

import java.util.List;

public class CertificateDTO {
    private Long id;
    private String name;
    private boolean  activate;
    private List<CertificateEmployee> employees;

    public CertificateDTO(Long id, String name, boolean activate, List<CertificateEmployee> employees) {
        this.id = id;
        this.name = name;
        this.activate = activate;
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
}
