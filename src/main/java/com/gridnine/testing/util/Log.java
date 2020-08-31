package com.gridnine.testing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

    public static void info (String msg) {
        LOGGER.info(msg);
    }

    public static void error(Exception e) {
        LOGGER.error(e.getMessage());
    }

    public static void error(String s, Exception e) {
        LOGGER.error(s);
    }
}
