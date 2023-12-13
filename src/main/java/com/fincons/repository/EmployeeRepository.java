package com.fincons.repository;

import com.fincons.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE DAY(e.hireDate) = DAY(:hireDate) AND MONTH(e.hireDate) = MONTH(:hireDate)")
    List<Employee> findEmployeesByTodayHireDate(@Param("hireDate") LocalDate hireDate);


    @Query("SELECT e FROM Employee e WHERE DAY(e.birthDate) = DAY(:birthDate) AND MONTH(e.birthDate) = MONTH(:birthDate)")
    List<Employee> findEmployeesByTodayBirthday(@Param("birthDate") LocalDate birthDate);

}

