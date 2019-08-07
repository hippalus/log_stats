package com.log.stats.loglistener.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessageProducer {
    private Properties properties;
    private Producer<String, String> producer;
    private String topic;

    public MessageProducer(Properties properties,String topic) {
        this.properties = properties;
        producer = createProducer();
        this.topic=topic;

    }

    private Producer<String, String> createProducer() {
        return new KafkaProducer<>(this.properties);
    }

    void sendMessage(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, message);
        producer.send(record);
    }
}
