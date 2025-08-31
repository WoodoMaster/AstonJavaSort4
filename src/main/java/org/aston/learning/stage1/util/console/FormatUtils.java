package org.aston.learning.stage1.util.console;

public class FormatUtils extends ConsoleUtils {
    public static void printTable(String[] headers, String[][] data) {
        // Вычисление максимальных ширины столбцов
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        // Вывод заголовка
        System.out.print(ConsoleColor.GREEN + "╔");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("═".repeat(columnWidths[i] + 2));
            if (i < headers.length - 1) System.out.print("╦");
        }
        System.out.println("╗" + ConsoleColor.RESET);

        System.out.print(ConsoleColor.GREEN + "║ ");
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + (columnWidths[i]) + "s ║ ", headers[i]);
        }
        System.out.println();

        System.out.print(ConsoleColor.GREEN + "╠");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("═".repeat(columnWidths[i] + 2));
            if (i < headers.length - 1) System.out.print("╬");
        }
        System.out.println("╣" + ConsoleColor.RESET);

        // Вывод данных
        for (String[] row : data) {
            System.out.print("║ ");
            for (int i = 0; i < row.length; i++) {
                String value = row[i] != null ? row[i] : "";
                System.out.printf("%-" + (columnWidths[i]) + "s ║ ", value);
            }
            System.out.println();
        }

        System.out.print(ConsoleColor.GREEN + "╚");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("═".repeat(columnWidths[i] + 2));
            if (i < headers.length - 1) System.out.print("╩");
        }
        System.out.println("╝" + ConsoleColor.RESET);
    }

    public static void showProgressBar(int current, int total, int width) {
        double progress = (double) current / total;
        int bars = (int) (progress * width);

        System.out.print("\r[");
        for (int i = 0; i < width; i++) {
            if (i < bars) {
                System.out.print(ConsoleColor.GREEN + "=" + ConsoleColor.RESET);
            } else {
                System.out.print(" ");
            }
        }
        System.out.printf("] %d%%", (int) (progress * 100));

        if (current == total) {
            System.out.println();
        }
    }
}