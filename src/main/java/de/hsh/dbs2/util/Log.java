package de.hsh.dbs2.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class Log {

    private static LogLevel currLogLevel = LogLevel.INFO;

    public static void setLogLevel(LogLevel logLevel) {
        currLogLevel = logLevel;
    }

    public static void debug(Object... args) {
        if (currLogLevel.isLowerOrEqual(LogLevel.DEBUG)) defaultLog(LogLevel.DEBUG, args);
    }

    public static void info(Object... args) {
        if (currLogLevel.isLowerOrEqual(LogLevel.INFO)) defaultLog(LogLevel.INFO, args);
    }

    public static void warn(Object... args) {
        if (currLogLevel.isLowerOrEqual(LogLevel.WARN)) defaultLog(LogLevel.WARN, args);
    }

    public static void error(Object... args) {
        if (currLogLevel.isLowerOrEqual(LogLevel.ERROR)) defaultLog(LogLevel.ERROR, args);
    }

    public static void error(Exception e) {
        StringWriter stackTraceErros = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTraceErros));

        if (currLogLevel.isLowerOrEqual(LogLevel.ERROR))
            defaultLog(LogLevel.ERROR, e.getMessage(), "\n", stackTraceErros.toString());
    }

    public static void defaultLog(LogLevel logLevel, Object... args) {
        System.out.printf(getCallerInfo() + " %s: ", logLevel);
        Arrays.stream(args).forEach(o -> System.out.print(o == null ? null : (o.toString() + "  ")));
        System.out.println();
    }

    private static CallerInfo getCallerInfo() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return new CallerInfo(ste.getClassName(), ste.getLineNumber());
            }
        }
        return null;
    }


    public enum LogLevel {
        VERBOSE(0, "V"),
        DEBUG(1, "D"),
        INFO(2, "I"),
        WARN(3, "W"),
        ERROR(4, "E"),
        OFF(5, "?");

        private int value;
        private String type;

        private LogLevel(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public Boolean isLowerOrEqual(LogLevel other) {
            return this.value <= other.value;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }

    private static class CallerInfo {
        final String className;
        final int line;

        CallerInfo(String className, int line) {
            this.className = className;
            this.line = line;
        }

        @Override
        public String toString() {
            return String.format("[%s (%d)]", className, line);
        }
    }

}
