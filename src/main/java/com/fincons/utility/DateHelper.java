package com.fincons.utility;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Calendar convertToCalendarViaInstance(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public LocalDate convertTo18(LocalDate dateToConvert) {
        return dateToConvert.plusYears(18L);
    }
    public LocalDate convertTo60(LocalDate dateToConvert) {
        return dateToConvert.plusYears(60L);
    }

    public LocalDate createHireDay(LocalDate birtHDate) {
        Date hireDate = faker.date().between(
                convertToDateViaInstant(convertTo18(birtHDate)),
                convertToDateViaInstant(convertTo60(birtHDate))
        );

        LocalDate hireDay = convertToLocalDateViaInstant(hireDate);
        Calendar hireCalendar = convertToCalendarViaInstance(hireDate);

        boolean weekEnd = hireCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                hireCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
        boolean festivity =
                hireCalendar.get(Calendar.MONTH) == Calendar.JANUARY && hireCalendar.get(Calendar.DAY_OF_MONTH) == 1 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.JANUARY && hireCalendar.get(Calendar.DAY_OF_MONTH) == 6 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.MAY && hireCalendar.get(Calendar.DAY_OF_MONTH) == 1 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.JUNE && hireCalendar.get(Calendar.DAY_OF_MONTH) == 2 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.AUGUST && hireCalendar.get(Calendar.DAY_OF_MONTH) == 15 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.NOVEMBER && hireCalendar.get(Calendar.DAY_OF_MONTH) == 1 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.DECEMBER && hireCalendar.get(Calendar.DAY_OF_MONTH) == 8 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.DECEMBER && hireCalendar.get(Calendar.DAY_OF_MONTH) == 25 ||
                hireCalendar.get(Calendar.MONTH) == Calendar.DECEMBER && hireCalendar.get(Calendar.DAY_OF_MONTH) == 26;

        if (weekEnd) {
            logger.info("\u001B[31m" + "Found day between Saturday and Sunday: "+ hireDay + " " + hireDay.getDayOfWeek().name() + "\u001B[0m");
            hireDay = createHireDay(birtHDate);
        } else if (festivity){
            logger.info("\u001B[31m" + "Found Holiday: " + hireDay + "\u001B[0m");
            hireDay = createHireDay(birtHDate);
        }
        return hireDay;
    }
}
