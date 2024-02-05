package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.exception.RoleException;

public interface RoleService {
    RoleDTO getRoleById(long roleId);

    RoleDTO createRole(RoleDTO roleDTO) throws  RoleException;

    RoleDTO updateRole(long roleId, RoleDTO roleModifiedDTO) throws RoleException;


}
