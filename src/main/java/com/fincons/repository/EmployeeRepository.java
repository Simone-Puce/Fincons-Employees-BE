package com.fincons.repository;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

    Employee findById(long id);

    @Query("SELECT e.project FROM Employee e WHERE e.id = :employeeId")
    List<Project> findProjectByEmployeeId(long employeeId);

    @Query(
            "SELECT NEW com.fincons.dto.EmployeeProjectDTO(e.lastName, e.id, p.name, p.id)" +
            "FROM Employee e " +
            "INNER JOIN Project p ON e.department.id = p.id")
    List<EmployeeProjectDTO> getAllEmployeeProject();



}
