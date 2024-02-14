package com.fincons.dto;


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
public class DepartmentDTO {
    private String departmentCode;
    private String name;
    private String address;
    private String city;
    private List<EmployeeDTO> employees;


    public DepartmentDTO(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public DepartmentDTO(String departmentCode, String name, String address, String city) {
        this.departmentCode = departmentCode;
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
