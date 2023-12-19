package com.fincons.controller;

import com.fincons.dto.DepartmentDTO;
import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.service.DepartmentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/find")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@RequestParam long id){
        return departmentService.findById(id);
    }

    @GetMapping(value="/list")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(){
        return  departmentService.findAll();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody Department department){
        return departmentService.save(department);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@RequestParam long id, @RequestBody Department department){
        return departmentService.update(id, department);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<DepartmentDTO> deleteDepartmentById(@RequestParam long id){
       return departmentService.deleteById(id);
    }

    @GetMapping(value = "/find-employees")
    public ResponseEntity<List<EmployeeDepartmentDTO>> getDepartmentFindEmployee(@RequestParam long idDepartment){
        return departmentService.getDepartmentEmployeesFindByIdDepartment(idDepartment);
    }

}

