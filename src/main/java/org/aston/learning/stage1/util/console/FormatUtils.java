package org.aston.learning.stage1.util.console;

public class FormatUtils extends ConsoleUtils {
    public static void printTable(String[] headers, String[][] data) {
        int[] columnWidths = calculateColumnWidths(headers, data);
        printTableHeader(headers, columnWidths);
        printTableData(headers, data, columnWidths);
        printTableFooter(headers, columnWidths);
    }

    private static int[] calculateColumnWidths(String[] headers, String[][] data) {
        int[] columnWidths = new int[headers.length];

        // Устанавливаем минимальную ширину по заголовкам
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Находим максимальную ширину по данным
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null) {
                    columnWidths[i] = Math.max(columnWidths[i], row[i].length());
                }
            }
        }

        return columnWidths;
    }

    private static void printTableHeader(String[] headers, int[] columnWidths) {
        // Верхняя граница
        System.out.print(ConsoleColor.GREEN + "╔");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("═".repeat(columnWidths[i] + 2));
            if (i < headers.length - 1) System.out.print("╦");
        }
        System.out.println("╗" + ConsoleColor.RESET);

        // Заголовки
        System.out.print(ConsoleColor.GREEN + "║ ");
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + columnWidths[i] + "s ║ ", headers[i]);
        }
        System.out.println();

        // Разделитель заголовков и данных
        System.out.print(ConsoleColor.GREEN + "╠");
        for (int i = 0; i < headers.length; i++) {
            System.out.print("═".repeat(columnWidths[i] + 2));
            if (i < headers.length - 1) System.out.print("╬");
        }
        System.out.println("╣" + ConsoleColor.RESET);
    }

    private static void printTableData(String[] headers, String[][] data, int[] columnWidths) {
        for (String[] row : data) {
            printTableRow(row, columnWidths);
        }
    }

    private static void printTableRow(String[] row, int[] columnWidths) {
        System.out.print("║ ");
        for (int i = 0; i < row.length; i++) {
            String value = (row[i] != null) ? row[i] : "";
            System.out.printf("%-" + columnWidths[i] + "s ║ ", value);
        }
        System.out.println();
    }

    private static void printTableFooter(String[] headers, int[] columnWidths) {
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