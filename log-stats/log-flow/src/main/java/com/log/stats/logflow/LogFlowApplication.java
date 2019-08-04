package com.log.stats.logflow;

import com.log.stats.logflow.topology.bolt.InValidLogStreamBolt;
import com.log.stats.logflow.topology.bolt.LogRowValidatorBolt;
import com.log.stats.logflow.topology.spout.LogFlowKafkaSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Properties;

public class LogFlowApplication {
    public static void main(String[] args) throws Exception {

        Properties kafkaBoltProps = new Properties();
        kafkaBoltProps.put("bootstrap.servers", "127.0.0.1:9092");
        kafkaBoltProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaBoltProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaBoltProps.put("batch.size", "0");
        kafkaBoltProps.put("acks", "all");

        LogFlowKafkaSpout logFlowKafkaSpout = new LogFlowKafkaSpout("127.0.0.1:9092", "cityLog-raw", "raw-log-consumer-group", 3000, 1000000);
        LogRowValidatorBolt validatorBolt = new LogRowValidatorBolt();
        InValidLogStreamBolt inValidLogStreamBolt = new InValidLogStreamBolt();

        FieldNameBasedTupleToKafkaMapper<String, String> validLogRowMapper = new FieldNameBasedTupleToKafkaMapper<>("key", "valid-log");

        KafkaBolt<String, String> kafkaValidBolt = new KafkaBolt<String, String>()
                .withProducerProperties(kafkaBoltProps)
                .withTopicSelector(new DefaultTopicSelector("cityLog-valid"))
                .withTupleToKafkaMapper(validLogRowMapper);

        FieldNameBasedTupleToKafkaMapper<String, String> errorMapper = new FieldNameBasedTupleToKafkaMapper<>("key", "invalid-log");

        KafkaBolt<String, String> kafkaInvalidBolt = new KafkaBolt<String, String>()
                .withProducerProperties(kafkaBoltProps)
                .withTopicSelector(new DefaultTopicSelector("cityLog-invalid"))
                .withTupleToKafkaMapper(errorMapper);




        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout", logFlowKafkaSpout, 1);
        builder.setBolt("validatorBolt", validatorBolt, 1).allGrouping("kafkaSpout");
        builder.setBolt("kafkaValidBolt", kafkaValidBolt, 1).allGrouping("validatorBolt", "valid-log-stream");
       // builder.setBolt("inValidLogStreamBolt", inValidLogStreamBolt, 1).allGrouping("validatorBolt", "invalid-log-stream");
        builder.setBolt("kafkaInvalidBolt", kafkaInvalidBolt, 1).allGrouping("validatorBolt","invalid-log-stream");

        Config config = new Config();
        config.setDebug(true);

        LocalCluster local = new LocalCluster();
        local.submitTopology("log_flow_topology", config, builder.createTopology());

    }
}
