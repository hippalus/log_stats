package com.log.stats.logflow.topology.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class ElasticSearchBolt extends BaseRichBolt {
    private OutputCollector collector;
    private TransportClient esClient;
    private JSONParser jsonParser;
    private String targetIndex;
    private String host;
    private Integer port;
    private String typeName;

    public ElasticSearchBolt(String targetIndex, String typeName, String host, Integer port) {
        this.targetIndex = targetIndex;
        this.typeName = typeName;
        this.host = host;
        this.port = port;
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        this.jsonParser = new JSONParser();
        Settings settings = Settings.builder()
                .put("cluster.name", "docker-cluster")
                .put("client.transport.ping_timeout", "500s").build();

        try {
            esClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(this.host), this.port));

        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Tuple tuple) {

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(tuple.getStringByField("valid-log"));
            IndexResponse ixResponse = esClient.prepareIndex(this.targetIndex, this.typeName)
                    .setSource(jsonObject)
                    .get();

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            this.collector.ack(tuple);
        }


    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }


}
