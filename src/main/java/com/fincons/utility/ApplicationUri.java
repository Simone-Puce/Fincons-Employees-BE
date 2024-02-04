package com.fincons.utility;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationUri {

    // TODO Script for transform every UPPER CASE to CamelCase in python/Java
    // APPLICATION CONTEXT
    @Value("${application.context}")
    private String appContext;

    // LOGIN/REGISTER END POINTS
    @Value("${base.uri.version}")
    private String baseUriVersion;

    @Value("${register.uri}")
    private String registerUri;

    @Value("${login.uri}")
    private String loginUri;

    @Value("${admin.uri}")
    private String adminUri;

    @Value("${delete.user-by-email}")
    private String deleteUserById;

    @Value("${user.uri}")
    private String userUri;

    @Value("${employees.uri}")
    private String employeesUri;

    @Value("${error.uri}")
    private String errorUri;

    @Value("${logout.uri}")
    private String logoutUri;

    @Value("${registered.users.uri}")
    private String registeredUsersUri;

    @Value("${detail.userdto.uri}")
    private String detailUserDtoUri;

    @Value("${modify.user}")
    private String modifyUserUri;

    @Value("${update.user.password.uri}")
    private String updateUserPasswordUri;



    //ROLE
    @Value("${role.uri}")
    private String roleUri;

    @Value("${role.find-by-id.uri}")
    private String roleFindByIdUri;

    @Value("${role.create.uri}")
    private String roleCreateUri;

    @Value("${role.put.uri}")
    private String rolePutUri;

    // DEPARTMENT
    @Value("${department.uri}")
    private String departmentUri;

    @Value("${department.find-by-id}")
    private String departmentFindByIdUri;

    @Value("${department.list}")
    private String departmentListUri;

    @Value("${department.create}")
    private String departmentCreateUri;

    @Value("${department.update}")
    private String departmentUpdateUri;

    @Value("${department.delete}")
    private String departmentDeleteUri;

    @Value("${department.find-employee-by-iddepartment}")
    private String departmentFindEmployeeByIdDepartmentUri;

    // POSITION
    @Value("${position.uri}")
    private String positionUri;

    @Value("${position.find-position-by-id}")
    private String positionFindByIdUri;

    @Value("${position.list}")
    private String positionListUri;

    @Value("${position.create}")
    private String positionCreateUri;

    @Value("${position.update}")
    private String positionUpdateUri;

    @Value("${position.delete}")
    private String positionDeleteUri;

    // PROJECT
    @Value("${project.uri}")
    private String projectUri;

    @Value("${project.find-project-by-id}")
    private String projectFindByIdUri;

    @Value("${project.list}")
    private String projectListUri;

    @Value("${project.create}")
    private String projectCreateUri;

    @Value("${project.update}")
    private String projectUpdateUri;

    @Value("${project.delete}")
    private String projectDeleteUri;

    // EMPLOYEE
    @Value("${employee.uri}")
    private String employeeUri;

    @Value("${employee.find-project-by-id}")
    private String employeeFindProjectByIdUri;

    @Value("${employee.find-project-by-email}")
    private String employeeFindProjectByEmailUri;

    @Value("${employee.list}")
    private String employeeListUri;

    @Value("${employee.create}")
    private String employeeCreateUri;

    @Value("${employee.update}")
    private String employeeUpdateUri;

    @Value("${employee.delete}")
    private String employeeDeleteUri;

    @Value("${employee.find-employee-project}")
    private String employeeFindEmployeeProjectUri;

    @Value("${employee.list-employee-project}")
    private String employeeListEmployeeProjectUri;

    @Value("${employee.create-employee-project}")
    private String employeeCreateEmployeeProjectUri;

    @Value("${employee.update-employee-project}")
    private String employeeUpdateEmployeeProjectUri;

    @Value("${employee.delete-employee-project}")
    private String employeeDeleteEmployeeProjectUri;

    @Value("${employee.importfile}")
    private String employeeImportFileUri;

    // FILE CONTROLLER END POINTS
    @Value("${file.uri}")
    private String fileUri;

    @Value("${file.list.uri}")
    private String fileListUri;

    @Value("${file.upload.uri}")
    private String fileUploadUri;

    @Value("${file.view.uri}")
    private String fileViewUri;

    @Value("${file.download.uri}")
    private String fileDownloadUri;

    @Value("${file.delete.uri}")
    private String fileDeleteUri;

    @Value("${file.import.uri}")
    private String fileImportUri;

    // EMAIL CONTROLLER
    @Value("${email.sender.uri}")
    private String emailSenderUri;

    @Value("${email.sender.birth.uri}")
    private String emailSenderBirthUri;

    @Value("${email.sender.hire.uri}")
    private String emailSenderHireUri;

    @Value("${new.employee.random.uri}")
    private String newEmployeeRandomUri;

    // CERTIFICATE CONTROLLER END POINTS
    @Value("${certificate.uri}")
    private String certificateUri;

    @Value("${certificate.list.uri}")
    private String certificateListUri;

    @Value("${certificate.add.uri}")
    private String certificateAddUri;

    @Value("${certificate.update.uri}")
    private String certificateUpdateUri;

    @Value("${certificate.find-by-id.uri}")
    private String certificateFindByIdUri;

    @Value("${certificate.delete.uri}")
    private String certificateDeleteUri;

    // CERTIFICATE EMPLOYEE CONTROLLER END POINTS
    @Value("${certificate-employee.uri}")
    private String certificateEmployeeUri;

    @Value("${certificate-employee.list.uri}")
    private String certificateEmployeeListUri;

    @Value("${certificate-employee.add.uri}")
    private String certificateEmployeeAddUri;

    @Value("${certificate-employee.find-by-id.uri}")
    private String certificateEmployeeFindByIdUri;

    @Value("${certificate-employee.update.uri}")
    private String certificateEmployeeUpdateUri;

    @Value("${certificate-employee.delete.uri}")
    private String certificateEmployeeDeleteUri;

    @Value("${certificate-employee.list-month-previous.uri}")
    private String certificateEmployeeListMonthPreviousUri;

    @Value("${certificate-employee.export-to-pdf.uri}")
    private String certificateEmployeeExportToPdfUri;

    @Value("${certificate-employee.download-pdf.uri}")
    private String certificateEmployeeDownloadPdfUri;

    @Value("${certificate-employee.random-certificate-employee.uri}")
    private String certificateEmployeeRandomCertificateEmployeeUri;


}
