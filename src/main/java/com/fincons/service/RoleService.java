package com.fincons.service;

import com.fincons.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {

    Role findById(long id);
    List<Role> findAll();
    Role saveRole(Role role);


}
