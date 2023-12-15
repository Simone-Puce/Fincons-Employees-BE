package com.fincons.controller;

import com.fincons.entity.Project;
import com.fincons.entity.Role;
import com.fincons.entity.Role;
import com.fincons.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${role.uri}")
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
        return roleService.save(role);
    }
    @PutMapping(value = "/update")
    public Role updateRoleById(@RequestParam long id, @RequestBody Role role){
        return roleService.update(id, role);
    }

    @DeleteMapping(value = "/delete")
    public void deleteRoleById(@RequestParam long id){
        roleService.deleteById(id);
    }

    
    
}
