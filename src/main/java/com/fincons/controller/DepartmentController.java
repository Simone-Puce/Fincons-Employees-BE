package com.fincons.controller;


import com.fincons.entity.Department;
import com.fincons.service.employeeService.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @RestController viene utilizzato per creare servizi web che restituiscono dati JSON o XML
 */
@RestController
@RequestMapping("/company-employee-management")
public class DepartmentController {

    /**
     * Cerca il Bean di tipo DepartmentService e lo innietta nel controller creando un'istanza
     */
    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "${department.find-by-id}")
    public ResponseEntity<Object> getDepartmentById(@RequestParam long id){
        return departmentService.getDepartmentById(id);
    }
    @GetMapping(value="${department.list}")
    public ResponseEntity<Object> getAllDepartment(){
        return  departmentService.getAllDepartment();
    }
    @PostMapping(value = "${department.create}")
    public ResponseEntity<Object> createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }
    @PutMapping(value = "${department.update}")
    public ResponseEntity<Object> updateDepartmentById(@RequestParam long id, @RequestBody Department department) throws Exception {
        return departmentService.updateDepartmentById(id, department);
    }
    @DeleteMapping(value = "${department.delete}")
    public ResponseEntity<Object> deleteDepartmentById(@RequestParam long id){
       return departmentService.deleteDepartmentById(id);
    }
    @GetMapping(value = "${department.find-employee-by-iddepartment}")
    public ResponseEntity<Object> getDepartmentFindEmployee(@RequestParam long id){
        return departmentService.getDepartmentEmployeesFindByIdDepartment(id);
    }

}

