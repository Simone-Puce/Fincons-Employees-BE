package com.fincons.service;


import com.fincons.entity.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProjectService {
    ResponseEntity<Object> findById(long id);
    ResponseEntity<Object> findAll();
    ResponseEntity<Object> save(Project project);
    ResponseEntity<Object> update(long id, Project project);
    ResponseEntity<Object> deleteById(long id);

}
