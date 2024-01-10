package com.fincons.service.impl;

import com.fincons.Handler.ResponseHandler;
import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import com.fincons.exception.IllegalArgumentException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.RoleMapper;
import com.fincons.repository.RoleRepository;
import com.fincons.service.RoleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public ResponseEntity<Object> getRoleById(long id) {

        Role existingRole = validateRoleById(id);
        RoleDTO roleDTO = roleMapper.mapRole(existingRole);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found role with ID " + id + ".",
                (HttpStatus.OK),
                roleDTO);
    }

    @Override
    public ResponseEntity<Object> getAllRoles() {
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
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Found " + newListRole.size() +
                        (newListRole.size() == 1 ? " role" : " roles") + " in the list.",
                (HttpStatus.OK),
                newListRole);
    }

    @Override
    public ResponseEntity<Object> createRole(Role role) {
        //Contition for not have null attribute
        validateRoleFields(role);

        List<Role> roles = roleRepository.findAll();
        //Condition if there are roles with name same
        checkForDuplicateRole(role, roles);

        RoleDTO roleDTO = roleMapper.mapRole(role);
        roleRepository.save(role);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Role with ID "+ role.getId() +" has been successfully updated!",
                (HttpStatus.OK), roleDTO);
    }

    @Override
    public ResponseEntity<Object> updateRoleById(long id, Role role) {

        //Condition for not have null attributes
        validateRoleFields(role);

        RoleDTO roleDTO;
        //Check if the specified ID exists
        Role existingRole = validateRoleById(id);

        List<Role> roles = roleRepository.findAll();
        //Condition if there are roles with same name
        checkForDuplicateRole(role, roles);

        existingRole.setName(role.getName());
        existingRole.setSalary(role.getSalary());
        roleRepository.save(existingRole);

        roleDTO = roleMapper.mapRole(role);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Role with ID "+ id +" has been successfully updated!",
                (HttpStatus.OK),
                roleDTO);
    }

    @Override
    public ResponseEntity<Object> deleteRoleById(long id) {
        getRoleById(id);
        roleRepository.deleteById(id);
        return ResponseHandler.generateResponse(LocalDateTime.now(),
                "Success: Role with ID "+ id +" has been successfully deleted!",
                (HttpStatus.OK),
                null);
    }

    private Role validateRoleById(long id) {
        Role existingRole = roleRepository.findById(id);

        if (existingRole == null) {
            throw new ResourceNotFoundException("Role with ID: " + id + " not found");
        }
        return existingRole;
    }

    private void validateRoleFields(Role role){
        //If one field is true run Exception
        if (Strings.isEmpty(role.getName()) ||
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
