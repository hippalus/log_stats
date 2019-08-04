package com.log.stats.loglistener.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessageProducer {
    private Properties properties;
    private Producer<String, String> producer;

    public MessageProducer(Properties properties) {
        this.properties = properties;
        producer = createProducer();

    }

    private Producer<String, String> createProducer() {
        return new KafkaProducer<>(this.properties);
    }

    void sendMessage(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_NAME, message);
        producer.send(record);
    }
}
