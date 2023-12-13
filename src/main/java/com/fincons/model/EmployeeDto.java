package com.fincons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class EmployeeDto {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String img;

    private String agency;

    private LocalDate birthDate;

    private LocalDate hireDate;

    @JsonCreator
    public EmployeeDto(@JsonProperty("id") long id,@JsonProperty("firstName") String firstName,@JsonProperty("lastName") String lastName,@JsonProperty("email") String email, @JsonProperty("img") String img, @JsonProperty("agency") String agency, @JsonProperty("birthDate") LocalDate birthDate, @JsonProperty("hireDate") LocalDate hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.img = img;
        this.agency = agency;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
