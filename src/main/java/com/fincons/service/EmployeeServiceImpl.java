package com.fincons.service;


import com.fincons.entity.Agency;
import com.fincons.entity.Employee;
import com.fincons.mapper.EmployeeMapper;
import com.fincons.model.EmployeeDto;
import com.fincons.repository.AgencyRepository;
import com.fincons.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements EmployeeServiceApi {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.mapEmployeeListToEmployeeDtoList(employees);
    }

    @Override
    public Optional<EmployeeDto> getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employeeMapper::mapEmployeeToEmployeeDto);
    }


    @Override
    public Optional<EmployeeDto> createEmployee(EmployeeDto employeeDto) throws Exception {
        String email = employeeDto.getEmail();

        Optional<Employee> existingEmployeeWithEmail = employeeRepository.findByEmail(email);
        if (existingEmployeeWithEmail.isPresent()) {
            String errorMessage = "Un employee con questa email è già presente.";
            throw new Exception(errorMessage);
        }

        String agencyName = employeeDto.getAgency();
        Optional<Agency> optionalAgency = agencyRepository.findByName(agencyName);

        Agency agency;
        if (optionalAgency.isEmpty()) {
            agency = new Agency();
        } else {
            agency = optionalAgency.get();
        }

        Employee employee = employeeMapper.mapEmployeeDtoToEmployee(employeeDto);
        employee.setAgency(agency);

        Employee savedEmployee = employeeRepository.save(employee);
        return Optional.ofNullable(employeeMapper.mapEmployeeToEmployeeDto(savedEmployee));
    }

    @Override
    public Optional<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(existingEmployee -> {
            if (!isValidEmail(employeeDto.getEmail())) {
                return null;
            }
            BeanUtils.copyProperties(employeeDto, existingEmployee, "id");
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return employeeMapper.mapEmployeeToEmployeeDto(updatedEmployee);
        });
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(employeeRepository::delete);
    }

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
