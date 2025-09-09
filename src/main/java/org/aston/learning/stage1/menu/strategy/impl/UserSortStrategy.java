package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.sort.*;

import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;

public class UserSortStrategy implements SortStrategy<User> {
    @Override
    public void sort(CustomCollection<User> collection, int fieldIndex) {
        switch (fieldIndex) {
            case 1 -> {
                // Сортировка по имени -> паролю -> email (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new UserNameComparator(),
                        new UserPasswordComparator(),
                        new UserEmailComparator());
            }
            case 2 -> {
                // Сортировка по паролю -> имени -> email (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new UserPasswordComparator(),
                        new UserNameComparator(),
                        new UserEmailComparator());
            }
            case 3 -> {
                // Сортировка по email -> имени -> паролю (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new UserEmailComparator(),
                        new UserNameComparator(),
                        new UserPasswordComparator());
            }
        }
    }

    @Override
    public String getSortDescription() {
        return "Сортировка " + switch (CollectionManager.actionFieldIndex) {
            case 1 -> "по имени, паролю, email";
            case 2 -> "по паролю, имени, email";
            case 3 -> "по email, имени, паролю";
            default -> "";
        };
    }
}