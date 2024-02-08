package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleException;
import com.fincons.mapper.UserAndRoleMapper;
import com.fincons.repository.RoleRepository;
import com.fincons.repository.UserRepository;
import com.fincons.utility.GenericResponse;
import com.fincons.utility.ReturnObject;
import com.fincons.utility.RoleValidator;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
     private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public Role getRoleById(long roleId) {

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty()){
            throw new ResourceNotFoundException("Role does not present");
        }
        Role optionalRoleToRole = optionalRole.get();
        optionalRoleToRole.setName(optionalRole.get().getName());
        optionalRoleToRole.setId(optionalRole.get().getId());
        optionalRoleToRole.setUsers(optionalRole.get().getUsers());

        return optionalRoleToRole;
    }

    @Override
    public Role createRole(RoleDTO roleDTO) throws RoleException {

        // See if matches with regex
        if(!RoleValidator.isValidRole(roleDTO.getName().toUpperCase())){
            throw new RoleException(RoleException.roleDoesNotRespectRegex());
        }
        Role roleExist = roleRepository.findByName(roleDTO.getName().toUpperCase());

        if(roleExist != null){
            throw new RoleException(RoleException.roleExistException());
        }
        //I set name to Uppercase
        roleDTO.setName(roleDTO.getName().toUpperCase());

        return roleRepository.save(userAndRoleMapper.dtoToRole(roleDTO));
    }

    @Override
    public Role updateRole(long roleId, RoleDTO roleModifiedDTO) throws  RoleException {

        String roleDTONameToConfront = roleModifiedDTO.getName().toUpperCase();

        if(!RoleValidator.isValidRole(roleDTONameToConfront)){
            throw new RoleException(RoleException.roleDoesNotRespectRegex());
        }
        Optional<Role> roleFound = roleRepository.findById(roleId);

        if(roleFound.isEmpty()){
            throw new ResourceNotFoundException("Role does not Exist");
        }

        if(roleFound.get().getName().equals("ROLE_ADMIN")){
            throw new RoleException("Can't modify a ROLE_ADMIN");
        }

        Role roleToModify = roleFound.get();

        roleToModify.setName(roleModifiedDTO.getName().toUpperCase());

        return roleRepository.save(roleToModify);
    }

    @Override
    public GenericResponse<String> deleteRole(long roleId, boolean deleteUsersAssociated) throws RoleException {  //  if Boolean deleteUsersAssociated == null -- > nullPointerExepciont

        // se non presente genera eccezione
        Optional<Role> roleToDelete = Optional.ofNullable(roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role does not Exist")));

        if (roleToDelete.isPresent() && !roleToDelete.get().getName().equals("ROLE_ADMIN"))  {
            if (roleToDelete.get().getUsers().isEmpty()) {
                roleRepository.deleteById(roleId);
                return new GenericResponse<>(HttpStatus.OK,true,"Role deleted successfully!");

            } else {
                if (deleteUsersAssociated) {
                    roleToDelete.get().removeUsersAssociations();
                    roleToDelete.get().getUsers()
                            .forEach(user -> userRepository.delete(user));
                    roleRepository.deleteById(roleToDelete.get().getId());
                    return new GenericResponse<>(HttpStatus.OK,true,"Role and users associated, deleted!");
                } else {
                    List<Long> idOfUsersToModifyRole = roleToDelete.get()
                            .getUsers()
                            .stream()
                            .map(User::getId)
                            .toList();

                    throw new RoleException("You have to change before the role's name of these users: List of id of users : " + idOfUsersToModifyRole);
                }
            }
        }else{
            throw new RoleException("Can't delete a ROLE_ADMIN");
        }

    }


}
