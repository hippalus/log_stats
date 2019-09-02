package com.log.stats.loggenerator;

import lombok.Data;

import java.util.Random;

@Data
public class City {
    private final String name;
    private final  String message;

    private City(String name) {
        this.name = name;
        this.message= String.format("Hello From %s", this.name);
    }

    private City(String name, String message) {
        this.name = name;
        this.message=message;
    }
    private static final String[] cities = new String[]{"Istanbul", "Tokyo", "Moscow", "Beijing", "London"};

    public static City getRandomCity() {
        return new City(cities[new Random().nextInt(cities.length)]);
    }

    @Override
    public String toString() {
        return name;
    }
}
