package com.log.stats.logflow.topology.spout;

import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;

public class LogFlowKafkaSpout extends KafkaSpout {


    public LogFlowKafkaSpout(String bootstrapServers, String topic, String groupId, int offsetCommitPeriods,
                             int maxUncommittedOffsets) {
        super(KafkaSpoutConfig.builder(bootstrapServers, topic)
                .setRecordTranslator(new KafkaRecordTranslator<>())
                .setGroupId(groupId)
                .setOffsetCommitPeriodMs(offsetCommitPeriods)
                .setMaxUncommittedOffsets(maxUncommittedOffsets)
                .build());

    }
}
