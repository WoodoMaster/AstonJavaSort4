package org.aston.learning.stage1.menu;

import org.aston.learning.stage1.util.ConsoleUtils;
import org.aston.learning.stage1.util.FormatUtils;
import org.aston.learning.stage1.util.InputUtils;

import java.util.*;

public class CollectionManager<T> {
    private final String name;
    private final List<T> collection;

    public CollectionManager(String name) {
        this.name = name;
        this.collection = new ArrayList<>();
    }

    public String getName() { return name; }

    public void fillManual() {
        // TODO: Логика заполнения вручную

        System.out.println("Коллекция '" + name + "' заполнена вручную\n" +
                "Добавлено элементов: " + (collection.size() + 5) + "\n" +
                "Статус: Успешно завершено\n");

        ConsoleUtils.pause();
    }

    public void fillFile() {
        // TODO: Получение данных из файла и заполнение коллекции

        // *** Пример прогресс бара
        System.out.println("\nЗагрузка данных:");
        for (int i = 0; i <= 100; i++) {
            FormatUtils.showProgressBar(i, 100, 50);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Коллекция '" + name + "' заполнена из файла\n" +
                "Прочитано строк: 15\n" +
                "Обработано элементов: 12\n");

        ConsoleUtils.pause();
    }

    public void fillRandom() {
        // TODO: Получение ПЕРЕМЕШАННЫХ данных из файла и заполнение коллекции

        System.out.println("Коллекция '" + name + "' заполнена случайными данными\n" +
                "Сгенерировано элементов: 8\n" +
                "Диапазон значений: 1-100\n");

        ConsoleUtils.pause();
    }

    public void clear() {
        if (!InputUtils.readBoolean("Вы уверены что хотите очистить коллекцию '" + name + "'?")) {
            return;
        }

        // TODO: Очистка коллекции

        System.out.println("Коллекция '" + name + "' очищена\n" +
                "Время выполнения: 15ms\n");

        ConsoleUtils.pause();
    }

    public void setLength() {
        // TODO: Установка длинны для коллекции

        System.out.println("Установлена длина коллекции '" + name + "'\n" +
                "Новая длина: 10 элементов\n" +
                "Старые данные сохранены\n");

        ConsoleUtils.pause();
    }

    public void sort() {
        // TODO: Сортировка коллекции

        System.out.println("Коллекция '" + name + "' отсортирована\n" +
                "Алгоритм: Быстрая сортировка\n" +
                "Время выполнения: 15ms\n");

        ConsoleUtils.pause();
    }

    public void find() {
        // TODO: Поиск по коллекции

        System.out.println("Поиск в коллекции '" + name + "' выполнен\n" +
                "Найдено элементов: 3\n" +
                "Критерий поиска: значение > 50\n");

        ConsoleUtils.pause();
    }

    public void show() {
        // TODO: Отрисовка всей коллекции

        // *** Пример таблицы
        String[] headers = {"ID", "Имя", "Возраст", "Email"};
        String[][] data = {
                {"1", "Иван Иванов", "25", "ivan@mail.com"},
                {"2", "Мария Петрова", "30", "maria@gmail.com"},
                {"3", "Алексей Сидоров", "22", "alex@yandex.ru"}
        };

        FormatUtils.printTable(headers, data);

        StringBuilder result = new StringBuilder();
        result.append("Содержимое коллекции '").append(name).append("':\n");

        if (collection.isEmpty()) {
            result.append("Коллекция пуста");
        } else {
            for (int i = 0; i < Math.min(collection.size(), 5); i++) {
                result.append("Элемент ").append(i + 1).append(": [данные]\n");
            }
            if (collection.size() > 5) {
                result.append("... и еще ").append(collection.size() - 5).append(" элементов");
            }
        }
        System.out.println();
        ConsoleUtils.pause();
    }
}