package org.aston.learning.stage1.util.console;

public enum ConsoleColor {
    RESET("\033[0m"),

    // Цвета текста
    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    PURPLE("\033[0;35m"),
    CYAN("\033[0;36m"),
    WHITE("\033[0;37m"),

    // Жирный текст
    BLACK_BOLD("\033[1;30m"),
    RED_BOLD("\033[1;31m"),
    GREEN_BOLD("\033[1;32m"),
    YELLOW_BOLD("\033[1;33m"),
    BLUE_BOLD("\033[1;34m"),
    PURPLE_BOLD("\033[1;35m"),
    CYAN_BOLD("\033[1;36m"),
    WHITE_BOLD("\033[1;37m"),

    // Подчеркнутый текст
    BLACK_UNDERLINED("\033[4;30m"),
    RED_UNDERLINED("\033[4;31m"),
    GREEN_UNDERLINED("\033[4;32m"),
    YELLOW_UNDERLINED("\033[4;33m"),
    BLUE_UNDERLINED("\033[4;34m"),
    PURPLE_UNDERLINED("\033[4;35m"),
    CYAN_UNDERLINED("\033[4;36m"),
    WHITE_UNDERLINED("\033[4;37m"),

    // Фон
    BLACK_BACKGROUND("\033[40m"),
    RED_BACKGROUND("\033[41m"),
    GREEN_BACKGROUND("\033[42m"),
    YELLOW_BACKGROUND("\033[43m"),
    BLUE_BACKGROUND("\033[44m"),
    PURPLE_BACKGROUND("\033[45m"),
    CYAN_BACKGROUND("\033[46m"),
    WHITE_BACKGROUND("\033[47m");

    private final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static String colorize(String text, ConsoleColor color) {
        return color + text + RESET;
    }

    public static void printColor(String text, ConsoleColor color) {
        System.out.print(colorize(text, color));
    }

    public static void printlnColor(String text, ConsoleColor color) {
        System.out.println(colorize(text, color));
    }

    public static void main(String[] args) {
        printlnColor("Успех!", ConsoleColor.GREEN_BOLD);
        printlnColor("Ошибка!", ConsoleColor.RED_BOLD);
        printlnColor("Предупреждение!", ConsoleColor.YELLOW_BOLD);
        printlnColor("Информация", ConsoleColor.BLUE_BOLD);
    }
}