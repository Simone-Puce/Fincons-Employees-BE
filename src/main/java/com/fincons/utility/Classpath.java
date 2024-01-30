package com.fincons.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Classpath {

    // APPLICATION CONTEXT
    @Value("${application.context}")
    private   String APP_CONTEXT ;

    // LOGIN/REGISTER END POINTS
    @Value("${base.uri.version}")
    private  String BASE_URI_VERSION;

    @Value("${register.uri}")
    private  String REGISTER_URI;

    @Value("${login.uri}")
    private  String LOGIN_URI;

    @Value("${admin.uri}")
    private  String ADMIN_URI;

    @Value("${user.uri}")
    private  String USER_URI;

    @Value("${employees.uri}")
    private  String EMPLOYEES_URI;

    @Value("${error.uri}")
    private  String ERROR_URI;

    @Value("${logout.uri}")
    private  String LOGOUT_URI;

    @Value("${registered.users.uri}")
    private  String REGISTERED_USERS_URI;
    @Value("${detail.userdto.uri}")
    private  String DETAIL_USERDTO_URI;

    @Value("${modify.user}")
    private  String MODIFY_USER;

    // DEPARTMENT
    @Value("${department.uri}")
    private  String DEPARTMENT_URI;

    @Value("${department.find-by-id}")
    private  String DEPARTMENT_FIND_BY_ID_URI;

    @Value("${department.list}")
    private  String DEPARTMENT_LIST_URI;

    @Value("${department.create}")
    private  String  DEPARTMENT_CREATE_URI;

    @Value("${department.update}")
    private  String DEPARTMENT_UPDATE_URI;

    @Value("${department.delete}")
    private  String  DEPARTMENT_DELETE_URI;

    @Value("${department.find-employee-by-iddepartment}")
    private   String DEPARTMENT_FIND_EMPLOYEE_BY_IDDEPARTMENT_URI;

    // POSITION
    @Value("${position.uri}")
    private  String   POSITION_URI;

    @Value("${position.find-position-by-id}")
    private  String  POSITION_FIND_BY_ID_URI;

    @Value("${position.list}")
    private  String  POSITION_LIST_URI;

    @Value("${position.create}")
    private  String   POSITION_CREATE_URI;

    @Value("${position.update}")
    private  String   POSITION_UPDATE_URI;

    @Value("${position.delete}")
    private  String  POSITION_DELETE_URI;

    // PROJECT
    @Value("${project.uri}")
    private  String  PROJECT_URI;

    @Value("${project.find-project-by-id}")
    private  String  PROJECT_FIND_BY_ID_URI;

    @Value("${project.list}")
    private  String  PROJECT_LIST_URI;

    @Value("${project.create}")
    private  String  PROJECT_CREATE_URI;

    @Value("${project.update}")
    private  String  PROJECT_UPDATE_URI;

    @Value("${project.delete}")
    private  String  PROJECT_DELETE_URI;

    // EMPLOYEE
    @Value("${employee.uri}")
    private  String  EMPLOYEE_URI;

    @Value("${employee.find-project-by-id}")
    private  String EMPLOYEE_FIND_PROJECT_BY_ID_URI;

    @Value("${employee.find-project-by-email}")
    private  String  EMPLOYEE_FIND_PROJECT_BY_EMAIL_URI;

    @Value("${employee.list}")
    private  String  EMPLOYEE_LIST_URI;

    @Value("${employee.create}")
    private  String  EMPLOYEE_CREATE_URI;

    @Value("${employee.update}")
    private  String  EMPLOYEE_UPDATE_URI;

    @Value("${employee.delete}")
    private  String  EMPLOYEE_DELETE_URI;

    @Value("${employee.find-employee-project}")
    private  String EMPLOYEE_FIND_EMPLOYEE_PROJECT_URI;

    @Value("${employee.list-employee-project}")
    private  String EMPLOYEE_LIST_EMPLOYEE_PROJECT_URI;

    @Value("${employee.create-employee-project}")
    private  String  EMPLOYEE_CREATE_EMPLOYEE_PROJECT_URI;

    @Value("${employee.update-employee-project}")
    private  String EMPLOYEE_UPDATE_EMPLOYEE_PROJECT_URI;

    @Value("${employee.delete-employee-project}")
    private  String  EMPLOYEE_DELETE_EMPLOYEE_PROJECT_URI;

    @Value("${employee.importfile}")
    private  String  EMPLOYEE_IMPORTFILE_URI;

    // FILE CONTROLLER END POINTS
    @Value("${file.uri}")
    private  String FILE_URI;

    @Value("${file.list.uri}")
    private  String  FILE_LIST_URI;

    @Value("${file.upload.uri}")
    private  String  FILE_UPLOAD_URI;

    @Value("${file.view.uri}")
    private  String   FILE_VIEW_URI;

    @Value("${file.download.uri}")
    private  String   FILE_DOWNLOAD_URI;

    @Value("${file.delete.uri}")
    private  String FILE_DELETE_URI;

    @Value("${file.import.uri}")
    private  String  FILE_IMPORT_URI;

    // EMAIL CONTROLLER
    @Value("${email.sender.uri}")
    private  String  EMAIL_SENDER_URI;

    @Value("${email.sender.birth.uri}")
    private  String EMAIL_SENDER_BIRTH_URI;

    @Value("${email.sender.hire.uri}")
    private  String   EMAIL_SENDER_HIRE_URI;

    @Value("${new.employee.random.uri}")
    private  String  NEW_EMPLOYEE_RANDOM_URI;

    // CERTIFICATE CONTROLLER END POINTS
    @Value("${certificate.uri}")
    private  String CERTIFICATE_URI;

    @Value("${certificate.list.uri}")
    private  String  CERTIFICATE_LIST_URI;

    @Value("${certificate.add.uri}")
    private  String   CERTIFICATE_ADD_URI;

    @Value("${certificate.update.uri}")
    private  String  CERTIFICATE_UPDATE_URI;

    @Value("${certificate.find-by-id.uri}")
    private  String   CERTIFICATE_FIND_BY_ID_URI;

    @Value("${certificate.delete.uri}")
    private  String  CERTIFICATE_DELETE_URI;

    // CERTIFICATE EMPLOYEE CONTROLLER END POINTS
    @Value("${certificate-employee.uri}")
    private  String  CERTIFICATE_EMPLOYEE_URI;

    @Value("${certificate-employee.list.uri}")
    private  String  CERTIFICATE_EMPLOYEE_LIST_URI;

    @Value("${certificate-employee.add.uri}")
    private  String  CERTIFICATE_EMPLOYEE_ADD_URI;

    @Value("${certificate-employee.find-by-id.uri}")
    private  String   CERTIFICATE_EMPLOYEE_FIND_BY_ID_URI;

    @Value("${certificate-employee.update.uri}")
    private  String  CERTIFICATE_EMPLOYEE_UPDATE_URI;

    @Value("${certificate-employee.delete.uri}")
    private  String CERTIFICATE_EMPLOYEE_DELETE_URI;

    @Value("${certificate-employee.list-month-previous.uri}")
    private  String  CERTIFICATE_EMPLOYEE_LIST_MONTH_PREVIOUS_URI;

    @Value("${certificate-employee.export-to-pdf.uri}")
    private  String CERTIFICATE_EMPLOYEE_EXPORT_TO_PDF_URI;

    @Value("${certificate-employee.download-pdf.uri}")
    private  String   CERTIFICATE_EMPLOYEE_DOWNLOAD_PDF_URI;

    @Value("${certificate-employee.random-certificate-employee.uri}")
    private  String   CERTIFICATE_EMPLOYEE_RANDOM_CERTIFICATE_EMPLOYEE_URI;

}
