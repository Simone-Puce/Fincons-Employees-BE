package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.exception.RoleException;
import com.fincons.utility.GenericResponse;
import com.fincons.utility.ReturnObject;

public interface RoleService {
    RoleDTO getRoleById(long roleId);

    RoleDTO createRole(RoleDTO roleDTO) throws  RoleException;

    RoleDTO updateRole(long roleId, RoleDTO roleModifiedDTO) throws RoleException;


    GenericResponse<ReturnObject> deleteRole(long roleId, boolean deleteUsers) throws RoleException;
}
