package com.log.stats.logflow.topology.bolt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.stats.logflow.entity.LogRow;
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
    private OutputCollector outputCollector;
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
            LogRow logRow = this.objectMapper.readValue(jsonRawLogRow.get("log_raw").toString(), LogRow.class);

            if (RawLogRowValidator.run(logRow)) {
                this.outputCollector.emit("valid-log", new Values(logRow.toString()));
            } else {
                this.outputCollector.emit("invalid-log", new Values(jsonRawLogRow.get("log_raw").toString()));
            }
        } catch (Exception exception) {
            LOGGER.error("Exception: " + exception);
        } finally {
            this.outputCollector.ack(tuple);
        }
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream("valid-log-stream", new Fields("valid-log"));
        outputFieldsDeclarer.declareStream("invalid-log-stream", new Fields("invalid-log"));
    }
}
