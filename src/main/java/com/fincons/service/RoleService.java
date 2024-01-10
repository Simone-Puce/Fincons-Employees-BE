package com.fincons.service;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {

    ResponseEntity<Object> findById(long id);
    ResponseEntity<Object> findAll();
    ResponseEntity<Object> save(Role role);
    ResponseEntity<Object> update(long id, Role role);
    ResponseEntity<Object> deleteById(long id);

}
