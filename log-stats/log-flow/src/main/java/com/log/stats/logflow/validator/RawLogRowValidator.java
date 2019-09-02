package com.log.stats.logflow.validator;

import com.log.stats.logflow.entity.LogRow;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class RawLogRowValidator {
    private RawLogRowValidator() {
     throw new AssertionError();
    }

    private static Map<Long,String> cityRepository=new HashMap<>();

    private static Set<String> getLogLevel() {
        return logLevel;
    }

    private static Set<String> logLevel=new HashSet<>();
    static{
        logLevel.add("DEBUG");
        logLevel.add("WARN");
        logLevel.add("ERROR");
        logLevel.add("FATAL");
        logLevel.add("INFO");

    }


    static {
        cityRepository.put(34L,"Istanbul");
        cityRepository.put(2L,"Tokyo");
        cityRepository.put(3L,"Moscow");
        cityRepository.put(4L,"Beijing");
        cityRepository.put(5L,"London");

    }
    private static Map<Long, String> getCityRepository() {
        return cityRepository;
    }


    public static boolean run(LogRow logRow){

        return getCityRepository().containsValue(logRow.getCity()) &&  getLogLevel().contains(logRow.getLogLevel()) ;
    }

}
