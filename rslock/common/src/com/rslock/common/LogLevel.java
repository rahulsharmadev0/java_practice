package com.rslock.common;

import java.util.logging.Level;

/**
 * LogLevel enum mapping to java.util.logging levels
 * Provides a cleaner API while leveraging standard Java logging
 */
public enum LogLevel {
    FINEST(Level.FINEST),
    FINE(Level.FINE),
    INFO(Level.INFO),
    WARNING(Level.WARNING),
    SEVERE(Level.SEVERE);

    private final Level javaLevel;

    LogLevel(Level javaLevel) {
        this.javaLevel = javaLevel;
    }

    public Level getJavaLevel() {
        return javaLevel;
    }

    public boolean isEnabled(java.util.logging.Logger logger) {
        return logger.isLoggable(javaLevel);
    }
}
