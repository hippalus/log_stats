package com.log.stats.loggenerator;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.log.stats.loggenerator.LogLevel.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogGeneratorTest {

    private final Date currentTime=new Date();
    private String formattedCurrentTime=Utils.dateToRFC3339(currentTime);


    @Test
    public void should_create_log_row(){

        LogRow logRowIst =LogRow.aNew()
                .date(currentTime)
                .city(new City("Istanbul"))
                .logLevel(DEBUG)
                .build();

        String expectedResultIst=formattedCurrentTime+"   "+"Istanbul   DEBUG   Hello From Istanbul";

        LogRow logRowTokyo =LogRow.aNew()
                .date(currentTime)
                .city(new City("Tokyo"))
                .logLevel(DEBUG)
                .build();

        String expectedResultTok=formattedCurrentTime+"   "+"Tokyo   DEBUG   Hello From Tokyo";

        assertEquals(expectedResultIst,logRowIst.toString());
        assertEquals(expectedResultTok,logRowTokyo.toString());
    }
    @Test
    public void should_write_log_rows_to_file(){
        LogRow logRowIst =LogRow.aNew()
                .date(currentTime)
                .city(new City("Istanbul"))
                .logLevel(DEBUG)
                .build();

  /*      try {
                int i=0;
                while (i<10000){
                LogFileWriter.write(logRowIst);
                i++;}
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }*/


    }

    @Test
    public void  should_random_log_level_create(){

         assertTrue( Arrays.asList("DEBUG","INFO","FATAL","ERROR","WARN").contains(randomLogLevel().toString()));
    }

    @Test
    public void  should_random_city_name_create(){
        List<City> cities=new ArrayList<>();
        cities.add(new City("Istanbul"));
        cities.add(new City("Tokyo"));
        cities.add(new City("Moscow"));
        cities.add(new City("Beijing"));
        cities.add(new City("London"));

        assertTrue(cities.contains(Utils.getRandomCity()));
    }
}
