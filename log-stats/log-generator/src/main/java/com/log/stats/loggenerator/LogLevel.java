package com.log.stats.loggenerator;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum LogLevel {
    DEBUG,INFO,ERROR,FATAL,WARN;

    private static final List<LogLevel> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static LogLevel randomLogLevel()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    public static Logger getLoggerByCity(City city) {
        return Logger.getLogger(city.getName());
    }

}
