package com.fincons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {


    private String employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private LocalDate birthDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String departmentId;
    private String positionId;
    private List<ProjectDTO> projects;
    private List<FileDTO> fileList;



    public EmployeeDTO(String firstName, String lastName, String gender, String email, LocalDate birthDate, LocalDate startDate, LocalDate endDate, List<FileDTO> fileList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.birthDate = birthDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileList = fileList;
    }

    public EmployeeDTO(String firstName, String lastName, String gender, String email, LocalDate birthDate, LocalDate startDate, LocalDate endDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
