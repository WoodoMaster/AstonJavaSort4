package org.aston.learning.stage1.util;

// Преобразование величин
public class ConvertUtils {

    // Перевод строки в Integer
    public static Integer parseIntOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Перевод строки в int cо значением по-умолчанию
    public static int parseIntOrDefault(String s, int def) {
        Integer v = parseIntOrNull(s);
        return v != null ? v : def;
    }

    // Перевод строки в Double
    public static Double parseDoubleOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Перевод строки в double cо значением по-умолчанию
    public static double parseDoubleOrDefault(String s, double def) {
        Double v = parseDoubleOrNull(s);
        return v != null ? v : def;
    }

    public static boolean isEven(int x) {
        return (x % 2) == 0;
    }
}
