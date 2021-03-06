package com.log.stats.logflow.utils;

import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.shade.org.json.simple.parser.JSONParser;

import java.text.ParseException;

public class Utils {
    private Utils() {
    throw new AssertionError();
    }

    public static synchronized JSONObject convertStringToJSON(String jsonStr){
        try {
            String json = jsonStr.replaceAll("\'", "\"");
            return (JSONObject) new JSONParser().parse(json);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
