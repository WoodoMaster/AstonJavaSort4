package org.aston.learning.stage1.menu;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.ElementHandler;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.util.ExecutionTimer;
import org.aston.learning.stage1.util.console.ConsoleUtils;
import org.aston.learning.stage1.util.console.FormatUtils;
import org.aston.learning.stage1.util.console.InputUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class CollectionManager<T> {
    private final String name;
    private final CustomCollection<T> collection;
    private final ElementHandler<T> elementHandler;
    private final SearchStrategy<T> searchStrategy;
    private final SortStrategy<T> sortStrategy;
    private final FileLoadStrategy<T> fileLoadStrategy;
    private final FileSaveStrategy<T> fileSaveStrategy;
    private int capacity;
    private int size;
    public static int actionFieldIndex = 1;

    public CollectionManager(String name, ElementHandler<T> elementHandler,
                             SearchStrategy<T> searchStrategy, SortStrategy<T> sortStrategy,
                             FileLoadStrategy<T> fileLoadStrategy, FileSaveStrategy<T> fileSaveStrategy) {
        this.name = name;
        this.elementHandler = elementHandler;
        this.searchStrategy = searchStrategy;
        this.sortStrategy = sortStrategy;
        this.fileLoadStrategy = fileLoadStrategy;
        this.fileSaveStrategy = fileSaveStrategy;
        this.collection = new ArrayCollection<>();
        this.capacity = 100;
        this.size = 0;
    }

    public CollectionManager(String name, ElementHandler<T> elementHandler,
                             SearchStrategy<T> searchStrategy, SortStrategy<T> sortStrategy,
                             FileLoadStrategy<T> fileLoadStrategy, FileSaveStrategy<T> fileSaveStrategy, int initialCapacity) {
        this.name = name;
        this.elementHandler = elementHandler;
        this.searchStrategy = searchStrategy;
        this.sortStrategy = sortStrategy;
        this.fileLoadStrategy = fileLoadStrategy;
        this.fileSaveStrategy = fileSaveStrategy;
        this.collection = new ArrayCollection<>();
        this.capacity = initialCapacity;
        this.size = 0;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public int getActionFieldIndex() {
        return actionFieldIndex;
    }

    public ElementHandler<T> getElementHandler() {
        return elementHandler;
    }

    public void fillManual() {
        System.out.println("=== Ручное заполнение ===");

        if (size >= capacity) {
            ConsoleUtils.printWarning("Коллекция переполнена!\n");
            ConsoleUtils.pause();
            return;
        }

        int elementsToAdd = InputUtils.readInt(
                "Сколько элементов добавить? (доступно: " + (capacity - size) + "): ",
                1, capacity - size
        );

        for (int i = 0; i < elementsToAdd; i++) {
            System.out.println("\n--- Элемент " + (size + 1) + " ---");
            T element = elementHandler.createElementManually();
            collection.add(element);
            size++;
            ConsoleUtils.printInfo("Элемент добавлен (" + (i + 1) + "/" + elementsToAdd + ")");
        }

        System.out.println("\nКоллекция '" + name + "' заполнена вручную\n" +
                "Добавлено элементов: " + elementsToAdd + "\n");

        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        ConsoleUtils.pause();
    }

    public void fillFile() {
        String filename = InputUtils.readString("Введите путь до файла: ", true);

        ExecutionTimer.TimedResult<CustomCollection<T>> timedResult = ExecutionTimer.measureExecutionTime(() -> {
            // TODO: Получение данных из файла и заполнение коллекции
            // *** Пример - загрузка из файла
            try {
                return fileLoadStrategy.loadFromFile(filename);
            } catch (IOException e) {
                System.out.println("Ошибка загрузки: " + e.getMessage());
                return new ArrayCollection<>();
            }
        });

        CustomCollection<T> loadedData = timedResult.result();
        long loadTime = timedResult.timeMs();

        if (loadedData.isEmpty()) {
            ConsoleUtils.printError("Статус: Операция прервана\n");
            ConsoleUtils.pause();
            return;
        }

        int canAdd = Math.min(loadedData.size(), capacity - size);
        if (canAdd == 0) {
            System.out.println("Нет места для новых элементов!");
            ConsoleUtils.printError("Статус: Операция прервана\n");
            ConsoleUtils.pause();
            return;
        }

        // Добавляем элементы (тоже замеряем время)
        long addTime = ExecutionTimer.measureExecutionTime(() -> {
            for (int i = 0; i < canAdd; i++) {
                collection.add(loadedData.get(i));
                size++;
            }
        });

        System.out.println("Коллекция '" + name + "' заполнена из файла " + filename + "\n" +
                "Загружено элементов: " + canAdd + "\n" +
                "Время загрузки: " + ExecutionTimer.formatTime(loadTime) + "\n" +
                "Время добавления: " + ExecutionTimer.formatTime(addTime) + "\n" +
                "Формат файла: " + fileLoadStrategy.getFileFormatDescription() + "\n");

        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        ConsoleUtils.pause();
    }

    public void fillRandom() {
        try {
            // Предлагаем выбор: использовать файл по умолчанию или указать свой
            System.out.println("Выберите источник данных:");
            System.out.println("1. Использовать демонстративный файл по умолчанию");
            System.out.println("2. Указать другой файл");

            int choice = InputUtils.readInt("Ваш выбор (1-2): ", 1, 2);

            String filename;
            CustomCollection<T> allData;
            if (choice == 1) {
                InputStream is = ClassLoader.getSystemResourceAsStream(getDefaultDataFile());
                allData = fileLoadStrategy.loadFromFile(is);
            } else {
                filename = InputUtils.readString("Введите путь к файлу: ", true);
                allData = fileLoadStrategy.loadFromFile(filename);
            }

            // Загружаем данные из файла
            if (allData.isEmpty()) {
                ConsoleUtils.printError("Нет данных для заполнения в файле");
                ConsoleUtils.printInfo("Проверьте формат файла и повторите попытку");
                ConsoleUtils.pause();
                return;
            }

            // Предлагаем выбрать количество элементов для добавления
            int maxElements = Math.min(allData.size(), capacity - size);
            System.out.println("Доступно элементов в файле: " + allData.size());
            System.out.println("Можно добавить: " + maxElements + " элементов");

            int elementsToAdd;
            if (maxElements > 1) {
                elementsToAdd = InputUtils.readInt(
                        "Сколько элементов добавить? (1-" + maxElements + "): ",
                        1, maxElements
                );
            } else if (maxElements == 1) {
                elementsToAdd = 1;
                System.out.println("Будет добавлен 1 элемент");
            } else {
                ConsoleUtils.printWarning("Нет места для новых элементов!");
                ConsoleUtils.pause();
                return;
            }

            // Перемешиваем данные
            CustomCollection<T> shuffledData = shuffleCollection(allData);

            // Добавляем элементы
            long addTime = ExecutionTimer.measureExecutionTime(() -> {
                for (int i = 0; i < elementsToAdd; i++) {
                    collection.add(shuffledData.get(i));
                    size++;
                }
            });

            // Показываем прогресс-бар
            System.out.println("\nДобавление элементов:");
            for (int i = 0; i <= 100; i++) {
                FormatUtils.showProgressBar(i, 100, 50);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("\nКоллекция '" + name + "' заполнена случайными данными");
            System.out.println("Добавлено элементов: " + elementsToAdd);
            System.out.println("Всего доступно в файле: " + allData.size());
            System.out.println("Время добавления: " + ExecutionTimer.formatTime(addTime));
            System.out.println();

            ConsoleUtils.printSuccess("Статус: Успешно завершено\n");

        } catch (IOException e) {
            ConsoleUtils.printError("Ошибка загрузки данных: " + e.getMessage());
            ConsoleUtils.printInfo("Проверьте формат файла и повторите попытку");
        } catch (Exception e) {
            ConsoleUtils.printError("Неожиданная ошибка: " + e.getMessage());
        }
        ConsoleUtils.pause();
    }

    // Метод для определения файла данных по типу коллекции
    private String getDefaultDataFile() {
        String typeName = elementHandler.getTypeName().toLowerCase();

        return switch (typeName) {
            case "student" -> "StudentData.csv";
            case "bus" -> "BusData.csv";
            case "user" -> "UserData.csv";
            default -> throw new IllegalArgumentException("Неизвестный тип коллекции: " + typeName);
        };
    }

    private CustomCollection<T> shuffleCollection(CustomCollection<T> original) {
        // Преобразуем в массив для перемешивания
        T[] array = original.toArray();
        // Перемешиваем
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            T temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        // Создаем новую коллекцию
        CustomCollection<T> shuffled = new ArrayCollection<>();
        for (T element : array) {
            shuffled.add(element);
        }
        return shuffled;
    }

    // Запись в файл на рабочем столе
    public void saveToFile() {
        if (collection.isEmpty()) {
            ConsoleUtils.printInfo("Коллекция пуста\n");
            return;
        }

        try {
            fileSaveStrategy.saveToFile(collection);
            ConsoleUtils.printSuccess("Данные сохранены в файл в корневой папке");
        } catch (IOException e) {
            ConsoleUtils.printError("Ошибка сохранения: " + e.getMessage());
        }
        ConsoleUtils.pause();
    }

    public void saveToFile(CustomCollection<T> collection) {
        if (collection.isEmpty()) {
            ConsoleUtils.printInfo("Коллекция пуста\n");
            return;
        }

        String filename = InputUtils.readString("Введите имя файла: ", true);

        try {
            fileSaveStrategy.saveToFile(collection);
            ConsoleUtils.printSuccess("Данные сохранены в файл: " + filename);
        } catch (IOException e) {
            ConsoleUtils.printError("Ошибка сохранения: " + e.getMessage());
        }
    }

    public void clear() {
        if (collection.isEmpty()) {
            ConsoleUtils.printWarning("Коллекция уже пуста!\n");
            ConsoleUtils.pause();
            return;
        }

        if (InputUtils.readBooleanInverted("Вы уверены что хотите очистить коллекцию '" + name + "'?")) {
            System.out.println();
            ConsoleUtils.printInfo("Операция отменена\n");
            return;
        }

        // *** Пример прогресс бара
        System.out.println("\nОчистка коллекции:");
        for (int i = 0; i <= 100; i++) {
            FormatUtils.showProgressBar(i, 100, 50);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Сохраняем количество элементов для вывода в отчет
        int savedSize = collection.size();

        long timeTaken = ExecutionTimer.measureExecutionTime(() -> {
            collection.clear();
            size = 0;
        });

        System.out.println("\nКоллекция '" + name + "' очищена\n" +
                "Удалено элементов: " + savedSize + "\n" +
                "Время выполнения: " + ExecutionTimer.formatTime(timeTaken) + "\n");

        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        ConsoleUtils.pause();
    }

    public void setLength() {
        System.out.println("Текущий размер коллекции: " + size);
        System.out.println("Текущая вместимость: " + capacity + "\n");

        int newCapacity = InputUtils.readInt("Введите новую вместимость: ", 1, 1000);

        if (newCapacity < size) {
            if (InputUtils.readBooleanInverted("Внимание! Новая вместимость меньше текущего размера. Продолжить?")) {
                System.out.println("Операция отменена.");
                return;
            }
            resizeCollection(newCapacity);
        }

        this.capacity = newCapacity;

        System.out.println("\nУстановлена длина коллекции '" + name + "'\n" +
                "Новая длина: " + capacity + " элементов\n" +
                "Старые данные сохранены\n");

        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        ConsoleUtils.pause();
    }

    public void sort() {
        if (collection.isEmpty()) {
            ConsoleUtils.printInfo("Коллекция пуста\n");
            ConsoleUtils.pause();
            return;
        }


        System.out.println("Сортировка: " + sortStrategy.getSortDescription());

        long timeTaken = ExecutionTimer.measureExecutionTime(() -> {
            sortStrategy.sort(collection, actionFieldIndex);
        });

        System.out.println("Коллекция '" + name + "' отсортирована\n" +
                "Алгоритм: Быстрая сортировка\n" +
                "Время выполнения: " + ExecutionTimer.formatTime(timeTaken) + "\n");

        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        if(!InputUtils.readBooleanInverted("Хотите записать отсортированную коллекцию " + name + " в файл?")) {
            saveToFile(collection);
            ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        }
        ConsoleUtils.pause();
    }

    public void find() {
        if (collection.isEmpty()) {
            ConsoleUtils.printInfo("Коллекция пуста\n");
            ConsoleUtils.pause();
            return;
        }

        String query = InputUtils.readString("Введите поисковый запрос: ", true);

        ExecutionTimer.TimedResult<CustomCollection<T>> timedResult = ExecutionTimer.measureExecutionTime(() ->
                searchStrategy.search(collection, actionFieldIndex, query)
        );

        CustomCollection<T> results = timedResult.result();
        long timeTaken = timedResult.timeMs();

        if (!results.isEmpty()) {
            System.out.println("-".repeat(50));
            showCollectionTable(results, 10);
            System.out.println("-".repeat(50));
        } else {
            System.out.println();
        }

        System.out.println("Поиск в коллекции '" + name + "' выполнен\n" +
                "Найдено элементов: " + results.size() + "\n" +
                "Критерий поиска: " + searchStrategy.getSearchDescription() + "\n" +
                "Время выполнения: " + ExecutionTimer.formatTime(timeTaken) + "\n");

        if(!InputUtils.readBooleanInverted("Хотите записать результаты поиска в файл?")) {
            saveToFile(results);
        }
        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        ConsoleUtils.pause();
    }

    public void show() {
        System.out.println("=== Коллекция: " + name + " ===");
        System.out.println("Размер: " + size + "/" + capacity);
        System.out.println("Тип: " + elementHandler.getTypeName());

        if (collection.isEmpty()) {
            System.out.println();
            ConsoleUtils.printInfo("Коллекция пуста\n");
            ConsoleUtils.pause();
            return;
        }

        System.out.println("-".repeat(50));
        showCollectionTable(collection);
        System.out.println("-".repeat(50));
        System.out.println("Всего элементов: " + size + "\n");

        ConsoleUtils.pause();
    }

    private void resizeCollection(int newCapacity) {
        while (collection.size() > newCapacity) {
            collection.remove(collection.size() - 1);
        }
        size = newCapacity;
    }

    private void showCollectionTable(CustomCollection<T> collectionToShow) {
        this.showCollectionTable(collectionToShow, 0);
    }

    private void showCollectionTable(CustomCollection<T> collectionToShow, int maxItemsToShow) {
        int collectionToShowSize = collectionToShow.size();
        int rowsToShow = maxItemsToShow > 0 ? Math.min(collectionToShowSize, maxItemsToShow) : collectionToShowSize;

        String[] headers = elementHandler.getTableHeaders();
        String[][] data = new String[rowsToShow][headers.length];

        for (int i = 0; i < rowsToShow; i++) {
            data[i] = elementHandler.convertToTableRow(collectionToShow.get(i), i);
        }

        FormatUtils.printTable(headers, data);

        if (collectionToShowSize > rowsToShow) {
            System.out.println("... и еще " + (collectionToShowSize - rowsToShow) + " элемент(ов)");
        }
    }


}