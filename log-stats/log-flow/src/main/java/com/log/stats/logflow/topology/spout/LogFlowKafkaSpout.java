package com.log.stats.logflow.topology.spout;

import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;

public class LogFlowKafkaSpout extends KafkaSpout {


    public LogFlowKafkaSpout(String bootstrapServers, String topic) {
        super(KafkaSpoutConfig.builder(bootstrapServers, topic)
                .s
                .setRecordTranslator(new KafkaRecordTranslator<>())
                .build());

    }
}
