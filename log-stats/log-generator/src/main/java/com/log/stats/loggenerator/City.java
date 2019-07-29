package com.log.stats.loggenerator;

import lombok.Data;

@Data
public class City {
    private final String name;
    private final  String message;

    public City(String name) {
        this.name = name;
        this.message= String.format("Hello From %s", this.name);
    }

    public City(String name, String message) {
        this.name = name;
        this.message=message;
    }

    @Override
    public String toString() {
        return name;
    }
}
