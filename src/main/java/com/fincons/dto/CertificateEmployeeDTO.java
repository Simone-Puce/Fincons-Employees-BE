package com.fincons.dto;


import com.fincons.entity.Certificate;
import com.fincons.entity.Employee;

import java.time.LocalDate;

public class CertificateEmployeeDTO {

    private Long id;
    private Certificate certificate;
    private Employee employee;
    private LocalDate achieved;

    public CertificateEmployeeDTO() {
    }

    public CertificateEmployeeDTO(Long id, Certificate certificate, Employee employee) {
        this.id = id;
        this.certificate = certificate;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getAchieved() {
        return achieved;
    }

    public void setAchieved(LocalDate achieved) {
        this.achieved = achieved;
    }
}
