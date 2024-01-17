package com.fincons.utility;

import com.fincons.dto.EmployeeDTO;
import com.fincons.entity.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    //converti da entity a dto
    public static EmployeeDTO convertToDto(Employee employee){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(employee, EmployeeDTO.class);
    }
    //converti da dto ad entity
    public static Employee convertToEntity(EmployeeDTO employeeDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(employeeDto, Employee.class);
    }

    // Converte una lista di Entity in Dto
    public static List<EmployeeDTO> convertListToDto(List<Employee> entities) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return entities.stream()
                .map(EmployeeMapper::convertToDto)
                .collect(Collectors.toList());
    }

    //Converte una lista di Dto in Entity
    public static List<Employee> convertListToEntity(List<EmployeeDTO> dtos) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return dtos.stream()
                .map(EmployeeMapper::convertToEntity)
                .collect(Collectors.toList());
    }
}
