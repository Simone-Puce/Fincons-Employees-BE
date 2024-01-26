package com.fincons.controller;


import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.service.employeeService.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${department.uri}")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/find-by-id")
    public ResponseEntity<Object> getDepartmentById(@RequestParam String departmentId){
        return departmentService.getDepartmentById(departmentId);
    }
    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllDepartment(){
        return  departmentService.getAllDepartment();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createDepartment(@RequestBody DepartmentDTO departmentDTO){
        return departmentService.createDepartment(departmentDTO);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateDepartmentById(@RequestParam String departmentId, @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.updateDepartmentById(departmentId, departmentDTO);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteDepartmentById(@RequestParam String departmentId){
       return departmentService.deleteDepartmentById(departmentId);
    }
    @GetMapping(value = "/find-employees")
    public ResponseEntity<Object> getDepartmentFindEmployee(@RequestParam String departmentId){
        return departmentService.getDepartmentEmployeesFindByIdDepartment(departmentId);
    }

}

