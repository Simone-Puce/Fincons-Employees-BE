package com.fincons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectDTO {

    private String lastNameEmployee;
    private String ssn;
    private String nameProject;
    private String projectId;


}
