package com.fincons.repository;

import com.fincons.entity.Department;
import com.fincons.entity.dto.DepartmentEmployeesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findById(long id);

    //@Query ("SELECT d.name, d.address, e.lastName FROM Department d INNER JOIN d.employee e WHERE d.id = :id")

/*
    @Query(value =
            "SELECT department.name, employee.last_name " +
            "FROM employee " +
            "INNER JOIN department ON employee.id_department = department.id " +
                    "WHERE employee.id_department = :idDepartment", nativeQuery = true)
    List<DepartmentWithEmployeesDetails> getDepartmentFindEmployees(@Param("idDepartment") long idDepartment);
*/


//Quando usi NEW nella query JPA, stai dicendo al sistema di creare oggetti direttamente dalla tupla restituita, piuttosto che restituire una lista di tuple.
    @Query(
        "SELECT NEW com.fincons.entity.dto.DepartmentEmployeesDTO(d.name, e.lastName) " +
                "FROM Department d " +
                "INNER JOIN Employee e ON e.department.id = d.id " +
                "WHERE e.department.id = :idDepartment")
    List<DepartmentEmployeesDTO> getDepartmentEmployeesFindByIdDepartment(@Param("idDepartment") long idDepartment);


    //Commentare il codice
}
