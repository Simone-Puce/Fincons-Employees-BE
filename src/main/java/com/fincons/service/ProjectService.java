package com.fincons.service;

import com.fincons.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProjectService {
    Project findById(long id);

    List<Project> findAll();

    Project saveProject(Project project);

}
