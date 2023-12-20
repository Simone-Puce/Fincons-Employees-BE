package com.fincons.service.impl;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.RoleMapper;
import com.fincons.repository.RoleRepository;
import com.fincons.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseEntity<RoleDTO> findById(long id) {

        Role existingRole = getRoleById(id);
        RoleDTO roleDTO = roleMapper.mapRole(existingRole);
        return ResponseEntity.ok(roleDTO);
    }

    @Override
    public ResponseEntity<List<RoleDTO>> findAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> newListRole = new ArrayList<>();
        //Check if the list of department is empty
        for (Role role : roles){
            if(role != null){
                RoleDTO roleDTO = roleMapper.mapRole(role);
                newListRole.add(roleDTO);
            } else{
                throw new IllegalArgumentException("There aren't Roles");
            }
        }
        return ResponseEntity.ok(newListRole);
    }

    @Override
    public ResponseEntity<RoleDTO> save(Role role) {
        //Contition for not have null attribute
        validateRoleFields(role);

        List<Role> roles = roleRepository.findAll();
        //Condition if there are roles with name same
        checkForDuplicateRole(role, roles);

        RoleDTO roleDTO = roleMapper.mapRole(role);
        roleRepository.save(role);
        return ResponseEntity.ok(roleDTO);
    }

    @Override
    public ResponseEntity<RoleDTO> update(long id, Role role) {

        RoleDTO roleDTO;
        //Condition for not have null attributes
        validateRoleFields(role);

        //Launch exception if not exist
        Role existingRole = getRoleById(id);

        existingRole.setName(role.getName());
        existingRole.setSalary(role.getSalary());
        roleRepository.save(existingRole);

        roleDTO = roleMapper.mapRole(role);
        return ResponseEntity.ok(roleDTO);
    }

    @Override
    public ResponseEntity<RoleDTO> deleteById(long id) {
        getRoleById(id);
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Role getRoleById(long id) {
        Role existingRole = roleRepository.findById(id);

        if (existingRole == null) {
            throw new ResourceNotFoundException("Role with ID: " + id + " not found");
        }
        return existingRole;
    }

    private void validateRoleFields(Role role){
        //If one field is true run Exception
        if(role.getName() == null || role.getName().isEmpty() ||
                Objects.isNull(role.getSalary())) {
            throw new IllegalArgumentException("The fields of the Role can't be null or empty");
        }
    }

    private void checkForDuplicateRole(Role role, List<Role>roles){
        for(Role role1 : roles){
            if(role1.getName().equals(role.getName())){
                throw new IllegalArgumentException("The names can't be the same");
            }
        }
    }
}
