package com.fincons.repository;

import com.fincons.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    @Query("SELECT c FROM Certificate c WHERE c.activate = true ")
    List<Certificate> certificateListTrue();
}
