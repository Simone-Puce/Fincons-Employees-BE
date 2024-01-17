package com.fincons.service.employeeService;


import com.fincons.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProjectService {
    ResponseEntity<Object> getProjectById(long id);
    ResponseEntity<Object> getAllProjects();
    ResponseEntity<Object> createProject(Project project);
    ResponseEntity<Object> updateProjectById(long id, Project project) throws Exception;
    ResponseEntity<Object> deleteProjectById(long id);

}
