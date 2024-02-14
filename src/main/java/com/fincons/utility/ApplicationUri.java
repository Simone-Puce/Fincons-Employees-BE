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

    @Value("${register.base.uri}")
    private String registerBaseUri;

    @Value("${login.base.uri}")
    private String loginBaseUri;

    @Value("${admin.base.uri}")
    private String adminBaseUri;

    @Value("${delete.user-by-email}")
    private String deleteUserByEmail;

    @Value("${user.base.uri}")
    private String userBaseUri;

    @Value("${employees.base.uri}")
    private String employeesBaseUri;

    @Value("${error.base.uri}")
    private String errorBaseUri;

    @Value("${logout.base.uri}")
    private String logoutBaseUri;

    @Value("${registered.users}")
    private String registeredUsers;

    @Value("${detail.userdto}")
    private String detailUserDto;

    @Value("${modify.user}")
    private String modifyUser;

    @Value("${update.user.password}")
    private String updateUserPassword;

    //ROLE
    @Value("${role.base.uri}")
    private String roleBaseUri;

    @Value("${role.find-by-id}")
    private String roleFindById;

    @Value("${role.create}")
    private String roleCreate;

    @Value("${role.put}")
    private String rolePut;

    @Value("${delete.role}")
    private String deleteRole;

    // DEPARTMENT
    @Value("${department.base.uri}")
    private String departmentBaseUri;

    @Value("${department.find-by-code}")
    private String departmentFindByCode;

    @Value("${department.list}")
    private String departmentList;

    @Value("${department.create}")
    private String departmentCreate;

    @Value("${department.update}")
    private String departmentUpdate;

    @Value("${department.delete}")
    private String departmentDelete;

    @Value("${department.find-employee-by-iddepartment}")
    private String departmentFindEmployeeByIdDepartment;

    // POSITION
    @Value("${position.base.uri}")
    private String positionUri;

    @Value("${position.find-position-by-code")
    private String positionFindByCode;

    @Value("${position.list}")
    private String positionList;

    @Value("${position.create}")
    private String positionCreate;

    @Value("${position.update}")
    private String positionUpdate;

    @Value("${position.delete}")
    private String positionDelete;

    // PROJECT
    @Value("${project.base.uri}")
    private String projectBaseUri;

    @Value("${project.find-project-by-id}")
    private String projectFindById;

    @Value("${project.list}")
    private String projectList;

    @Value("${project.create}")
    private String projectCreate;

    @Value("${project.update}")
    private String projectUpdate;

    @Value("${project.delete}")
    private String projectDelete;

    // EMPLOYEE
    @Value("${employee.base.uri}")
    private String employeeBaseUri;

    @Value("${employee.find-by-ssn}")
    private String employeeFindBySsn;

    @Value("${employee.find-by-email}")
    private String employeeFindByEmail;

    @Value("${employee.list}")
    private String employeeList;

    @Value("${employee.create}")
    private String employeeCreate;

    @Value("${employee.update}")
    private String employeeUpdate;

    @Value("${employee.delete}")
    private String employeeDelete;

    @Value("${employee.find-employee-projects}")
    private String employeeFindEmployeeProjects;

    @Value("${employee.list-employees-projects}")
    private String employeeListEmployeesProjects;

    @Value("${employee.create-employee-project}")
    private String employeeCreateEmployeeProject;

    @Value("${employee.update-employee-project}")
    private String employeeUpdateEmployeeProject;

    @Value("${employee.delete-employee-project}")
    private String employeeDeleteEmployeeProject;

    @Value("${employee.importfile}")
    private String employeeImportFile;

    // FILE CONTROLLER END POINTS
    @Value("${file.base.uri}")
    private String fileBaseUri;

    @Value("${file.list}")
    private String fileList;

    @Value("${file.upload}")
    private String fileUpload;

    @Value("${file.view}")
    private String fileView;

    @Value("${file.download}")
    private String fileDownload;

    @Value("${file.delete}")
    private String fileDelete;


    // EMAIL CONTROLLER
    @Value("${email.sender.base.uri}")
    private String emailSenderUri;

    @Value("${email.sender.birth}")
    private String emailSenderBirth;

    @Value("${email.sender.hire}")
    private String emailSenderHire;

    @Value("${new.employee.random}")
    private String newEmployeeRandom;

    // CERTIFICATE CONTROLLER END POINTS
    @Value("${certificate.base.uri}")
    private String certificateUri;

    @Value("${certificate.list}")
    private String certificateList;

    @Value("${certificate.add}")
    private String certificateAdd;

    @Value("${certificate.update}")
    private String certificateUpdate;

    @Value("${certificate.find-by-id}")
    private String certificateFindById;

    @Value("${certificate.delete}")
    private String certificateDelete;

    // CERTIFICATE EMPLOYEE CONTROLLER END POINTS
    @Value("${certificate-employee.base.uri}")
    private String certificateEmployeeBaseUri;

    @Value("${certificate-employee.list}")
    private String certificateEmployeeList;

    @Value("${certificate-employee.add}")
    private String certificateEmployeeAdd;

    @Value("${certificate-employee.find-by-id}")
    private String certificateEmployeeFindById;

    @Value("${certificate-employee.update}")
    private String certificateEmployeeUpdate;

    @Value("${certificate-employee.delete}")
    private String certificateEmployeeDelete;

    @Value("${certificate-employee.list-month-previous}")
    private String certificateEmployeeListMonthPrevious;

    @Value("${certificate-employee.export-to-pdf}")
    private String certificateEmployeeExportToPdf;

    @Value("${certificate-employee.download-pdf}")
    private String certificateEmployeeDownloadPdf;

    @Value("${certificate-employee.random-certificate-employee}")
    private String certificateEmployeeRandomCertificateEmployee;
}
