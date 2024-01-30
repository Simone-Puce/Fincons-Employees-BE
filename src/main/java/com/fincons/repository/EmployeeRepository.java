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

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

    Employee findEmployeeByEmployeeId(String idEmployee);
    Employee findByEmail(String email);

    @Query("SELECT e.projects FROM Employee e WHERE e.employeeId = :employeeId")
    List<Project> findProjectByEmployeeId(String employeeId);

    @Query(
            "SELECT NEW com.fincons.dto.EmployeeProjectDTO(e.lastName, e.employeeId, p.name, p.projectId)" +
                    "FROM Employee e " +
                    "JOIN e.projects p"
    )
    List<EmployeeProjectDTO> getAllEmployeeProject();

    @Query("SELECT e FROM Employee e WHERE DAY(e.startDate) = DAY(:startDate) AND MONTH(e.startDate) = MONTH(:startDate)")
    List<Employee> findEmployeesByTodayStartDate(@Param("startDate") LocalDate startDate);


    @Query("SELECT e FROM Employee e WHERE DAY(e.birthDate) = DAY(:birthDate) AND MONTH(e.birthDate) = MONTH(:birthDate)")
    List<Employee> findEmployeesByTodayBirthday(@Param("birthDate") LocalDate birthDate);



}
