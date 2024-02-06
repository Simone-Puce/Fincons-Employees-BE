package com.fincons.controller;


import com.fincons.dto.RoleDTO;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleException;
import com.fincons.service.authService.RoleService;
import com.fincons.utility.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/company-employee-management")
public class RoleController {


    @Autowired
    private RoleService roleService;


    // /company-employee-management/v1/role/find-by-id
    @GetMapping("${role.find-by-id}/{roleId}")  //  /v1/role/find-by-id   application.properties role.find-by-id.uri=("${role.uri}/find-by-id")    role.uri = {base.version.uri}/role    ApplicationUri -> roleFindByIdUri
    public ResponseEntity<GenericResponse<RoleDTO>> getRoleById(@PathVariable long roleId) {
        try{
            RoleDTO roleDTO = roleService.getRoleById(roleId);

            return ResponseEntity.ok(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Role found Succesfully  !!!")
                    .data(roleDTO)
                    .build());
        }catch(ResourceNotFoundException rnfe){
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.resolve(404))
                    .success(false)
                    .message(rnfe.getMessage())
                    .build());
        }

    }

    @PostMapping("${role.create}")
    public ResponseEntity<GenericResponse<RoleDTO>> createRole(@RequestBody RoleDTO roleDTO) {
        try{
            RoleDTO newRole = roleService.createRole(roleDTO);
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Role created Succesfully!!!")
                    .data(newRole)
                    .build());
        }catch(RoleException re ){
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(re.getMessage())
                    .build());
        }
    }

    @PutMapping("${role.put}/{roleId}")
    /*
    I'm going to testing when I change roleName of ROLE_ADMIN then I navigate on another endPoint also if I use the Bearer token,
    I will not have permission anymore.
    Okay Tested it work, or better, it's right if I change roleName the permission change, so I'll not be able to navigate,
    So if it happens you need to register new admin, after you can go to modify again a roleName.
     How to manage this error "Duplicate entry 'secondoAdmin@gmail.com' for key 'users.UK_6dotkott2kjsp8vw4d0m25fb7'"
     */
    // TODO modify permission an admin can modify a roleName but can't modify a ROLE_ADMIN
    public ResponseEntity<GenericResponse<RoleDTO>> updateRole(@PathVariable long roleId, @RequestBody RoleDTO roleModifiedDTO) {
        try{
            RoleDTO newRoleModifiedTO = roleService.updateRole(roleId,roleModifiedDTO);
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Role updated Succesfully!!!")
                    .data(newRoleModifiedTO)
                    .build());
        }catch(RoleException re){
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(re.getMessage())
                    .build());
        }
    }

    // delete.role.uri = ${role.uri}/delete
    //  Role Service works when a role has no relationship (Example, I deleted role_manager, it wasn't assigned to no-one user)
    // i test now to delete a role guest, it has relationship with an user / ok doesn't work
    @DeleteMapping("${delete.role}/{roleId}")
    public ResponseEntity<GenericResponse<String>> deleteRole(
            @PathVariable long roleId,
            @RequestParam (name = "deleteUsers" , required = false) Boolean deleteUsersAnyway) {

        try{

            String roleDeleted = roleService.deleteRole(roleId,deleteUsersAnyway);

            return ResponseEntity.status(200).body(
                    GenericResponse.<String>builder()
                            .status(HttpStatus.OK)
                            .success(true)
                            .message("Role deleted successfully")
                            .data(roleDeleted)
                            .build()
            );

        } catch(RoleException | ResourceNotFoundException ree){
            return ResponseEntity.status(200).body(
                    GenericResponse.<String>builder()
                            .status(HttpStatus.OK)
                            .success(true)
                            .message(ree.getMessage())
                            .build()
            );
        }
    }







}
