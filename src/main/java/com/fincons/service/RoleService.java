package com.fincons.service;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {

    ResponseEntity<RoleDTO> findById(long id);
    ResponseEntity<List<RoleDTO>> findAll();
    ResponseEntity<RoleDTO> save(Role role);
    ResponseEntity<RoleDTO> update(long id, Role role);
    ResponseEntity<RoleDTO> deleteById(long id);

}
