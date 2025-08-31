package org.aston.learning.stage1.menu;

import org.aston.learning.stage1.menu.strategy.impl.*;
import org.aston.learning.stage1.model.*;

public class CollectionManagerFactory {

    // Методы с конкретными типами возврата
    public static CollectionManager<Student> createStudentManager(String name) {
        return new CollectionManager<>(
                name,
                new StudentHandler(),
                new StudentSearchStrategy(),
                new StudentSortStrategy(),
                new StudentFileLoadStrategy()
        );
    }

    public static CollectionManager<Student> createStudentManager(String name, int size) {
        return new CollectionManager<>(
                name,
                new StudentHandler(),
                new StudentSearchStrategy(),
                new StudentSortStrategy(),
                new StudentFileLoadStrategy(),
                size
        );
    }

    public static CollectionManager<Bus> createBusManager(String name) {
        return new CollectionManager<>(
                name,
                new BusHandler(),
                new BusSearchStrategy(),
                new BusSortStrategy(),
                new BusFileLoadStrategy()
        );
    }

    public static CollectionManager<Bus> createBusManager(String name, int size) {
        return new CollectionManager<>(
                name,
                new BusHandler(),
                new BusSearchStrategy(),
                new BusSortStrategy(),
                new BusFileLoadStrategy(),
                size
        );
    }

    public static CollectionManager<User> createUserManager(String name) {
        return new CollectionManager<>(
                name,
                new UserHandler(),
                new UserSearchStrategy(),
                new UserSortStrategy(),
                new UserFileLoadStrategy()
        );
    }

    public static CollectionManager<User> createUserManager(String name, int size) {
        return new CollectionManager<>(
                name,
                new UserHandler(),
                new UserSearchStrategy(),
                new UserSortStrategy(),
                new UserFileLoadStrategy(),
                size
        );
    }

    // Универсальные методы с проверкой типа
    public static CollectionManager<?> createManager(String typeName, String customName) {
        return switch (typeName.toLowerCase()) {
            case "student" -> createStudentManager(customName);
            case "bus" -> createBusManager(customName);
            case "user" -> createUserManager(customName);
            default -> throw new IllegalArgumentException("Неизвестный тип: " + typeName);
        };
    }

    public static CollectionManager<?> createManager(String typeName, String customName, int size) {
        return switch (typeName.toLowerCase()) {
            case "student" -> createStudentManager(customName, size);
            case "bus" -> createBusManager(customName, size);
            case "user" -> createUserManager(customName, size);
            default -> throw new IllegalArgumentException("Неизвестный тип: " + typeName);
        };
    }
}