package com.log.stats.loggenerator;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
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

        List<String> cities=new ArrayList<>();
        cities.add( "Istanbul");
        cities.add("Tokyo");
        cities.add("Moscow");
        cities.add("Beijing");
        cities.add("London");

        Assertions.assertAll(
                () -> assertTrue(cities.contains(City.getRandomCity().getName()))
        );

        Assertions.assertAll(
                () -> {
                    City city=City.getRandomCity();
        assertEquals(String.format("Hello From %s", city.getName()),city.getMessage());

    });}

    @Test
    public  void should_random_city_logger(){
        City city=City.getRandomCity();
        Logger logger=LogLevel.getLoggerByCity(city);
        assertEquals(city.getName(),logger.getName());

    }
}
