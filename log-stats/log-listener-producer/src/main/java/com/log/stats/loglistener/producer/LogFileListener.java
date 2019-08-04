package com.log.stats.loglistener.producer;


import java.io.File;
import java.io.RandomAccessFile;

public class LogFileListener implements Runnable {

    private long lastKnownPosition = 0;
    private boolean shouldIRun = true;
    private File file;
    private MessageProducer messageProducer;

    public LogFileListener(String filePath) {
        this.file = new File(filePath);
    }

    public LogFileListener(String filePath, MessageProducer messageProducer) {
        this.file = new File(filePath);
        this.messageProducer = messageProducer;
    }

    private void printLine(String message) {
        System.out.println(message);
    }

    public void stopRunning() {
        this.shouldIRun = false;
    }

    @Override
    public void run() {
        try {
            while (this.shouldIRun) {
                long fileLength = file.length();
                if (fileLength > this.lastKnownPosition) {
                    RandomAccessFile readFileAccess = new RandomAccessFile(file, "r");
                    readFileAccess.seek(this.lastKnownPosition);
                    String line;
                    while ((line = readFileAccess.readLine()) != null) {
                        this.printLine(line);
                        this.messageProducer.sendMessage(Utils.stringToJsonString(line));
                    }
                    this.lastKnownPosition = readFileAccess.getFilePointer();
                    readFileAccess.close();
                }
            }
        } catch (Exception e) {
            stopRunning();
        }
    }

}
