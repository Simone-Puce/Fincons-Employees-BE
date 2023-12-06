package com.fincons.dto;

public class EmployeeProjectDTO {

    private String lastName;
    private Long idEmployee;
    private String nameProject;
    private Long idProject;

    public EmployeeProjectDTO(String lastName, Long idEmployee, String nameProject, Long idProject) {
        this.lastName = lastName;
        this.idEmployee = idEmployee;
        this.nameProject = nameProject;
        this.idProject = idProject;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }
}
