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
    private DepartmentMapper modelMapperProject;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper modelMapperProject) {
        this.departmentRepository = departmentRepository;
        this.modelMapperProject = modelMapperProject;
    }

    @Override
    public ResponseEntity<Object> getDepartmentById(String idDepartment) {

        Department existingDepartment = validateDepartmentById(idDepartment);
        DepartmentDTO departmentDTO = modelMapperProject.mapToDTO(existingDepartment);

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found department with ID " + idDepartment + ".",
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
                DepartmentDTO departmentDTO = modelMapperProject.mapToDTO(department);
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
    public ResponseEntity<Object> createDepartment(DepartmentDTO departmentDTO) {

        //Condition for not have null attributes
        validateDepartmentFields(departmentDTO);

        List<Department> departments = departmentRepository.findAll();
        //Condition if there are departments with name same
        checkForDuplicateDepartment(departmentDTO, departments);

        Department department = modelMapperProject.mapToEntity(departmentDTO);

        departmentRepository.save(department);

        departmentDTO.setDepartmentId(department.getDepartmentId());

        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Department with ID "+ departmentDTO.getDepartmentId() +" has been successfully updated!",
                (HttpStatus.OK), departmentDTO);
    }


    @Override
    public ResponseEntity<Object> updateDepartmentById(String idDepartment, DepartmentDTO departmentDTO) {


        //Condition for not have null attributes
        validateDepartmentFields(departmentDTO);

        List<Department> departments = departmentRepository.findAll();

        //Check if the specified ID exists
        Department existingDepartment = validateDepartmentById(idDepartment);




        existingDepartment.setDepartmentId(idDepartment);
        existingDepartment.setName(departmentDTO.getName());
        existingDepartment.setAddress(departmentDTO.getAddress());
        existingDepartment.setCity(departmentDTO.getCity());

        List<Department> departmentsWithoutDepartmentIdChosed = new ArrayList<>();

        for (Department d : departments ) {
            if(!Objects.equals(d.getDepartmentId(), idDepartment)){
                departmentsWithoutDepartmentIdChosed.add(d);
            }
        }

        if(departmentsWithoutDepartmentIdChosed.isEmpty()){
            departmentRepository.save(existingDepartment);
            departmentDTO.setDepartmentId(existingDepartment.getDepartmentId());
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
                    departmentDTO.setDepartmentId(existingDepartment.getDepartmentId());
                }
            }
        }



        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Department with ID "+ idDepartment +" has been successfully updated!",
                (HttpStatus.OK),
                departmentDTO);
    }

    @Override
    public ResponseEntity<Object> deleteDepartmentById(String idDepartment) {

        Department department = validateDepartmentById(idDepartment);
        departmentRepository.deleteById(department.getId());
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Department with ID "+ idDepartment +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    @Override
    public ResponseEntity<Object> getDepartmentEmployeesFindByIdDepartment(String idDepartment) {
        Department department = validateDepartmentById(idDepartment);

        List<EmployeeDepartmentDTO> idDepartmentForEmployee = departmentRepository.getDepartmentEmployeesFindByIdDepartment(department.getId());

        if(idDepartmentForEmployee.isEmpty()){
            throw new IllegalArgumentException("Department with ID: " + idDepartment + " is Empty");
        }
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: This Department has " + idDepartmentForEmployee.size() +
                        (idDepartmentForEmployee.size() == 1 ? " employee." : " employees."),
                (HttpStatus.OK),
                idDepartmentForEmployee);
    }

    public Department validateDepartmentById(String idDepartment){
        Department existingDepartment = departmentRepository.findByDepartmentId(idDepartment);

        if(Objects.isNull(existingDepartment)){
            throw new ResourceNotFoundException("Department with ID: " + idDepartment + " not found");
        }
        return existingDepartment;
    }

    public void validateDepartmentFields(DepartmentDTO departmentDTO){
        //If one field is true run Exception
        if (Strings.isEmpty(departmentDTO.getName())||
                Strings.isEmpty(departmentDTO.getAddress()) ||
                Strings.isEmpty(departmentDTO.getCity())) {
            throw new IllegalArgumentException("The fields of the Department can't be null or empty");
        }
    }
    public void checkForDuplicateDepartment(DepartmentDTO departmentDTO, List<Department> departments){
        for (Department department1 : departments) {
            if (department1.getName().equals(departmentDTO.getName())) {
                throw new IllegalArgumentException("Department with the same name, already exists");
            }
        }
    }
}
