package com.gridnine.testing.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private Log() {
    }

    private static final Logger LOGGER = LogManager.getLogger("Log");

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void error(Exception e) {
        LOGGER.error(e);
        for (StackTraceElement s : e.getStackTrace()) {
            String err = String.format("\tat %s", s);
            LOGGER.error(err);
        }
    }
}
