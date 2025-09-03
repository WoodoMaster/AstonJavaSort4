package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.menu.strategy.ElementHandler;
import org.aston.learning.stage1.util.console.InputUtils;

public class UserHandler implements ElementHandler<User> {
    @Override
    public User createElementManually() {
        System.out.println("Создание пользователя:");
        String name = InputUtils.readString("Имя: ", true);
        String password = InputUtils.readString("Пароль: ", true);
        String email = InputUtils.readString("Email: ", true);
        return new User(name, password, email);
    }

    @Override
    public String[] getTableHeaders() {
        return new String[]{"№", "Имя", "Email", "Пароль"};
    }

    @Override
    public String[] convertToTableRow(User user, int index) {
        return new String[]{
                String.valueOf(index + 1),
                user.getName(),
                user.getEmail(),
                "*".repeat(user.getPassword().length())
        };
    }

    @Override
    public String getTypeName() {
        return "User";
    }
}