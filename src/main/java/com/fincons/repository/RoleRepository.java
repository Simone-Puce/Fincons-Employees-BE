package com.fincons.repository;

import com.fincons.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Role findByName(String roleUser);


    boolean existsByName(String name);


}
