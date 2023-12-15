package com.fincons.service;

import com.fincons.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {

    Role findById(long id);
    List<Role> findAll();
    Role save(Role role);
    Role update(long id, Role role);
    void deleteById(long id);

}
