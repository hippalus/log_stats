package com.log.stats.logflow.topology.bolt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.stats.logflow.entity.LogRow;
import com.log.stats.logflow.topology.Constants;
import com.log.stats.logflow.utils.Utils;
import com.log.stats.logflow.validator.RawLogRowValidator;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LogRowValidatorBolt extends BaseRichBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRowValidatorBolt.class);

    private transient OutputCollector outputCollector;
    private ObjectMapper objectMapper;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void execute(Tuple tuple) {
        try {
            String rawLogRow = tuple.getStringByField("value");
            JSONObject jsonRawLogRow = Utils.convertStringToJSON(rawLogRow);
            assert jsonRawLogRow != null;
            String content = jsonRawLogRow.get(Constants.TupleFields.LOG_RAW).toString();
            LogRow logRow = this.objectMapper.readValue(content, LogRow.class);

            if (RawLogRowValidator.run(logRow)) {
                this.outputCollector.emit(Constants.Stream.VALID_LOG_STREAM, new Values(content));
            } else {
                this.outputCollector.emit(Constants.Stream.INVALID_LOG_STREAM, new Values(content));
            }
        } catch (Exception exception) {
            LOGGER.error(String.format("Exception: %s", exception.getMessage()));
        } finally {
            this.outputCollector.ack(tuple);
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream(Constants.Stream.VALID_LOG_STREAM, new Fields(Constants.TupleFields.VALID_LOG));
        outputFieldsDeclarer.declareStream(Constants.Stream.INVALID_LOG_STREAM, new Fields(Constants.TupleFields.INVALID_LOG));
    }
}
