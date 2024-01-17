package com.fincons.utility;

import com.fincons.service.employeeService.DepartmentService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Component
public class DateHelper {
    Faker faker = new Faker();
    Random random = new Random();
    @Autowired
    private DepartmentService departmentService;

    Logger logger = LoggerFactory.getLogger(DateHelper.class);

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public LocalDate convertTo18(LocalDate dateToConvert) {
        return dateToConvert.plusYears(18L);
    }

    public LocalDate createStartDay(LocalDate birtHDate) {
        Date hireDate = faker.date().between(
                convertToDateViaInstant(convertTo18(birtHDate)),
                new Date(System.currentTimeMillis()));

        LocalDate startDay = convertToLocalDateViaInstant(hireDate);

        boolean weekEnd = startDay.getDayOfWeek() == DayOfWeek.SATURDAY ||
                startDay.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean holiday =
                startDay.getMonth() == Month.JANUARY && startDay.getDayOfMonth() == 1 ||
                        startDay.getMonth() == Month.JANUARY && startDay.getDayOfMonth() == 6 ||
                        startDay.getMonth() == Month.MAY && startDay.getDayOfMonth() == 1 ||
                        startDay.getMonth() == Month.JUNE && startDay.getDayOfMonth() == 2 ||
                        startDay.getMonth() == Month.AUGUST && startDay.getDayOfMonth() == 15 ||
                        startDay.getMonth() == Month.NOVEMBER && startDay.getDayOfMonth() == 1 ||
                        startDay.getMonth() == Month.DECEMBER && startDay.getDayOfMonth() == 8 ||
                        startDay.getMonth() == Month.DECEMBER && startDay.getDayOfMonth() == 24 ||
                        startDay.getMonth() == Month.DECEMBER && startDay.getDayOfMonth() == 25 ||
                        startDay.getMonth() == Month.DECEMBER && startDay.getDayOfMonth() == 26;

        if (weekEnd || holiday) {
            logger.error("Found day between Saturday and Sunday: {} {} ", startDay, startDay.getDayOfWeek().name());
            startDay = startDay.plusDays(3);
        }
        return startDay;
    }

    public String getRandomGender() {
        int randomInt = random.nextInt(2);
        String gender;
        if (randomInt == 0) {
            gender = "male";
        } else {
            gender = "female";
        }
        return gender;
    }
}
