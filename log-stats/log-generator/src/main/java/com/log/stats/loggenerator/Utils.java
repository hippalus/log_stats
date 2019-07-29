package com.log.stats.loggenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    private static final customSDF sdf = new customSDF();

    public static synchronized String dateToRFC3339(Date date) {
        return sdf.format(date);
    }

    public static synchronized Date RFC3339toDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    private static class customSDF {
        public final SimpleDateFormat sdf;

        public customSDF() {
            this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            this.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        public final String format(Date date) {
            return this.sdf.format(date);
        }

        public Date parse(String source) throws ParseException {
            return this.sdf.parse(source);
        }
    }

    private static String[] cities = new String[]{"Istanbul", "Tokyo", "Moscow", "Beijing", "London"};

    public static City getRandomCity() {
        return new City(cities[new Random().nextInt(cities.length)]);
    }

}
