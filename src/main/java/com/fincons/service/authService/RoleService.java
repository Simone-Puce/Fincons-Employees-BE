package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import com.fincons.exception.RoleDoesNotRespectRegex;
import com.fincons.exception.RoleExistsException;

public interface RoleService {
    RoleDTO getRoleById(long roleId);

    RoleDTO createRole(RoleDTO roleDTO) throws RoleExistsException, RoleDoesNotRespectRegex;

    RoleDTO updateRole(long roleId, RoleDTO roleModifiedDTO) throws RoleExistsException, RoleDoesNotRespectRegex;
}
