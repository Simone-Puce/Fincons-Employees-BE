package com.fincons.service.importFile.impl;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Employee;
import com.fincons.entity.Position;
import com.fincons.entity.User;
import com.fincons.enums.ErrorCode;
import com.fincons.service.authService.UserServiceImpl;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.employeeService.impl.DepartmentServiceImpl;
import com.fincons.service.employeeService.impl.EmployeeServiceImpl;
import com.fincons.service.employeeService.impl.PositionServiceImpl;
import com.fincons.service.importFile.PersistenceEmployeeService;
import com.fincons.utility.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersistenceEmployeeServiceImpl implements PersistenceEmployeeService {

    @Autowired
    EmployeeServiceImpl employeeService;
    @Autowired
    DepartmentServiceImpl departmentService;
    @Autowired
    PositionServiceImpl positionService;
    @Autowired
    UserServiceImpl userService;


    @Override
    @Transactional
    public void addIfNotPresent(Employee employeeEntity,EmployeeDTO employeeDTO ,List<ErrorDetailDTO> duplicatedResultList) throws RuntimeException {
       //TODO - contatori
        long nRow = employeeDTO.getRowNum();

        boolean employeeExists = employeeService.employeeExists(employeeEntity);

        if (!employeeExists) {
            Employee insertedEmployee = employeeService.addEmployeeFromFile(employeeEntity);
            //SE DOPO LA VALIDAZIONE E LA CONFERMA CHE IL DIPENDENTE NON è PRESENTE NEL DB,
            //CREO UN ACCOUNT CON PASSWORD TEMPORANEA PER PERMETTERE ALL'UTENTE DI ESSERE REGISTRATO AUTOMATICAMENTE
            //AL PORTALE E SFRUTTARE LA PIATTAFORMA DOPO UN CAMBIO DELLA PASSWORD
            User newUser = new User(employeeEntity.getEmail(), employeeEntity.getFirstName(), employeeEntity.getLastName(), "Password!");
            userService.addNewUser(newUser);
        } else {
            duplicatedResultList.add(new ErrorDetailDTO(nRow, "Email: " + employeeDTO.getEmail(), ErrorCode.EMPLOYEE_ALREADY_EXISTS));
        }
    }

}
