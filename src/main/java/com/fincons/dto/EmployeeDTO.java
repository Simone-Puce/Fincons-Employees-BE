package com.fincons.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincons.entity.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class EmployeeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private String email;
    private LocalDate startDate;
    private LocalDate endDate;
    private Department department;
    private Position position;
    private Set<Project> projects;
    private List<CertificateEmployee> certificates;
    private List<File> fileList;

    private long rowNum;


    public EmployeeDTO(String firstName, String lastName, String gender, LocalDate birthDate, String email, LocalDate startDate, LocalDate endDate, Department department, Position position) {
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


    public EmployeeDTO(Long id, String firstName, String lastName, String gender, LocalDate birthDate, String email, LocalDate startDate, LocalDate endDate, Department department, Position position, Set<Project> projects, List<CertificateEmployee> certificates, List<File> fileList) {
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
        this.certificates = certificates;
        this.fileList = fileList;
    }


    public EmployeeDTO(Long id, String firstName, String lastName, String gender, String email, LocalDate birthDate, LocalDate startDate, LocalDate endDate, List<File> fileList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileList = fileList;
    }

    public EmployeeDTO(Long id, String firstName, String lastName, String gender, String email, LocalDate birthDate, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EmployeeDTO() {
    }


    public EmployeeDTO(Long id, String firstName, String lastName, String gender, String email, LocalDate birthDate,  LocalDate startDate, LocalDate endDate, String departmentName, String departmentAddress, String departmentCity, Position position, Set<Project> projects, List<File> fileList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.projects = projects;
        this.fileList = fileList;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public List<CertificateEmployee> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificateEmployee> certificates) {
        this.certificates = certificates;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public long getRowNum() {
        return rowNum;
    }

    public void setRowNum(long rowNum) {
        this.rowNum = rowNum;
    }

}
