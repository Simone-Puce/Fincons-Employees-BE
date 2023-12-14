package com.fincons.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class FileDTO {

    private String file64;

    private String name;

    private String description;

    //private Employee employeeId;
    private long employeeId;

    public FileDTO() {
    }

    @JsonCreator
    public FileDTO(
            @JsonProperty("file") String file64,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("employeeId") long employeeId
    ) {
        this.file64 = file64;
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
}