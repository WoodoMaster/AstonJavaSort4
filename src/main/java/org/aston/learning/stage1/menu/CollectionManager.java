package org.aston.learning.stage1.menu;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.ElementHandler;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.util.ExecutionTimer;
import org.aston.learning.stage1.util.console.ConsoleUtils;
import org.aston.learning.stage1.util.console.FormatUtils;
import org.aston.learning.stage1.util.console.InputUtils;

import java.io.IOException;

public class CollectionManager<T> {
    private final String name;
    private final CustomCollection<T> collection;
    private final ElementHandler<T> elementHandler;
    private final SearchStrategy<T> searchStrategy;
    private final SortStrategy<T> sortStrategy;
    private final FileLoadStrategy<T> fileLoadStrategy;
    private int capacity;
    private int size;
    public static int actionFieldIndex = 1;

    public CollectionManager(String name, ElementHandler<T> elementHandler,
                             SearchStrategy<T> searchStrategy, SortStrategy<T> sortStrategy,
                             FileLoadStrategy<T> fileLoadStrategy) {
        this.name = name;
        this.elementHandler = elementHandler;
        this.searchStrategy = searchStrategy;
        this.sortStrategy = sortStrategy;
        this.fileLoadStrategy = fileLoadStrategy;
        this.collection = new ArrayCollection<>();
        this.capacity = 100;
        this.size = 0;
    }

    public CollectionManager(String name, ElementHandler<T> elementHandler,
                             SearchStrategy<T> searchStrategy, SortStrategy<T> sortStrategy,
                             FileLoadStrategy<T> fileLoadStrategy, int initialCapacity) {
        this.name = name;
        this.elementHandler = elementHandler;
        this.searchStrategy = searchStrategy;
        this.sortStrategy = sortStrategy;
        this.fileLoadStrategy = fileLoadStrategy;
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

    // Сеттеры
    public void setActionFieldIndex(int actionFieldIndex) {
        this.actionFieldIndex = actionFieldIndex;
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
        String filename = InputUtils.readString("Введите имя файла: ", true);

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
        // TODO: Получение ПЕРЕМЕШАННЫХ данных из файла и заполнение коллекции

        System.out.println("Коллекция '" + name + "' заполнена случайными данными\n" +
                "Сгенерировано элементов: 8\n" +
                "Диапазон значений: 1-100\n");

        ConsoleUtils.printSuccess("Статус: Успешно завершено\n");
        ConsoleUtils.pause();
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