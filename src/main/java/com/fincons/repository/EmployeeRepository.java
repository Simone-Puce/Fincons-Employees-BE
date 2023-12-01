package com.fincons.repository;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

    Employee findById(long id);
    List<Employee> findByFirstName(String firstName);

    @Query("SELECT e.project FROM Employee e WHERE e.id = :employeeId")
    List<Project> findProjectByEmployeeId(long employeeId);

}
