package org.aston.learning.stage1.util.console;

import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    // Пауза выполнения
    public static void pause() {
        System.out.println(ConsoleColor.CYAN + "Нажмите Enter для продолжения..." + ConsoleColor.RESET);
        scanner.nextLine();
    }

    // Завершение программы
    public static void exit() {
        System.out.println(ConsoleColor.GREEN + "Выход из программы..." + ConsoleColor.RESET);
        System.exit(0);
    }

    // Вывод подсказки
    public static void printInfo(String message) {
        System.out.println(ConsoleColor.BLUE + "[i] " + message + ConsoleColor.RESET);
    }

    // Вывод ошибки
    public static void printError(String message) {
        System.out.println(ConsoleColor.RED + "[X] " + message + ConsoleColor.RESET);
    }

    // Вывод успеха
    public static void printSuccess(String message) {
        System.out.println(ConsoleColor.GREEN + "[√] " + message + ConsoleColor.RESET);
    }

    // Вывод предупреждения
    public static void printWarning(String message) {
        System.out.println(ConsoleColor.YELLOW + "[!] " + message + ConsoleColor.RESET);
    }
}