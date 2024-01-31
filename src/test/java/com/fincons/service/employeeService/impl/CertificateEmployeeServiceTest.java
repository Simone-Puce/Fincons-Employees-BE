package com.fincons.service.employeeService.impl;

import com.fincons.dto.CertificateEmployeeDTO;
import com.fincons.mapper.CertificateEmployeeMapper;
import com.fincons.repository.CertificateEmployeeRepository;
import com.fincons.service.employeeService.CertificateEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CertificateEmployeeServiceTest {

    @Autowired
    private CertificateEmployeeService certificateEmployeeService;

    @Autowired
    private CertificateEmployeeRepository certificateEmployeeRepository;

    @Autowired
    private CertificateEmployeeMapper certificateEmployeeMapper;
    private jakarta.servlet.http.HttpServletResponse HttpServletResponse;

    @Test
    void testListCertificateEmployeeByPreviousMonth() {
        LocalDate dateFrom = LocalDate.of(2000, 1, 1);
        LocalDate dateTo = LocalDate.of(2023, 12, 31);

        List<CertificateEmployeeDTO> result = certificateEmployeeService.listCertificateEmployeeByPreviousMonth(dateFrom, dateTo);

        assertFalse(result.isEmpty(), "La lista non deve essere vuota");
        assertEquals(88, result.size(), "La lista deve contenere almeno un elemento");
    }


}
