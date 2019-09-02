package com.log.stats.logflow.topology;

public final class Constants {
    private Constants() {
        throw new AssertionError();
    }

    public static final class Topics {
        private Topics() {
            throw new AssertionError();
        }

        public static final String CITY_LOG_INVALID = "cityLog-invalid";
        public static final String CITY_LOG_VALID = "cityLog-valid";
        public static final String CITY_LOG_RAW = "cityLog-raw";

    }

    public static final String CITY_LOGS = "city-logs";

    public static final class Stream {

        private Stream() {
            throw new AssertionError();
        }

        public static final String VALID_LOG_STREAM = "valid-log-stream";
        public static final String INVALID_LOG_STREAM = "invalid-log-stream";
    }

    public static final class TupleFields {
        private TupleFields() {
            throw new AssertionError();
        }

        public static final String VALID_LOG = "valid-log";
        public static final String INVALID_LOG = "invalid-log";
        public static final String LOG_RAW = "log_raw";
    }


}
