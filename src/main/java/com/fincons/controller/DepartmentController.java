package com.fincons.controller;


import com.fincons.entity.Department;
import com.fincons.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

/**
 * @RestController viene utilizzato per creare servizi web che restituiscono dati JSON o XML
 */
@RestController
@RequestMapping("${department.uri}")
public class DepartmentController {

    /**
     * Cerca il Bean di tipo DepartmentService e lo innietta nel controller creando un'istanza
     */
    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDepartmentById(@RequestParam long id){
        return departmentService.findById(id);
    }
    @GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllDepartment(){
        return  departmentService.findAll();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createDepartment(@RequestBody Department department){
        return departmentService.save(department);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateDepartmentById(@RequestParam long id, @RequestBody Department department){
        return departmentService.update(id, department);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteDepartmentById(@RequestParam long id){
       return departmentService.deleteById(id);
    }
    @GetMapping(value = "/find-employees")
    public ResponseEntity<Object> getDepartmentFindEmployee(@RequestParam long id){
        return departmentService.getDepartmentEmployeesFindByIdDepartment(id);
    }

}

