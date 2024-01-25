package com.fincons.controller;


import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.service.employeeService.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company-employee-management")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "${department.find-by-id}")
    public ResponseEntity<Object> getDepartmentById(@RequestParam String idDepartment){
        return departmentService.getDepartmentById(idDepartment);
    }
    @GetMapping(value="${department.list}")
    public ResponseEntity<Object> getAllDepartment(){
        return  departmentService.getAllDepartment();
    }
    @PostMapping(value = "${department.create}")
    public ResponseEntity<Object> createDepartment(@RequestBody DepartmentDTO departmentDTO){
        return departmentService.createDepartment(departmentDTO);
    }
    @PutMapping(value = "${department.update}")
    public ResponseEntity<Object> updateDepartmentById(@RequestParam String idDepartment, @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.updateDepartmentById(idDepartment, departmentDTO);
    }
    @DeleteMapping(value = "${department.delete}")
    public ResponseEntity<Object> deleteDepartmentById(@RequestParam String idDepartment){
       return departmentService.deleteDepartmentById(idDepartment);
    }
    @GetMapping(value = "${department.find-employee-by-iddepartment}")
    public ResponseEntity<Object> getDepartmentFindEmployee(@RequestParam String idDepartment){
        return departmentService.getDepartmentEmployeesFindByIdDepartment(idDepartment);
    }

}

