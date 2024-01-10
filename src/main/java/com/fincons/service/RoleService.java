package com.fincons.service;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {

    ResponseEntity<Object> getRoleById(long id);
    ResponseEntity<Object> getAllRoles();
    ResponseEntity<Object> createRole(Role role);
    ResponseEntity<Object> updateRoleById(long id, Role role);
    ResponseEntity<Object> deleteRoleById(long id);

}
