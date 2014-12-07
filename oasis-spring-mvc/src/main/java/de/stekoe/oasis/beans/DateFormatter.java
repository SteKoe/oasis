package de.stekoe.oasis.beans;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateFormatter {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDate() {
        return new DateTime(new Date()).toString(DATE_FORMAT);
    }

    public String getCurrentYear() {
        return new DateTime(new Date()).toString("yyyy");
    }
}
