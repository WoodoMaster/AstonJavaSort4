package org.aston.learning.stage1;

import org.aston.learning.stage1.menu.*;
import org.aston.learning.stage1.model.*;
import org.aston.learning.stage1.util.console.ConsoleUtils;

public class Main {
    private static Menu collectionSelectMenu;
    private static Menu fillMenu;
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

        /* ========================== Меню действий над коллекцией ========================== */
        operationsMenu = new Menu("Действия над коллекцией", MAIN_MENU_TITLE_TEXT)
                .addAction("Установить длину", () -> executeOperationAction(CollectionManager::setLength, "Установка длины"))
                .addAction("Отсортировать", () -> executeOperationAction(CollectionManager::sort, "Сортировка"))
                .addAction("Найти", () -> executeOperationAction(CollectionManager::find, "Поиск"))
                .addAction("Просмотреть", () -> executeOperationAction(CollectionManager::show, "Просмотр"))
                .addAction("Записать коллекцию в файл", () -> executeFillAction(CollectionManager::saveToFile, "Запись в файл"));

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
}