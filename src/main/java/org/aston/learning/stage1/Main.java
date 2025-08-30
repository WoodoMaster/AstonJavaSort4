package org.aston.learning.stage1;

import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.menu.CurrentCollectionManager;
import org.aston.learning.stage1.menu.Menu;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.util.ConsoleUtils;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        CollectionManager<Student> studentManager = new CollectionManager<>("Student");
        CollectionManager<Bus> busManager = new CollectionManager<>("Bus");
        CollectionManager<User> userManager = new CollectionManager<>("User");

        Menu mainMenu = new Menu("Главное меню");
        Menu collectionSelectMenu = new Menu("Выбор коллекции", mainMenu.getTitle());
        Menu fillMenu = new Menu("Заполнение коллекции", mainMenu.getTitle());
        Menu actionsMenu = new Menu("Действия над коллекцией", mainMenu.getTitle());

        /* ========================== Меню выбора коллекции ========================== */
        collectionSelectMenu.addAction("Student", () -> selectCollection(studentManager, collectionSelectMenu));
        collectionSelectMenu.addAction("Bus", () -> selectCollection(busManager, collectionSelectMenu));
        collectionSelectMenu.addAction("User", () -> selectCollection(userManager, collectionSelectMenu));

        /* ========================== Меню заполнения коллекции ========================== */
        fillMenu.addAction("Заполнить вручную", () -> executeCollectionAction(CollectionManager::fillManual, "Заполнение вручную", fillMenu));
        fillMenu.addAction("Заполнить из файла", () -> executeCollectionAction(CollectionManager::fillFile, "Заполнение из файла", fillMenu));
        fillMenu.addAction("Заполнить случайно", () -> executeCollectionAction(CollectionManager::fillRandom, "Случайное заполнение", fillMenu));
        fillMenu.addAction("Очистить коллекцию", () -> executeCollectionAction(CollectionManager::clear, "Очистка коллекции", fillMenu));

        /* ========================== Меню действий над коллекцией ========================== */
        actionsMenu.addAction("Установить длину", () -> executeCollectionAction(CollectionManager::setLength, "Установка длины", actionsMenu));
        actionsMenu.addAction("Отсортировать", () -> executeCollectionAction(CollectionManager::sort, "Сортировка", actionsMenu));
        actionsMenu.addAction("Найти", () -> executeCollectionAction(CollectionManager::find, "Поиск", actionsMenu));
        actionsMenu.addAction("Просмотреть", () -> executeCollectionAction(CollectionManager::show, "Просмотр", actionsMenu));

        /* ========================== Главное меню ========================== */
        mainMenu.addAction(collectionSelectMenu.getTitle(), collectionSelectMenu::open);
        mainMenu.addAction(fillMenu.getTitle(), fillMenu::open);
        mainMenu.addAction(actionsMenu.getTitle(), actionsMenu::open);

        mainMenu.open();
    }

    private static void selectCollection(CollectionManager<?> manager, Menu menu) {
        CurrentCollectionManager.setCurrent(manager, manager.getName());
        menu.close();
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