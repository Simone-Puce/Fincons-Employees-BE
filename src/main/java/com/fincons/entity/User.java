package com.fincons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL) // Performance
    @JoinTable(
            name = "users_roles", // nome tabella join che avrà id di user e is di role
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id" ), // user id fa riferimento al primo della relaizione   user_role  <- a sinistra vi è role quindi mettiamo user_id
            inverseJoinColumns = @JoinColumn(name = "role_id",  referencedColumnName = "id" )   // a destra vi è role quindi dall'altra parte(l'inversa)  quuindi inversejoincolumns role_id
    )
    private List<Role> roles;


}
