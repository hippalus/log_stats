package com.log.stats.loglistener.producer;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String stringToJsonString(String row) {
        // 2019-08-03 21:57:39.037 DEBUG  Istanbul Hello From Istanbul
        String regex = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}) ([^ ]*) {2}([^ ]*) (.*)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(row);


        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        ObjectNode rawLogRow = mapper.createObjectNode();

        if ((m.matches() && m.groupCount() == 4)) {

            rawLogRow.put("Date", m.group(1));
            rawLogRow.put("LogLevel", m.group(2));
            rawLogRow.put("City", m.group(3));
            rawLogRow.put("Message", m.group(4));
            rootNode.set("log_raw", rawLogRow);

        }
        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;


    }


}
