package com.log.stats.logflow.topology.bolt;

import com.log.stats.logflow.topology.Constants;
import org.apache.http.HttpHost;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.elasticsearch.client.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ElasticSearchBolt extends BaseRichBolt {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchBolt.class);

    private transient RestClient esClient;
    private transient OutputCollector collector;
    private transient JSONParser jsonParser;
    private boolean declareOutputFlag=true;
    private final String targetIndex;
    private final String host;
    private final Integer port;

    public ElasticSearchBolt(String targetIndex ,String host, Integer port) {
        this.targetIndex = targetIndex;
        this.host = host;
        this.port = port;
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        this.jsonParser = new JSONParser();
        esClient =  RestClient.builder(new HttpHost(this.host,this.port,"http")).build();

    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Tuple tuple) {
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(tuple.getStringByField(Constants.TupleFields.VALID_LOG));
            Request request=new Request("POST", String.format("/%s", targetIndex)+"/_doc");

            request.setJsonEntity(jsonObject.toJSONString());

            Response response=esClient.performRequest(request);
            //TODO :Response  check

        } catch (ParseException | IOException e) {
            LOGGER.error(String.format("Failed ElasticBolt :%s", e.getMessage()));
        } finally {
            this.collector.ack(tuple);
        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        if (!declareOutputFlag){
            throw new UnsupportedOperationException();
        }

    }


}
