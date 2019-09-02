package com.log.stats.logflow.topology.spout;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.kafka.spout.RecordTranslator;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;

public class KafkaRecordTranslator<K, V> implements RecordTranslator<K, V> {

    private static final Fields FIELDS = new Fields("value");

    private static final long serialVersionUID = 4046733527937465363L;


    @Override
    public List<Object> apply(ConsumerRecord<K, V> record) {
        return new Values(record.value());
    }

    @Override
    public Fields getFieldsFor(String stream) {
        return FIELDS;
    }

    @Override
    public List<String> streams() {
        return DEFAULT_STREAM;
    }

}