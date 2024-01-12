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

    Employee findById(long id);

    Employee findByEmail(String email);

    @Query("SELECT e.projects FROM Employee e WHERE e.id = :employeeId")
    List<Project> findProjectByEmployeeId(long employeeId);

    @Query(
            "SELECT NEW com.fincons.dto.EmployeeProjectDTO(e.lastName, e.id, p.name, p.id)" +
                    "FROM Employee e " +
                    "JOIN e.projects p"
    )
    List<EmployeeProjectDTO> getAllEmployeeProject();

    @Query("SELECT e FROM Employee e WHERE DAY(e.startDate) = DAY(:startDate) AND MONTH(e.startDate) = MONTH(:startDate)")
    List<Employee> findEmployeesByTodayStartDate(@Param("startDate") LocalDate startDate);


    @Query("SELECT e FROM Employee e WHERE DAY(e.birthDate) = DAY(:birthDate) AND MONTH(e.birthDate) = MONTH(:birthDate)")
    List<Employee> findEmployeesByTodayBirthday(@Param("birthDate") LocalDate birthDate);


}
