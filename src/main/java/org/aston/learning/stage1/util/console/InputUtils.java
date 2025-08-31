package org.aston.learning.stage1.util.console;

import java.util.InputMismatchException;

public class InputUtils extends ConsoleUtils {
    // Ввод целого числа с диапазоном
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(ConsoleColor.CYAN + prompt + ConsoleColor.RESET);
                int value = getScanner().nextInt();
                getScanner().nextLine(); // Очистка буфера

                if (value >= min && value <= max) {
                    return value;
                } else {
                    printError("Число должно быть от " + min + " до " + max);
                }
            } catch (InputMismatchException e) {
                printError("Пожалуйста, введите целое число!");
                getScanner().nextLine(); // Очистка неверного ввода
            }
        }
    }

    // Ввод дробного числа
    public static double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(ConsoleColor.CYAN + prompt + ConsoleColor.RESET);
                double value = getScanner().nextDouble();
                getScanner().nextLine();

                if (value >= min && value <= max) {
                    return value;
                } else {
                    printError("Число должно быть от " + min + " до " + max);
                }
            } catch (InputMismatchException e) {
                printError("Пожалуйста, введите число!");
                getScanner().nextLine();
            }
        }
    }

    // Ввод строки с проверкой на пустоту
    public static String readString(String prompt, boolean required) {
        while (true) {
            System.out.print(ConsoleColor.CYAN + prompt + ConsoleColor.RESET);
            String input = getScanner().nextLine().trim();

            if (required && input.isEmpty()) {
                printError("Это поле обязательно для заполнения!");
            } else {
                return input;
            }
        }
    }

    // Ввод булевого значения (да/нет)
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(ConsoleColor.YELLOW + prompt + " (y/n): " + ConsoleColor.RESET);
            String input = getScanner().nextLine().trim().toLowerCase();

            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("Yes")) {
                return true;
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("No")) {
                return false;
            } else {
                printError("Пожалуйста, введите 'y' или 'n'");
            }
        }
    }
}