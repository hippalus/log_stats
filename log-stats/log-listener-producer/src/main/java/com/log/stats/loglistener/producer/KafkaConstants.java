package com.log.stats.loglistener.producer;

public final class KafkaConstants {
    private KafkaConstants() {
        throw new AssertionError();
    }

    public static final String KAFKA_BROKERS="localhost:9092";
    public static final String TOPIC_NAME="cityLog-raw";




}
