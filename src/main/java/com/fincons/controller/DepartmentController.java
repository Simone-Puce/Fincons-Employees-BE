package com.fincons.controller;

import com.fincons.entity.Department;
import com.fincons.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

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




}
