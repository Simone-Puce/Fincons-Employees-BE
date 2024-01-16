package com.fincons.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "certificate_employee")
public class CertificateEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificateId")
    @JsonBackReference(value= "certificate_employee")
    private Certificate certificate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId")
    @JsonBackReference(value= "certificate_employee")
    private Employee employee;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "achieved")
    private LocalDate achieved;

    public CertificateEmployee() {
    }

    public CertificateEmployee(Long id, Certificate certificate, Employee employee, LocalDate achieved) {
        this.id = id;
        this.certificate = certificate;
        this.employee = employee;
        this.achieved = achieved;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateEmployee that = (CertificateEmployee) o;
        return Objects.equals(id, that.id) && Objects.equals(certificate, that.certificate) && Objects.equals(employee, that.employee) && Objects.equals(achieved, that.achieved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificate, employee, achieved);
    }
}
