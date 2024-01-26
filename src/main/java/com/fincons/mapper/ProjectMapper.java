package com.fincons.mapper;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.dto.ProjectDTO;
import com.fincons.entity.Employee;
import com.fincons.entity.Project;
import com.fincons.entity.Project;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {


    private final ModelMapper modelMapperProject;

    public ProjectMapper(ModelMapper modelMapperProject) {
        this.modelMapperProject = modelMapperProject;
        modelMapperProject.addMappings(new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                skip(destination.getProjects());

            }
        });
    }
    public ProjectDTO mapToDTO(Project project) {

        ProjectDTO projectDTO = modelMapperProject.map(project, ProjectDTO.class);

        return projectDTO;
    }

    public Project mapToEntity(ProjectDTO projectDTO){

        Project project = modelMapperProject.map(projectDTO, Project.class);

        return project;
    }

}
