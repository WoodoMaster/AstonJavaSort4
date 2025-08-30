package org.aston.learning.stage1.menu;

public class CurrentCollectionManager {
    private static Object currentCollection;
    private static String currentName = "Не выбрана";

    public static void setCurrent(Object collection, String name) {
        currentCollection = collection;
        currentName = name;
    }

    public static String getCurrentName() {
        return currentName;
    }

    public static <T> T getCurrent() {
        return (T) currentCollection;
    }

    public static boolean isSelected() {
        return currentCollection != null;
    }
}