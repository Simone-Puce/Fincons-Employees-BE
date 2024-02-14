package com.fincons.repository;

import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findDepartmentByDepartmentCode(String idDepartment);


    @Query(
        "SELECT NEW com.fincons.dto.EmployeeDepartmentDTO(d.name, e.lastName) " +
                "FROM Department d " +
                "INNER JOIN Employee e ON e.department.id = d.id " +
                "WHERE e.department.id = :id")
    List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(@Param("id") Long id);



}
