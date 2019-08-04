package com.log.stats.loggenerator;

import org.apache.log4j.Logger;

import java.util.*;

public class Utils {

    private static String[] cities = new String[]{"Istanbul", "Tokyo", "Moscow", "Beijing", "London"};
    public static City getRandomCity() {
        return new City(cities[new Random().nextInt(cities.length)]);
    }
    public static Logger getLoggerByCity(City city) {
        return Logger.getLogger(city.getName());
    }


}
