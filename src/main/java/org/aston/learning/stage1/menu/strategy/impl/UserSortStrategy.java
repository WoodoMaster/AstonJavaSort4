package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.menu.strategy.SortStrategy;

public class UserSortStrategy implements SortStrategy<User> {
    @Override
    public void sort(CustomCollection<User> collection) {
        // TODO: Сортировка коллекции
        // *** Пример - сортировка коллекции
        // Пузырьковая сортировка по имени
        for (int i = 0; i < collection.size() - 1; i++) {
            for (int j = 0; j < collection.size() - i - 1; j++) {
                User current = collection.get(j);
                User next = collection.get(j + 1);

                if (current.getName().compareTo(next.getName()) > 0) {
                    swap(collection, j, j + 1);
                }
            }
        }
    }

    @Override
    public String getSortDescription() {
        return "Сортировка по имени";
    }

    private void swap(CustomCollection<User> collection, int i, int j) {
        User temp = collection.get(i);
        collection.remove(i);
        collection.add(temp);
    }
}