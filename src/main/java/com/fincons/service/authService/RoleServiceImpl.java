package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.entity.Role;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleDoesNotRespectRegex;
import com.fincons.exception.RoleExistsException;
import com.fincons.mapper.UserAndRoleMapper;
import com.fincons.repository.RoleRepository;
import com.fincons.utility.PasswordValidator;
import com.fincons.utility.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
     private RoleRepository roleRepository;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;


    @Override
    public RoleDTO getRoleById(long roleId) {

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty()){
            throw new ResourceNotFoundException("Role does not present");
        }
        Role optionalRoleToRole = optionalRole.get();
        optionalRoleToRole.setName(optionalRole.get().getName());
        optionalRoleToRole.setId(optionalRole.get().getId());
        optionalRoleToRole.setUsers(optionalRole.get().getUsers());

        return userAndRoleMapper.roleToRoleDto(optionalRoleToRole);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) throws RoleExistsException, RoleDoesNotRespectRegex {

        // See if matches with regex
        if(!RoleValidator.isValidRole(roleDTO.getName().toUpperCase())){
            throw new RoleDoesNotRespectRegex("Role does not respect regex of 'ROLE_*****'");
        }
        Role roleExist = roleRepository.findByName(roleDTO.getName().toUpperCase());

        if(roleExist != null){
            throw new RoleExistsException("Role exist yet");
        }
        //I set name to Upper case
        roleDTO.setName(roleDTO.getName().toUpperCase());

        Role newRoleSaved = roleRepository.save(userAndRoleMapper.dtoToRole(roleDTO));

        return userAndRoleMapper.roleToRoleDto(newRoleSaved);
    }

    @Override
    public RoleDTO updateRole(long roleId, RoleDTO roleModifiedDTO) throws RoleExistsException, RoleDoesNotRespectRegex {

        String roleDTONameToConfront = roleModifiedDTO.getName().toUpperCase();

        if(!RoleValidator.isValidRole(roleDTONameToConfront)){
            throw new RoleDoesNotRespectRegex("Role does not respect regex");
        }
        Optional<Role> roleFound = roleRepository.findById(roleId);

        if(roleFound.isEmpty()){
            throw new ResourceNotFoundException("Role does not Exist");
        }

        Role roleToModify = roleFound.get();

        roleToModify.setName(roleModifiedDTO.getName().toUpperCase());
        Role roleModifiedSaved =  roleRepository.save(roleToModify);

        return userAndRoleMapper.roleToRoleDto(roleModifiedSaved);
    }





}
