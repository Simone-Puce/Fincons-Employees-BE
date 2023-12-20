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
    public ResponseEntity<RoleDTO> getRoleById(@RequestParam long id){
        return roleService.findById(id);
    }
    @GetMapping(value="/list")
    public ResponseEntity<List<RoleDTO>> getAllRoles(){
        return roleService.findAll();
    }
    @PostMapping(value = "/create")
    public ResponseEntity<RoleDTO> createRole(@RequestBody Role role){
        return roleService.save(role);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<RoleDTO> updateRoleById(@RequestParam long id, @RequestBody Role role){
        return roleService.update(id, role);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<RoleDTO> deleteRoleById(@RequestParam long id){
        return roleService.deleteById(id);
    }

    
    
}
