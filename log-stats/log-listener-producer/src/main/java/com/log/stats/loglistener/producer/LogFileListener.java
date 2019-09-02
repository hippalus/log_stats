package com.log.stats.loglistener.producer;


import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogFileListener implements Runnable {

    private long lastKnownPosition = 0;
    private final AtomicBoolean shouldIRun = new AtomicBoolean(true);
    private File file;
    private MessageProducer messageProducer;

    private LogFileListener(String filePath, MessageProducer messageProducer) {
        this.file = new File(filePath);
        this.messageProducer = messageProducer;
    }

    private void printLine(String message) {
        System.out.println(message);
    }

    public void stopRunning() {
        this.shouldIRun.set(false);
    }

    public static LogFileListener getListenerAndProducer(String filePath, MessageProducer messageProducer) {
        return new LogFileListener(filePath, messageProducer);
    }


    @Override
    public void run() {
        try {
            while (this.shouldIRun.get()) {
                long fileLength = file.length();
                if (fileLength > this.lastKnownPosition) {
                    try (RandomAccessFile readFileAccess = new RandomAccessFile(file, "r")) {
                        readFileAccess.seek(this.lastKnownPosition);
                        String line;
                        while ((line = readFileAccess.readLine()) != null) {
                            this.printLine(line);
                            this.messageProducer.sendMessage(Utils.stringToJsonString(line));
                        }
                        this.lastKnownPosition = readFileAccess.getFilePointer();
                    }
                }
            }
        } catch (Exception e) {
            stopRunning();
        }
    }

}
