package com.fincons.service.employeeService.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.employeeService.DepartmentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public ResponseEntity<Object> getDepartmentById(long id) {

        Department existingDepartment = validateDepartmentById(id);
        DepartmentDTO departmentDTO = departmentMapper.mapDepartmentToDepartmentDto(existingDepartment);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found department with ID " + id + ".",
                (HttpStatus.OK),
                departmentDTO);
    }

    @Override
    public ResponseEntity<Object> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> newListDepartment = new ArrayList<>();
        //Check if the list of department is empty
        for (Department department : departments) {
            if (department != null) {
                DepartmentDTO departmentDTO = departmentMapper.mapDepartmentToDepartmentDto(department);
                newListDepartment.add(departmentDTO);
            } else {
                throw new IllegalArgumentException("There aren't Departments");
            }
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found " + newListDepartment.size() +
                        (newListDepartment.size() == 1 ? " department" : " departments") + " in the list.",
                (HttpStatus.OK),
                newListDepartment);
    }

    @Override
    public ResponseEntity<Object> createDepartment(Department department) {

        //Condition for not have null attributes
        validateDepartmentFields(department);

        List<Department> departments = departmentRepository.findAll();
        //Condition if there are departments with name same
        checkForDuplicateDepartment(department, departments);

        DepartmentDTO departmentDTO = departmentMapper.mapDepartmentToDepartmentDto(department);
        departmentRepository.save(department);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Department with ID "+ department.getId() +" has been successfully updated!",
                (HttpStatus.OK), departmentDTO);
    }


    @Override
    public ResponseEntity<Object> updateDepartmentById(long id, Department department) {


        //Condition for not have null attributes
        validateDepartmentFields(department);

        DepartmentDTO departmentDTO;
        //Check if the specified ID exists
        Department existingDepartment = validateDepartmentById(id);
        
        List<Department> departments = departmentRepository.findAll();


        existingDepartment.setId(id);
        existingDepartment.setName(department.getName());
        existingDepartment.setAddress(department.getAddress());
        existingDepartment.setCity(department.getCity());

        List<Department> departmentsWithoutDepartmentIdChosed = new ArrayList<>();

        for ( Department d : departments ) {
            if(d.getId() != id){
                departmentsWithoutDepartmentIdChosed.add(d);
            }
        }

        if(departmentsWithoutDepartmentIdChosed.isEmpty()){
            departmentRepository.save(existingDepartment);
        }
        else {
            for (Department d : departmentsWithoutDepartmentIdChosed ) {
                if(d.getName().equals(existingDepartment.getName()) &&
                        d.getAddress().equals(existingDepartment.getAddress()) &&
                        d.getCity().equals(existingDepartment.getCity())
                ){
                    throw new IllegalArgumentException("The department existing yet");
                }else{
                    departmentRepository.save(existingDepartment);
                }
            }
        }

        departmentDTO = departmentMapper.mapDepartmentToDepartmentDto(department);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Department with ID "+ id +" has been successfully updated!",
                (HttpStatus.OK),
                departmentDTO);
    }

    @Override
    public ResponseEntity<Object> deleteDepartmentById(long id) {

        validateDepartmentById(id);
        departmentRepository.deleteById(id);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Department with ID "+ id +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    @Override
    public ResponseEntity<Object> getDepartmentEmployeesFindByIdDepartment(long id) {
        validateDepartmentById(id);
        List<EmployeeDepartmentDTO> idDepartmentForEmployee;

        idDepartmentForEmployee = departmentRepository.getDepartmentEmployeesFindByIdDepartment(id);
        if(idDepartmentForEmployee.isEmpty()){
            throw new IllegalArgumentException("Department with ID: " + id + " is Empty");
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: This Department has " + idDepartmentForEmployee.size() +
                        (idDepartmentForEmployee.size() == 1 ? " employee." : " employees."),
                (HttpStatus.OK),
                idDepartmentForEmployee);
    }

    private Department validateDepartmentById(long id){
        Department existingDepartment = departmentRepository.findById(id);

        if(Objects.isNull(existingDepartment)){
            throw new ResourceNotFoundException("Department with ID: " + id + " not found");
        }
        return existingDepartment;
    }

    private void validateDepartmentFields(Department department){
        //If one field is true run Exception
        if (Strings.isEmpty(department.getName())||
                Strings.isEmpty(department.getAddress()) ||
                Strings.isEmpty(department.getCity())) {
            throw new IllegalArgumentException("The fields of the Department can't be null or empty");
        }
    }
    private void checkForDuplicateDepartment(Department department, List<Department> departments){
        for (Department department1 : departments) {
            if (department1.getName().equals(department.getName())) {
                throw new IllegalArgumentException("Department with the same name, already exists");
            }
        }
    }
}
