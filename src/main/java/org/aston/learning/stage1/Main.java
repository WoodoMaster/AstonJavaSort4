package org.aston.learning.stage1;

import org.aston.learning.stage1.menu.*;
import org.aston.learning.stage1.model.*;
import org.aston.learning.stage1.util.console.ConsoleUtils;

public class Main {
    private static Menu collectionSelectMenu;
    private static Menu fillMenu;
    private static Menu searchMenu;
    private static Menu sortMenu;
    private static Menu operationsMenu;
    private static final String MAIN_MENU_TITLE_TEXT = "Главное меню";

    public static void main(String[] args) {
        /* ========================== Создание коллекций ========================== */
        CollectionManager<Student> studentManager = CollectionManagerFactory.createStudentManager("Студенты", 30);
        CollectionManager<Bus> busManager = CollectionManagerFactory.createBusManager("Автобусы", 5);
        CollectionManager<User> userManager = CollectionManagerFactory.createUserManager("Пользователи");

        /* ========================== Установка коллекции по умолчанию ========================== */
        CurrentCollectionManager.setCurrent(studentManager, studentManager.getName());

        /* ========================== Меню выбора коллекции ========================== */
        collectionSelectMenu = new Menu("Выбор коллекции", MAIN_MENU_TITLE_TEXT)
                .addAction(studentManager.getName(), () -> selectCollection(studentManager))
                .addAction(busManager.getName(), () -> selectCollection(busManager))
                .addAction(userManager.getName(), () -> selectCollection(userManager));

        /* ========================== Меню заполнения коллекции ========================== */
        fillMenu = new Menu("Заполнение коллекции", MAIN_MENU_TITLE_TEXT)
                .addAction("Заполнить вручную", () -> executeFillAction(CollectionManager::fillManual, "Заполнение вручную"))
                .addAction("Заполнить из файла", () -> executeFillAction(CollectionManager::fillFile, "Заполнение из файла"))
                .addAction("Заполнить случайно", () -> executeFillAction(CollectionManager::fillRandom, "Случайное заполнение"))
                .addAction("Очистить коллекцию", () -> executeFillAction(CollectionManager::clear, "Очистка коллекции"));

        /* ========================== Меню поиска по коллекции ========================== */
        searchMenu = new Menu("Поиск по коллекции", MAIN_MENU_TITLE_TEXT)
                .addAction("Поиск по полю 1", () -> executeFindAction(CollectionManager::find, 1, "Поиск по полю 1"))
                .addAction("Поиск по полю 2", () -> executeFindAction(CollectionManager::find, 2, "Поиск по полю 2"))
                .addAction("Поиск по полю 3", () -> executeFindAction(CollectionManager::find, 3, "Поиск по полю 3"));

        /* ========================== Меню сортировки коллекции ========================== */
        sortMenu = new Menu("Сортировка коллекции", MAIN_MENU_TITLE_TEXT)
                .addAction("Сортировка по полю 1", () -> executeFindAction(CollectionManager::sort, 1, "Сортировка по полю 1"))
                .addAction("Сортировка по полю 2", () -> executeFindAction(CollectionManager::sort, 2,"Сортировка по полю 2"))
                .addAction("Сортировка по полю 3", () -> executeFindAction(CollectionManager::sort, 3,"Сортировка по полю 3"))
                .addAction("Особая сортировка", () -> executeFindAction(CollectionManager::sort, 4,"Особая сортировка"));


        /* ========================== Меню действий над коллекцией ========================== */
        operationsMenu = new Menu("Действия над коллекцией", MAIN_MENU_TITLE_TEXT)
                .addAction("Установить длину", () -> executeOperationAction(CollectionManager::setLength, "Установка длины"))
                .addAction("Отсортировать", () -> executeOperationAction(CollectionManager::sort, "Сортировка"))
                .addAction("Найти", () -> executeOperationAction(CollectionManager::find, "Поиск"))
                .addAction("Просмотреть", () -> executeOperationAction(CollectionManager::show, "Просмотр"))
                .addAction("Записать коллекцию в файл", () -> executeFillAction(CollectionManager::saveToFile, "Запись в файл"))
                .addAction("Отсортировать", sortMenu::open)
                .addAction("Найти", searchMenu::open)
                .addAction("Просмотреть", () -> executeOperationAction(CollectionManager::show, "Просмотр"));


        /* ========================== Главное меню ========================== */
        new Menu(MAIN_MENU_TITLE_TEXT)
                .addAction(collectionSelectMenu.getTitle(), collectionSelectMenu::open)
                .addAction(fillMenu.getTitle(), fillMenu::open)
                .addAction(operationsMenu.getTitle(), operationsMenu::open)
                .open();
    }

    private static void selectCollection(CollectionManager<?> manager) {
        CurrentCollectionManager.setCurrent(manager, manager.getName());
        ConsoleUtils.printInfo("Выбрана коллекция: " + manager.getName() + "\n");
        collectionSelectMenu.close();
    }

    private static void executeFillAction(CollectionAction action, String actionTitle) {
        if (!CurrentCollectionManager.isSelected()) {
            ConsoleUtils.printError("Сначала выберите коллекцию!");
            return;
        }

        fillMenu.displayHeader(actionTitle);
        CollectionManager<?> manager = CurrentCollectionManager.getCurrent();
        action.execute(manager);
    }

    private static void executeOperationAction(CollectionAction action, String actionTitle) {
        if (!CurrentCollectionManager.isSelected()) {
            ConsoleUtils.printError("Сначала выберите коллекцию!");
            return;
        }

        operationsMenu.displayHeader(actionTitle);
        CollectionManager<?> manager = CurrentCollectionManager.getCurrent();
        action.execute(manager);
    }

    private static void executeFindAction(CollectionAction action, int fieldIndex, String actionTitle) {
        if (!CurrentCollectionManager.isSelected()) {
            ConsoleUtils.printError("Сначала выберите коллекцию!");
            return;
        }

        fillMenu.displayHeader(actionTitle);
        CollectionManager<?> manager = CurrentCollectionManager.getCurrent();
        CollectionManager.actionFieldIndex = fieldIndex;
        action.execute(manager);
    }
}