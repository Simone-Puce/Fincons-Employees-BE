package com.fincons.repository;

import com.fincons.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface EmployeeProjectRepository {

/*
    @Query("SELECT e.project FROM Employee e WHERE e.id = :employeeId")
    Set<Project> findProjectsByEmployeeId(@Param("employeeId") Long employeeId);
*/
}
