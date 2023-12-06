package com.fincons.controller;

import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import com.fincons.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController viene utilizzato per creare servizi web che restituiscono dati JSON o XML
 */
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    /**
     * Cerca il Bean di tipo DepartmentService e lo innietta nel controller creando un'istanza
     */
    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "/find")
    public Department getDepartmentById(@RequestParam long id){
        return departmentService.findById(id);
    }

    @GetMapping(value="/list")
    public List<Department> getAllDepartment(){
        return  departmentService.findAll();
    }
    @PostMapping(value = "/create")
    public Department createDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }
    @GetMapping(value = "/find-employees")
    public List<EmployeeDepartmentDTO> getDepartmentFindEmployee(@RequestParam long idDepartment){
        return departmentService.getDepartmentEmployeesFindByIdDepartment(idDepartment);
    }





}

