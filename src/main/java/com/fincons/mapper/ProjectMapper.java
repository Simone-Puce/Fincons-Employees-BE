package com.fincons.mapper;

import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectDTO mapProject(Project project){
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getArea(),
                project.getPriority(),
                project.getEmployees());
    }

    public ProjectDTO mapProjectWithoutEmployees(Project project){
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getArea(),
                project.getPriority());
    }

}
