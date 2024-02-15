package com.fincons.repository;

import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.dto.EmployeeProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


public interface EmployeeRepository extends JpaRepository <Employee, Long> {

    Employee findEmployeeBySsn(String ssn);
    Employee findByEmail(String email);

    @Query("SELECT e.projects FROM Employee e WHERE e.ssn = :ssn")
    List<Project> findProjectsByEmployeeSsn(String ssn);


    @Query(
            "SELECT NEW com.fincons.dto.EmployeeProjectDTO(e.lastName, e.ssn, p.name, p.projectId)" +
                    "FROM Employee e " +
                    "JOIN e.projects p"
    )
    List<EmployeeProjectDTO> getAllEmployeeProject();

    @Query("SELECT e FROM Employee e WHERE DAY(e.startDate) = DAY(CURRENT_DATE) AND MONTH(e.startDate) = MONTH(CURRENT_DATE)")
    List<Employee> findEmployeesByTodayStartDate(@Param("startDate") LocalDate startDate);


    @Query("SELECT e FROM Employee e WHERE DAY(e.birthDate) = DAY(CURRENT_DATE) AND MONTH(e.birthDate) = MONTH(CURRENT_DATE)")
    List<Employee> findEmployeesByTodayBirthday(@Param("birthDate") LocalDate birthDate);

    boolean  existsByEmail(String emailId);

    boolean existsBySsn(String ssn);

}
