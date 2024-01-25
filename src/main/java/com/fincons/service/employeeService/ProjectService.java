package com.fincons.service.employeeService;


import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProjectService {
    ResponseEntity<Object> getProjectById(String idProject);
    ResponseEntity<Object> getAllProjects();
    ResponseEntity<Object> createProject(ProjectDTO projectDTO);
    ResponseEntity<Object> updateProjectById(String idProject, ProjectDTO projectDTO);
    ResponseEntity<Object> deleteProjectById(String idProject);

}
