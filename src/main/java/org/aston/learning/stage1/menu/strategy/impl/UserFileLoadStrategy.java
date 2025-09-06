package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserFileLoadStrategy implements FileLoadStrategy<User> {
    @Override
    public CustomCollection<User> loadFromFile(String filename) throws IOException {
        // TODO: Загрузка из файла
        // *** Пример - загрузка из файла
        CustomCollection<User> users = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split("\\|");
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Неверное количество полей");
                    }
                    User user = new User(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim());
                    if (validateData(user)) {
                        users.add(user);
                    } else {
                        System.out.println("Пропущена строка " + lineNumber + ": невалидные данные");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return users;
    }

    @Override
    public String getFileFormatDescription() {
        return "Формат: Имя|Пароль|Email";
    }

    @Override
    public boolean validateData(User user) {
        return user.getName() != null && !user.getName().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getEmail() != null && !user.getEmail().isEmpty();
    }
}