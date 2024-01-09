package com.fincons.utility;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateHelper {
    Faker faker = new Faker();

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

    public LocalDate createHireDay(LocalDate birtHDate) {
        Date hireDate = faker.date().between(
                convertToDateViaInstant(convertTo18(birtHDate)),
                new Date(System.currentTimeMillis()));

        LocalDate hireDay = convertToLocalDateViaInstant(hireDate);

        boolean weekEnd = hireDay.getDayOfWeek() == DayOfWeek.SATURDAY ||
                hireDay.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean holiday =
                hireDay.getMonth() == Month.JANUARY && hireDay.getDayOfMonth() == 1 ||
                        hireDay.getMonth() == Month.JANUARY && hireDay.getDayOfMonth() == 6 ||
                        hireDay.getMonth() == Month.MAY && hireDay.getDayOfMonth() == 1 ||
                        hireDay.getMonth() == Month.JUNE && hireDay.getDayOfMonth() == 2 ||
                        hireDay.getMonth() == Month.AUGUST && hireDay.getDayOfMonth() == 15 ||
                        hireDay.getMonth() == Month.NOVEMBER && hireDay.getDayOfMonth() == 1 ||
                        hireDay.getMonth() == Month.DECEMBER && hireDay.getDayOfMonth() == 8 ||
                        hireDay.getMonth() == Month.DECEMBER && hireDay.getDayOfMonth() == 24 ||
                        hireDay.getMonth() == Month.DECEMBER && hireDay.getDayOfMonth() == 25 ||
                        hireDay.getMonth() == Month.DECEMBER && hireDay.getDayOfMonth() == 26;

        if (weekEnd || holiday) {
            logger.error("Found day between Saturday and Sunday: {} {} ", hireDay, hireDay.getDayOfWeek().name());
            hireDay = hireDay.plusDays(3);
        }
        return hireDay;
    }
}
