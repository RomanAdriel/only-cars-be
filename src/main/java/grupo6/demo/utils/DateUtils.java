package grupo6.demo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String stringifyDate(LocalDate date) {

        String dateString = date.format(DateTimeFormatter.ISO_DATE);

        dateString = dateString.replaceAll("-", "");

        return dateString;

    }
}
