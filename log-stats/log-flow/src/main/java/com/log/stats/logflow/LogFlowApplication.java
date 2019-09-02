package com.log.stats.logflow;

import com.log.stats.logflow.topology.Constants;
import com.log.stats.logflow.topology.bolt.ElasticSearchBolt;
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
        String KAFKA_BROKER = "127.0.0.1:9092";
        String ELK_HOST = "127.0.0.1";
        try {
            KAFKA_BROKER = args[0];
            ELK_HOST = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }


            Properties kafkaBoltProps = new Properties();
            kafkaBoltProps.put("bootstrap.servers", KAFKA_BROKER);
            kafkaBoltProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaBoltProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaBoltProps.put("batch.size", "0");
            kafkaBoltProps.put("acks", "all");


            LogFlowKafkaSpout logFlowKafkaSpout = new LogFlowKafkaSpout(KAFKA_BROKER, Constants.Topics.CITY_LOG_RAW);
            LogRowValidatorBolt validatorBolt = new LogRowValidatorBolt();
            ElasticSearchBolt elasticSearchBolt = new ElasticSearchBolt(Constants.CITY_LOGS, ELK_HOST, 9200);

            FieldNameBasedTupleToKafkaMapper<String, String> validLogRowMapper = new FieldNameBasedTupleToKafkaMapper<>("key", Constants.TupleFields.VALID_LOG);

            KafkaBolt<String, String> kafkaValidBolt = new KafkaBolt<String, String>()
                    .withProducerProperties(kafkaBoltProps)
                    .withTopicSelector(new DefaultTopicSelector( Constants.Topics.CITY_LOG_VALID))
                    .withTupleToKafkaMapper(validLogRowMapper);

            FieldNameBasedTupleToKafkaMapper<String, String> errorMapper = new FieldNameBasedTupleToKafkaMapper<>("key", Constants.TupleFields.INVALID_LOG);

            KafkaBolt<String, String> kafkaInvalidBolt = new KafkaBolt<String, String>()
                    .withProducerProperties(kafkaBoltProps)
                    .withTopicSelector(new DefaultTopicSelector(Constants.Topics.CITY_LOG_INVALID))
                    .withTupleToKafkaMapper(errorMapper);


            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("kafkaSpout", logFlowKafkaSpout, 1);
            builder.setBolt("validatorBolt", validatorBolt, 1).allGrouping("kafkaSpout");
            builder.setBolt("kafkaValidBolt", kafkaValidBolt, 1).allGrouping("validatorBolt", Constants.Stream.VALID_LOG_STREAM);
            builder.setBolt("kafkaInvalidBolt", kafkaInvalidBolt, 1).allGrouping("validatorBolt", Constants.Stream.INVALID_LOG_STREAM);
            builder.setBolt("elasticSearchBolt", elasticSearchBolt, 1).shuffleGrouping("validatorBolt", Constants.Stream.VALID_LOG_STREAM);

            Config config = new Config();
            config.setDebug(true);
            config.setNumWorkers(2);
            config.setMaxSpoutPending(5000);

            LocalCluster local = new LocalCluster();
            local.submitTopology("log_flow_topology", config, builder.createTopology());

            //StormSubmitter.submitTopology("log_flow_topology", config, builder.createTopology());

    }
}
