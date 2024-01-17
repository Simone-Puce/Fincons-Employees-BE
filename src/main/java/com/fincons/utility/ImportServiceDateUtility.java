package com.fincons.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ImportServiceDateUtility {
    private ImportServiceDateUtility() {
        throw new IllegalStateException("Utility class");
    }


    public static Timestamp generateId() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static String generateDate() {
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        return data.format(formatter);
    }
}
