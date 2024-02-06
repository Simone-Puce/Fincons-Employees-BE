package com.fincons.service.importFile.impl;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.enums.ErrorCode;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.importFile.PersistenceEmployeeService;
import com.fincons.utility.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

 @Service
public class PersistenceEmployeeServiceImpl implements PersistenceEmployeeService {

    @Autowired
    EmployeeService employeeService;
    @Override
    public List<ErrorDetailDTO> addIfNotPresent(List<EmployeeDTO> employeeList) {
        List<ErrorDetailDTO> duplicatedResultList= new ArrayList<>();
        for (EmployeeDTO employee: employeeList) {
            long nRow= employee.getRowNum();
            boolean employeeExists= employeeService.employeeExists(EmployeeMapper.convertToEntity(employee));
            if(employeeExists){
                duplicatedResultList.add(new ErrorDetailDTO(nRow, "EmailId: " + employee.getEmail(), ErrorCode.RESOURCE_ALREADY_EXISTS));
            }else{
                employeeService.addEmployeeFromFile(EmployeeMapper.convertToEntity(employee));
            }
        }
        return duplicatedResultList;
    }
}
