package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.sort.UserEmailComparator;
import org.aston.learning.stage1.sort.UserNameComparator;
import org.aston.learning.stage1.sort.UserPasswordComparator;

import java.util.ArrayList;
import java.util.List;

import static org.aston.learning.stage1.search.BinarySearch.binarySearchAll;
import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;

public class UserSearchStrategy implements SearchStrategy<User> {

    @Override
    public CustomCollection<User> search(CustomCollection<User> collection, int fieldIndex, String query) {
        CustomCollection<User> results = new ArrayCollection<>();

        // Список найденных индексов текущего поиска
        List<Integer> foundIndexes = new ArrayList<>();

        User user = new User(query, query, query);

        // Поиск по полю с предварительной сортировкой
        switch (fieldIndex) {
            case 1 -> { // по имени
                quickSortByMultipleFields(
                        collection,
                        new UserNameComparator(),
                        new UserPasswordComparator(),
                        new UserEmailComparator());
                foundIndexes = binarySearchAll(collection, user, new UserNameComparator());
            }
            case 2 -> { // по паролю
                quickSortByMultipleFields(
                        collection,
                        new UserPasswordComparator(),
                        new UserNameComparator(),
                        new UserEmailComparator());
                foundIndexes = binarySearchAll(collection, user, new UserPasswordComparator());
            }
            case 3 -> { // по email
                quickSortByMultipleFields(
                        collection,
                        new UserEmailComparator(),
                        new UserNameComparator(),
                        new UserPasswordComparator());
                foundIndexes = binarySearchAll(collection, user, new UserEmailComparator());
            }

        }
        if (!foundIndexes.isEmpty()) {
            for(Integer ind: foundIndexes) {
                results.add(collection.get(ind));
            }
        }


        return results;
    }

    @Override
    public String getSearchDescription() {
        return "Поиск " + switch (CollectionManager.actionFieldIndex) {
            case 1 -> "по имени";
            case 2 -> "по паролю";
            case 3 -> "по email";
            default -> "";
        };
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}