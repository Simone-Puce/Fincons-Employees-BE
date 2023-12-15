package com.fincons.service.impl;

import com.fincons.entity.Role;
import com.fincons.entity.Role;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.repository.RoleRepository;
import com.fincons.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }


    @Override
    public Role update(long id, Role role) {
        Role existingRole = roleRepository.findById(id);
        if (existingRole == null) {
            throw new ResourceNotFoundException("Dipartimento con ID " + id + " non trovato");
        } else {
            existingRole.setName(role.getName());
            existingRole.setSalary(role.getSalary());
        }
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteById(long id) {
        roleRepository.deleteById(id);
    }
}
