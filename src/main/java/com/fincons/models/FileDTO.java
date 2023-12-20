package com.fincons.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fincons.entities.Employee;


public class FileDTO {

    private String file64;

    private String name;

    private String extension;

    private String description;

    private Employee employeeId;

    public FileDTO() {
    }

    @JsonCreator
    public FileDTO(
            @JsonProperty("file64") String file64,
            @JsonProperty("name") String name,
            @JsonProperty("extension") String extension,
            @JsonProperty("description") String description,
            @JsonProperty("employeeId") Employee employeeId
    ) {
        this.file64 = file64;
        this.name = name;
        this.extension = extension;
        this.description = description;
        this.employeeId = employeeId;
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

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }
}