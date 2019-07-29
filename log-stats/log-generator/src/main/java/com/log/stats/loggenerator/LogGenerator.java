package com.log.stats.loggenerator;

import java.io.IOException;
import java.util.Date;

public class LogGenerator {

    private static LogRow getRandomLogRow() {
        return LogRow.aNew()
                .date(new Date())
                .logLevel(LogLevel.randomLogLevel())
                .city(Utils.getRandomCity())
                .build();
    }

    public static void run() {
        while (true) {
            try {
                LogFileWriter.write(getRandomLogRow());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
