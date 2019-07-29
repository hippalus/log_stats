package com.log.stats.loggenerator;

import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode
public class LogRow {

    private final String date;
    private final City city;
    private final LogLevel logLevel;
    private  String message;

    public LogRow(Date date, City city, LogLevel logLevel) {
        this.date = Utils.dateToRFC3339(date);
        this.city = city;
        this.logLevel = logLevel;
        this.message = city.getMessage();
    }

    public String getDate() {
        return date;
    }

    public City getCity() {
        return city;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }
    public static Builder aNew(){
        return new Builder();
    }
    public static class Builder{
        private Date date;
        private  City city;
        private  LogLevel logLevel;

        public Builder date(Date date){
            this.date=date;
            return this;
        }
        public Builder city(City city){
            this.city=city;
            return this;
        }
        public Builder logLevel(LogLevel logLevel){
            this.logLevel=logLevel;
            return this;
        }
        public LogRow build(){
            checkDataCell();
            return new LogRow(this.date,this.city,this.logLevel);
        }

        private void checkDataCell() {
            if (this.date==null) throw new PropertyRequiredException( "LogRow","date" );
            if (this.logLevel==null) throw new PropertyRequiredException( "LogRow","logLevel" );
            if (this.city==null) throw new PropertyRequiredException( "LogRow","city" );
        }


    }

    @Override
    public String toString() {
        return String.format("%s   %s   %s   %s", date, city.toString(), logLevel, message);
    }
}
