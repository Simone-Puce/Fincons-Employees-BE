package com.fincons.controller;

import com.fincons.entity.Role;
import com.fincons.entity.Role;
import com.fincons.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/find")
    public Role getRoleById(@RequestParam long id){
        return roleService.findById(id);
    }

    @GetMapping(value="/list")
    public List<Role> getAllRoles(){
        return roleService.findAll();
    }
    @PostMapping(value = "/create")
    public Role createRole(@RequestBody Role role){
        return roleService.saveRole(role);
    }

    
    
}
