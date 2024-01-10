package com.fincons.controller;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import com.fincons.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${role.uri}")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/find")
    public ResponseEntity<Object> getRoleById(@RequestParam long id){
        return roleService.findById(id);
    }
    @GetMapping(value="/list")
    public ResponseEntity<Object> getAllRoles(){
        return roleService.findAll();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createRole(@RequestBody Role role){
        return roleService.save(role);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateRoleById(@RequestParam long id, @RequestBody Role role){
        return roleService.update(id, role);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteRoleById(@RequestParam long id){
        return roleService.deleteById(id);
    }

    
    
}
