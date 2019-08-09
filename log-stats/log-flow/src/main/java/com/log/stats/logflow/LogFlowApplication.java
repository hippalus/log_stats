package com.log.stats.logflow;

import com.log.stats.logflow.topology.bolt.ElasticSearchBolt;
import com.log.stats.logflow.topology.bolt.LogRowValidatorBolt;
import com.log.stats.logflow.topology.spout.LogFlowKafkaSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
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
        ElasticSearchBolt elasticSearchBolt=new ElasticSearchBolt("city-logs" ,"valid","127.0.0.1",9300);

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
        builder.setBolt("kafkaInvalidBolt", kafkaInvalidBolt, 1).allGrouping("validatorBolt","invalid-log-stream");
        builder.setBolt("elasticSearchBolt",elasticSearchBolt,1).shuffleGrouping("validatorBolt","valid-log-stream");

        Config config = new Config();
        config.setDebug(true);
        config.setNumWorkers(1);
        config.setMaxSpoutPending(5000);

       // LocalCluster local = new LocalCluster();
        StormSubmitter.submitTopology("log_flow_topology", config, builder.createTopology());

    }
}
