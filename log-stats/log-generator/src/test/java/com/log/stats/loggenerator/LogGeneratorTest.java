package com.log.stats.loggenerator;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.log.stats.loggenerator.LogLevel.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogGeneratorTest {
    @Test
    public void  should_random_log_level_create() {

        assertTrue(Arrays.asList("DEBUG", "INFO", "FATAL", "ERROR", "WARN").contains(randomLogLevel().toString()));
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
    @Test
    public  void should_random_city_logger(){
        City city=Utils.getRandomCity();
        Logger logger=Utils.getLoggerByCity(city);
        assertEquals(city.getName(),logger.getName());

    }
}
