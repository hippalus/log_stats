package com.log.stats.loglistener.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogListenerProducerApplication {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        MessageProducer messageProducer = new MessageProducer(props);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        String filePath = "/home/hisler/Workspace/log_stats/log-stats/cities.log";

        LogFileListener listener = new LogFileListener(filePath, messageProducer);

        executor.execute(listener);

    }
}
