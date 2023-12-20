package com.fincons.mapper;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleDTO mapRole(Role role){
        return new RoleDTO(
                role.getName(),
                role.getSalary());
    }
}
