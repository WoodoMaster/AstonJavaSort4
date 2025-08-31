package org.aston.learning.stage1;

import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.menu.CollectionManagerFactory;
import org.aston.learning.stage1.menu.CurrentCollectionManager;
import org.aston.learning.stage1.menu.Menu;
import org.aston.learning.stage1.model.*;
import org.aston.learning.stage1.util.console.ConsoleUtils;

import java.util.function.Consumer;

public class Main {
    private static Menu mainMenu;
    private static Menu collectionSelectMenu;
    private static Menu fillMenu;
    private static Menu actionsMenu;

    public static void main(String[] args) {
        /* ========================== Создание коллекций ========================== */
        CollectionManager<Student> studentManager = CollectionManagerFactory.createStudentManager("Студенты", 30);
        CollectionManager<Bus> busManager = CollectionManagerFactory.createBusManager("Автобусы", 5);
        CollectionManager<User> userManager = CollectionManagerFactory.createUserManager("Пользователи");

        /* ========================== Установка коллекции по умолчанию ========================== */
        CurrentCollectionManager.setCurrent(studentManager, studentManager.getName());

        mainMenu = new Menu("Главное меню");
        collectionSelectMenu = new Menu("Выбор коллекции", mainMenu.getTitle());
        fillMenu = new Menu("Заполнение коллекции", mainMenu.getTitle());
        actionsMenu = new Menu("Действия над коллекцией", mainMenu.getTitle());

        /* ========================== Меню выбора коллекции ========================== */
        collectionSelectMenu
                .addAction(studentManager.getName(), () -> selectCollection(studentManager))
                .addAction(busManager.getName(), () -> selectCollection(busManager))
                .addAction(userManager.getName(), () -> selectCollection(userManager));

        /* ========================== Меню заполнения коллекции ========================== */
        fillMenu
                .addAction("Заполнить вручную", () -> executeCollectionAction(CollectionManager::fillManual, "Заполнение вручную", fillMenu))
                .addAction("Заполнить из файла", () -> executeCollectionAction(CollectionManager::fillFile, "Заполнение из файла", fillMenu))
                .addAction("Заполнить случайно", () -> executeCollectionAction(CollectionManager::fillRandom, "Случайное заполнение", fillMenu))
                .addAction("Очистить коллекцию", () -> executeCollectionAction(CollectionManager::clear, "Очистка коллекции", fillMenu));

        /* ========================== Меню действий над коллекцией ========================== */
        actionsMenu
                .addAction("Установить длину", () -> executeCollectionAction(CollectionManager::setLength, "Установка длины", actionsMenu))
                .addAction("Отсортировать", () -> executeCollectionAction(CollectionManager::sort, "Сортировка", actionsMenu))
                .addAction("Найти", () -> executeCollectionAction(CollectionManager::find, "Поиск", actionsMenu))
                .addAction("Просмотреть", () -> executeCollectionAction(CollectionManager::show, "Просмотр", actionsMenu));

        /* ========================== Главное меню ========================== */
        mainMenu
                .addAction(collectionSelectMenu.getTitle(), collectionSelectMenu::open)
                .addAction(fillMenu.getTitle(), fillMenu::open)
                .addAction(actionsMenu.getTitle(), actionsMenu::open)
                .open();
    }

    private static void selectCollection(CollectionManager<?> manager) {
        CurrentCollectionManager.setCurrent(manager, manager.getName());
        System.out.println("Выбрана коллекция: " + manager.getName() + "\n");
        collectionSelectMenu.close();
    }

    private static void executeCollectionAction(Consumer<CollectionManager<?>> action,
                                                String actionName, Menu menu) {
        if (!CurrentCollectionManager.isSelected()) {
            ConsoleUtils.printError("Сначала выберите коллекцию!");
            return;
        }

        CollectionManager<?> manager = CurrentCollectionManager.getCurrent();
        menu.displayHeader(actionName);
        action.accept(manager);
    }
}