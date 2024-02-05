package com.fincons.controller;


import com.fincons.dto.RoleDTO;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleDoesNotRespectRegex;
import com.fincons.exception.RoleExistsException;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/company-employee-management")
public class RoleController {


    @Autowired
    private RoleService roleService;


    // /company-employee-management/v1/role/find-by-id
    @GetMapping("${role.find-by-id.uri}/{roleId}")  //  /v1/role/find-by-id   application.properties role.find-by-id.uri=("${role.uri}/find-by-id")    role.uri = {base.version.uri}/role    ApplicationUri -> roleFindByIdUri
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

    @PostMapping("${role.create.uri}")
    public ResponseEntity<GenericResponse<RoleDTO>> createRole(@RequestBody RoleDTO roleDTO) {
        try{
            RoleDTO newRole = roleService.createRole(roleDTO);
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Role created Succesfully!!!")
                    .data(newRole)
                    .build());
        }catch(RoleExistsException ree ){
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(ree.getMessage())
                    .build());
        } catch (RoleDoesNotRespectRegex rdnrr) {
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(rdnrr.getMessage())
                    .build());
        }
    }

    @PutMapping("${role.put.uri}/{roleId}")
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
                    .message("Role created Succesfully!!!")
                    .data(newRoleModifiedTO)
                    .build());
        }catch(RoleExistsException  | RoleDoesNotRespectRegex r){
            return ResponseEntity.status(200).body(GenericResponse.<RoleDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(r.getMessage())
                    .build());
        }
    }




}
