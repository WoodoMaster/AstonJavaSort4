package org.aston.learning.stage1.util;

import java.util.function.Supplier;

public class ExecutionTimer {

    // Обертка для Runnable
    public static long measureExecutionTime(Runnable action) {
        long startTime = System.currentTimeMillis();
        action.run();
        return System.currentTimeMillis() - startTime;
    }

    // Обертка для Supplier с возвращаемым значением
    public static <T> TimedResult<T> measureExecutionTime(Supplier<T> action) {
        long startTime = System.currentTimeMillis();
        T result = action.get();
        long timeTaken = System.currentTimeMillis() - startTime;
        return new TimedResult<>(result, timeTaken);
    }

    // Класс для хранения результата и времени
    public static class TimedResult<T> {
        private final T result;
        private final long timeMs;

        public TimedResult(T result, long timeMs) {
            this.result = result;
            this.timeMs = timeMs;
        }

        public T getResult() { return result; }
        public long getTimeMs() { return timeMs; }
    }

    // Метод для форматирования времени
    public static String formatTime(long timeMs) {
        if (timeMs < 1000) {
            return timeMs + "ms";
        } else {
            return String.format("%.2fs", timeMs / 1000.0);
        }
    }
}