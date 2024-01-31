package com.fincons.service.employeeService.impl;

import com.fincons.entity.*;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.service.employeeService.CertificateEmployeeService;
import com.fincons.service.employeeService.ICreateRandomCertificateEmployee;
import com.fincons.utility.DateHelper;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

@Service
public class CreateRandomCertificateEmployeeImpl implements ICreateRandomCertificateEmployee {
    @Autowired
    private CertificateEmployeeService certificateEmployeeService;
    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;
    @Autowired
    private DateHelper dateHelper;
    Logger logger = LoggerFactory.getLogger(CreateRandomCertificateEmployeeImpl.class);

    @Override
    public void CreateCertificateEmployee() {
            Faker faker = new Faker(new Random());
            Random random =  new Random();

            Employee employee = new Employee();
            employee.setId(1L);
            Certificate certificate = new Certificate();
            certificate.setId(random.nextLong(certificate.getId()));

            for (int i = 0; i < 30; i++) {

                Date dateBirth = faker.date().birthday();
                LocalDate randomDate = dateHelper.convertToLocalDateViaInstant(dateBirth);
                logger.info("Birthday Date: {}", randomDate);

                CertificateEmployee certificateEmployee = new CertificateEmployee();
                certificateEmployee.setEmployee(employee);
                certificateEmployee.setCertificate(certificate);
                certificateEmployee.setAchieved(randomDate);

                certificateEmployeeService.addCertificateEmployee(certificateEmployeeMapper.mapCertificateEmployeeToCertificateEmployeeDto(certificateEmployee));
            }
    }

}
