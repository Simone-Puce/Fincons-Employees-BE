package com.fincons.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name = "groups")
public class Group {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column (name = "role")
    private String role;
    @Column (name = "description")
    private String description;

    @ManyToMany(mappedBy = "groups",
            fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<User>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

