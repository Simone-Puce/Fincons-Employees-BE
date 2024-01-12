package com.fincons.repository;

import com.fincons.entity.Department;
import com.fincons.dto.EmployeeDepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findById(long id);



    /**
     * Quando usi NEW nella query JPA, stai dicendo al sistema di creare oggetti direttamente dalla tupla restituita, piuttosto che restituire una lista di tuple.
     * I parametri che passo nel costruttore del DTO sono esattamente con l'ordine della select
     */

    @Query(
        "SELECT NEW com.fincons.dto.EmployeeDepartmentDTO(d.name, e.lastName) " +
                "FROM Department d " +
                "INNER JOIN Employee e ON e.department.id = d.id " +
                "WHERE e.department.id = :id")
    List<EmployeeDepartmentDTO> getDepartmentEmployeesFindByIdDepartment(@Param("id") long id);



}