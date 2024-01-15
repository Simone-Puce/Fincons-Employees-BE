package com.fincons.dto;

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

    @JsonIgnore
    private List<User> users;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
