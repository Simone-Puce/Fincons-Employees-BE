package com.fincons.service.importFile.impl;

import com.fincons.controller.UserController;
import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.User;
import com.fincons.enums.ErrorCode;
import com.fincons.exception.EmailException;
import com.fincons.repository.UserRepository;
import com.fincons.service.authService.UserServiceImpl;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.importFile.PersistenceEmployeeService;
import com.fincons.utility.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
 @Service
public class PersistenceEmployeeServiceImpl implements PersistenceEmployeeService {

    @Autowired
    EmployeeService employeeService;

    //rebase, con difficoltà //implementata funzione //gestione transactional //attesa lorenzo per i campi(?)
    @Autowired
    UserServiceImpl userService;


    @Override
    @Transactional
    public void addIfNotPresent(EmployeeDTO employee, List<ErrorDetailDTO> duplicatedResultList) throws RuntimeException{
        int contEmployee=0, contUser=0;
            long nRow= employee.getRowNum();
            boolean employeeExists= employeeService.employeeExists(EmployeeMapper.convertToEntity(employee));
                if(!employeeExists){
                    Employee employeeEntity= EmployeeMapper.convertToEntity(employee);

                        Employee insertedEmployee = employeeService.addEmployeeFromFile(employeeEntity);
                        if(employeeEntity.getEmail().equals(insertedEmployee.getEmail())){
                           contEmployee++;
                        }
                        //SE DOPO LA VALIDAZIONE E LA CONFERMA CHE IL DIPENDENTE NON è PRESENTE NEL DB,
                        //CREO UN ACCOUNT CON PASSWORD TEMPORANEA PER PERMETTERE ALL'UTENTE DI ESSERE REGISTRATO AUTOMATICAMENTE
                        //AL PORTALE E SFRUTTARE LA PIATTAFORMA DOPO UN CAMBIO DELLA PASSWORD
                             User newUser = new User(employeeEntity.getEmail(), employeeEntity.getFirstName(), employeeEntity.getLastName(), "Password!");

                             userService.addNewUser(newUser);
                             contUser++;
                }else{
                     duplicatedResultList.add(new ErrorDetailDTO(nRow, "Email: " + employee.getEmail(), ErrorCode.RESOURCE_ALREADY_EXISTS));
                     }
                    System.out.println("Dipendenti Aggiunti: "+ contEmployee + "\nAccount Registrati: "+ contUser);



            /*
             if(employeeExists){
                duplicatedResultList.add(new ErrorDetailDTO(nRow, "Email: " + employee.getEmail(), ErrorCode.RESOURCE_ALREADY_EXISTS));
                throw new EmailException(EmailException.emailInvalidOrExist());
            }else{
                Employee employeeEntity= EmployeeMapper.convertToEntity(employee);
                employeeService.addEmployeeFromFile(employeeEntity);
                contEmployee++;
                //SE DOPO LA VALIDAZIONE E LA CONFERMA CHE IL DIPENDENTE NON è PRESENTE NEL DB, CREO UN ACCOUNT CON PASSWORD TEMPORANEA PER
                //PERMETTERE ALL'UTENTE DI ESSERE REGISTRATO AUTOMATICAMENTE AL PORTALE E SFRUTTARE LA PIATTAFORMA DOPO UN CAMBIO DELLA PASSWORD
                User newUser = new User(employeeEntity.getEmail(), employeeEntity.getFirstName(), employeeEntity.getLastName(), "Password!");
                if(userService.addNewUser(newUser)){
                    contUser++;
                } else{
                    duplicatedResultList.add(new ErrorDetailDTO(nRow, "Email: "+ employee.getEmail(), ErrorCode.USER_ALREADY_EXISTS));
                    throw new EmailException(EmailException.emailInvalidOrExist());
                }



                System.out.println("Dipendenti Aggiunti: "+ contEmployee + "\nAccount Registrati: "+ contUser);

            } */


    }
}
