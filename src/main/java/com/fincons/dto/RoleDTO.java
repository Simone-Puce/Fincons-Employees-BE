package com.fincons.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fincons.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private long id;

    private String name;

    private List<UserDTO> users;


}
