package com.fincons.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupsDTO {
    private Long id;
    private String role;
    private String admin;
    @JsonCreator
    public GroupsDTO (@JsonProperty ("role") String role, @JsonProperty ("admin") String admin){
        this.id = id;
        this.role = role;
        this.admin = admin;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
    @Override
    public String toString() {
        return "GroupsDTO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
