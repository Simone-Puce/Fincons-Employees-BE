package com.fincons.service;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProjectService {
    ResponseEntity<ProjectDTO> findById(long id);
    ResponseEntity<List<ProjectDTO>> findAll();
    ResponseEntity<ProjectDTO> save(Project project);
    ResponseEntity<ProjectDTO> update(long id, Project project);
    ResponseEntity<ProjectDTO> deleteById(long id);

}
