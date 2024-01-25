package com.fincons.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PositionDTO {
    private String positionId;
    private String name;
    private Double salary;
    private List<EmployeeDTO> employees;

    public PositionDTO(String name, Double salary) {
        this.name = name;
        this.salary = salary;
    }

}
