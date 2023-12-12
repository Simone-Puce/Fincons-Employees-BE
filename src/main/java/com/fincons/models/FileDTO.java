package com.fincons.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDTO {

    private Long id;
    private String file64;

    private String name;

    private String description;


    @JsonCreator
    public FileDTO(
            @JsonProperty("fileId") Long Id,
            @JsonProperty("file") String File64,
            @JsonProperty("name") String Name,
            @JsonProperty("description") String Description)

    {
        this.id = id;
        this.file64 = file64;
        this.name = name;
        this.description = description;

    }

    public FileDTO(long id, String file64, String name, String description) {
    }

    public Long getFileId() {
        return id;
    }

    public void setFileId(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}