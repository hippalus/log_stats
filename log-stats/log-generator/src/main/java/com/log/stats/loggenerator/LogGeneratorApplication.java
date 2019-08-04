package com.log.stats.loggenerator;

import org.apache.log4j.Logger;


public class LogGeneratorApplication {

    public static void main(String[] args) throws InterruptedException {

        while (true) {
            City city = Utils.getRandomCity();
            Logger logger = Utils.getLoggerByCity(city);
            checkRandomLogLevelAndRandomLoggerName(city, logger);
            Thread.sleep((long) (Math.random()*1000));
        }

    }

    private static void checkRandomLogLevelAndRandomLoggerName(City city, Logger logger) {
        if (isLogLevelError()) {
            loggerIsError(city, logger);
        } else if (isLogLevelWarn()) {
            loggerIsWarn(city, logger);
        } else if (isLogLevelInfo()) {
            loggerIsInfo(city, logger);
        } else if (isLogLevelDebug()) {
            loggerIsDebug(city, logger);
        } else if (isLogLevelFatal()) {
            loggerIsFatal(city, logger);
        }
    }

    private static void loggerIsError(City city, Logger logger) {
        logger.error(city.getMessage());
    }

    private static void loggerIsWarn(City city, Logger logger) {
        logger.warn(String.format(" %s", city.getMessage()));
    }

    private static void loggerIsInfo(City city, Logger logger) {
        logger.info(String.format(" %s", city.getMessage()));
    }

    private static void loggerIsDebug(City city, Logger logger) {
        logger.debug(city.getMessage());
    }

    private static void loggerIsFatal(City city, Logger logger) {
        logger.fatal(city.getMessage());
    }

    private static boolean isLogLevelFatal() {
        return LogLevel.FATAL.equals(LogLevel.randomLogLevel());
    }

    private static boolean isLogLevelDebug() {
        return LogLevel.DEBUG.equals(LogLevel.randomLogLevel());
    }

    private static boolean isLogLevelInfo() {
        return LogLevel.INFO.equals(LogLevel.randomLogLevel());
    }

    private static boolean isLogLevelWarn() {
        return LogLevel.WARN.equals(LogLevel.randomLogLevel());
    }

    private static boolean isLogLevelError() {
        return LogLevel.ERROR.equals(LogLevel.randomLogLevel());
    }
}
