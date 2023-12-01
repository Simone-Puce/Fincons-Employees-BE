package com.fincons.repository;

import com.fincons.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProjectRepository extends JpaRepository <Project, Long> {

    Project findById(long id);


}
