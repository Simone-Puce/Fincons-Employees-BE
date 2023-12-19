package com.fincons.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table (name = "users")
public class Users {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "first_name")
    private String first_name;
    @Column (name = "last_name")
    private String last_name;
    @Column (name = "email")
    private String email;
    @Column (name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable (
            name = "users_groups",
            joinColumns = { @JoinColumn(name = "users_id", referencedColumnName = "id",table = "users")},
            inverseJoinColumns = { @JoinColumn(name = "groups_id", referencedColumnName = "id", table = "groups")}
    )
    private List<Groups> groups = new ArrayList<Groups>() ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {

        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {

        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
