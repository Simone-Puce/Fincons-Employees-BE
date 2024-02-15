package com.fincons.repository;

import com.fincons.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProjectRepository extends JpaRepository <Project, Long> {

    Project findProjectByProjectId(String idProject);

    Project findProjectByName(String projectName);
}
