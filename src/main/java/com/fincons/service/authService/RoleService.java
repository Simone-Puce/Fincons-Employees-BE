package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import com.fincons.exception.RoleException;
import com.fincons.utility.GenericResponse;

import java.util.List;

public interface RoleService {
    Role getRoleById(long roleId);

    Role createRole(RoleDTO roleDTO) throws  RoleException;

    Role updateRole(long roleId, RoleDTO roleModifiedDTO) throws RoleException;

    String deleteRole(long roleId, boolean deleteUsers) throws RoleException;

    List<Role> findAllRoles();
}
