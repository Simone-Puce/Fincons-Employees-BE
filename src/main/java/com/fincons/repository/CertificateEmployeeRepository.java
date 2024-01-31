package com.fincons.repository;

import com.fincons.entity.CertificateEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CertificateEmployeeRepository extends JpaRepository<CertificateEmployee, Long> {

    //@Query("SELECT ce FROM CertificateEmployee ce WHERE MONTH(ce.achieved) = MONTH(CURRENT_DATE())  ORDER BY ce.achieved ASC")
    @Query("SELECT ce FROM CertificateEmployee ce WHERE ce.achieved BETWEEN ?1 AND ?2")
    List<CertificateEmployee> listCertificateEmployeeByDateRange(LocalDate dateFrom, LocalDate dateTo);
}
