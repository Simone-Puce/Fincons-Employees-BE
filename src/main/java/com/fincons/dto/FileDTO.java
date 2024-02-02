package com.fincons.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class FileDTO {

    private String file64;

    private String name;

    private String extension;

    private String description;

    private EmployeeDTO empDTO;


    @JsonCreator
    public FileDTO(
            @JsonProperty("file64") String file64,
            @JsonProperty("name") String name,
            @JsonProperty("extension") String extension,
            @JsonProperty("description") String description,
            @JsonProperty("empDTO") EmployeeDTO empDTO
    ) {
        this.file64 = file64;
        this.name = name;
        this.extension = extension;
        this.description = description;
        this.empDTO = empDTO;
    }
}