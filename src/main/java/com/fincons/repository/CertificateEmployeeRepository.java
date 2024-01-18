package com.fincons.repository;

import com.fincons.entity.CertificateEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateEmployeeRepository extends JpaRepository<CertificateEmployee, Long> {
}
