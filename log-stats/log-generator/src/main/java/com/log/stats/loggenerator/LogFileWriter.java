package com.log.stats.loggenerator;


import java.io.*;
import java.text.ParseException;

public class LogFileWriter {

    private static final String LOGS_PATH = System.getProperty("user.dir");
    private static final String FILE_NAME = "cities-";
    private static final String FILE_EXTENSION = ".log";
    private static final long MAX_FILE_SIZE = 2097152;
    private static String currentAbsoluteFilePath = getAbsoluteFilePath();

    public static void write(LogRow logRow) throws IOException{

        File file = getNewFile(currentAbsoluteFilePath);

       if (file.length() > MAX_FILE_SIZE) {
            currentAbsoluteFilePath=getAbsoluteFilePath();
            file = getNewFile( currentAbsoluteFilePath);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.append(logRow.toString());
            writer.newLine();
            writer.flush();

        }

    }

    private static File getNewFile(String absoluteFilePath) {
        return new File(absoluteFilePath);
    }

    private static String getAbsoluteFilePath() {
        return String.format("%s%s%s%d%s", LOGS_PATH, File.separator, FILE_NAME, System.currentTimeMillis(), FILE_EXTENSION);
    }

}
