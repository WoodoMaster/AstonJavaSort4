package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;

public class UserSearchStrategy implements SearchStrategy<User> {
    @Override
    public CustomCollection<User> search(CustomCollection<User> collection, String query) {
        // TODO: Поиск по коллекции
        // *** Пример - поиск по коллекции
        CustomCollection<User> results = new ArrayCollection<>();

        for (int i = 0; i < collection.size(); i++) {
            User user = collection.get(i);
            if (containsIgnoreCase(user.getName(), query) ||
                    containsIgnoreCase(user.getEmail(), query) ||
                    containsIgnoreCase(user.getPassword(), query)) {
                results.add(user);
            }
        }
        return results;
    }

    @Override
    public String getSearchDescription() {
        return "Поиск по имени, email или паролю";
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}