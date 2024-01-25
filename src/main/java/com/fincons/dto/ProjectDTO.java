package com.fincons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fincons.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProjectDTO {


    private String projectId;
    private String name;
    private String area;
    private String priority;
    private List<EmployeeDTO> employees;

    public ProjectDTO(String name, String area, String priority) {
        this.name=name;
        this.area=area;
        this.priority=priority;
    }

    public ProjectDTO(String projectId, String name, String area, String priority) {
        this.projectId = projectId;
        this.name = name;
        this.area = area;
        this.priority = priority;
    }
}
