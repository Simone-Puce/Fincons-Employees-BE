package com.fincons.controller;

import com.fincons.dto.ImportResultDTO;
import com.fincons.entity.Employee;
import com.fincons.dto.EmployeeProjectDTO;
import com.fincons.enums.ProcessingStatus;
import com.fincons.exception.EmailException;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.employeeService.ProjectService;
import com.fincons.service.importFile.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company-employee-management")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ProjectService projectService;

    @Autowired
    private ImportService importService;



    @GetMapping(value = "${employee.find-project-by-id}")
    public ResponseEntity<Object> getEmployeeById(@RequestParam long id){
        return employeeService.getEmployeeById(id);
    }
    @GetMapping(value = "${employee.find-project-by-email}")
    public ResponseEntity<Object> getDepartmentByEmail(@RequestParam String email){
        return employeeService.getEmployeeByEmail(email);
    }
    @GetMapping(value="${employee.list}")
    public ResponseEntity<Object> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @PostMapping(value = "${employee.create}")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }
    @PutMapping(value = "${employee.update}")
    public ResponseEntity<Object> updateEmployeeById(@RequestParam long id, @RequestBody Employee employee) throws Exception {
        return employeeService.updateEmployeeById(id, employee);
    }
    @DeleteMapping(value = "${employee.delete}")
    public  ResponseEntity<Object> deleteEmployeeById(@RequestParam long id){
        return employeeService.deleteEmployeeById(id);
    }

    @GetMapping(value = "${employee.find-employee-project}")
    public ResponseEntity<Object> getAllEmployeesProjects(@RequestParam long id){
        return employeeService.findAllEmployeeProjects(id);
    }
    @GetMapping(value = "${employee.list-employee-project}")
    public ResponseEntity<Object> getAllEmployeeProject(){
        return employeeService.getAllEmployeeProject();
    }
    @PostMapping(value= "${employee.create-employee-project}")
    public ResponseEntity<Object> createEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject) {
        return employeeService.addEmployeeProject(idEmployee, idProject);
    }
    @PutMapping(value ="${employee.update-employee-project}")
    public ResponseEntity<Object> updateEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject, @RequestBody EmployeeProjectDTO employeeProjectDTO){
        return employeeService.updateEmployeeProject(idEmployee, idProject, employeeProjectDTO);
    }
    @DeleteMapping(value= "${employee.delete-employee-project}")
    public ResponseEntity<Object> deleteEmployeeProject(@RequestParam long idEmployee, @RequestParam long idProject) {
        return employeeService.deleteEmployeeProject(idEmployee, idProject);
    }

    @PostMapping("${employee.importfile}")
    public ResponseEntity<ImportResultDTO> addEmployeeFromFile(@RequestBody MultipartFile importedFile) {

            ImportResultDTO importResult = importService.processImport(importedFile);
            if (importResult.getStatus() == ProcessingStatus.NOT_LOADED
                    || importResult.getStatus()== ProcessingStatus.LOADED_WITH_ERRORS
                    || importResult.getStatus()== ProcessingStatus.LOADED) {
                return ResponseEntity.ok(importResult);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(importResult);
            }







    }



}

