package com.fincons.service.impl;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.DepartmentMapper;
import com.fincons.repository.DepartmentRepository;
import com.fincons.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ResponseEntity<DepartmentDTO> findById(long id) {

        Department existingDepartment = getDepartmentById(id);
        DepartmentDTO departmentDTO = departmentMapper.mapDepartment(existingDepartment);
        return ResponseEntity.ok(departmentDTO);

    }

    @Override
    public ResponseEntity<List<DepartmentDTO>> findAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> newListDepartment = new ArrayList<>();
        //Check if the list of department is empty
        for (Department department : departments) {
            if (department != null) {
                DepartmentDTO departmentDTO = departmentMapper.mapDepartment(department);
                newListDepartment.add(departmentDTO);
            } else {
                throw new IllegalArgumentException("There aren't Departments");
            }
        }
        return ResponseEntity.ok(newListDepartment);
    }

    @Override
    public ResponseEntity<DepartmentDTO> save(Department department) {

        //Condition for not have null attributes
        validateDepartmentFields(department);

        List<Department> departments = departmentRepository.findAll();
        //Condition if there are departments with name same
        checkForDuplicateEmployee(department, departments);

        DepartmentDTO departmentDTO = departmentMapper.mapDepartment(department);
        departmentRepository.save(department);
        return ResponseEntity.ok(departmentDTO);
    }


    @Override
    public ResponseEntity<DepartmentDTO> update(long id, Department department) {

        DepartmentDTO departmentDTO;
        //Condition for not have null attributes
        validateDepartmentFields(department);

        //Condition for not have null Department
        Department existingDepartment = getDepartmentById(id);

        existingDepartment.setName(department.getName());
        existingDepartment.setAddress(department.getAddress());
        existingDepartment.setCity(department.getCity());
        departmentRepository.save(existingDepartment);
        departmentDTO = departmentMapper.mapDepartment(department);

        return ResponseEntity.ok(departmentDTO);
    }

    @Override
    public ResponseEntity<DepartmentDTO> deleteById(long id) {

        getDepartmentById(id);
        departmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EmployeeDepartmentDTO>> getDepartmentEmployeesFindByIdDepartment(long id) {
        getDepartmentById(id);
        List<EmployeeDepartmentDTO> idDepartmentForEmployee;

        idDepartmentForEmployee = departmentRepository.getDepartmentEmployeesFindByIdDepartment(id);
        if(idDepartmentForEmployee.isEmpty()){
            throw new IllegalArgumentException("Department with ID: " + id + " is Empty");
        }
        return ResponseEntity.ok(idDepartmentForEmployee);
    }

    private Department getDepartmentById(long id){
        Department existingDepartment = departmentRepository.findById(id);

        if(existingDepartment == null){
            throw new ResourceNotFoundException("Department with ID: " + id + " not found");
        }
        return existingDepartment;
    }

    private void validateDepartmentFields(Department department){
        //If one field is true run Exception
        if (department.getName() == null || department.getName().isEmpty() ||
                department.getAddress() == null || department.getAddress().isEmpty() ||
                department.getCity() == null || department.getCity().isEmpty()) {
            throw new IllegalArgumentException("The fields of the Department can't be null or empty");
        }
    }
    private void checkForDuplicateEmployee(Department department, List<Department> departments){
        for (Department department1 : departments) {
            if (department1.getName().equals(department.getName())) {
                throw new IllegalArgumentException("The names can't be the same");
            }
        }
    }
}
