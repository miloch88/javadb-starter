package pl.sda.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parse(String date) {
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Date date) {
        return simpleDateFormat.format(date);
    }
}
