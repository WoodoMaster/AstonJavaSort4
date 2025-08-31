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
        CustomCollection<User> users = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    User user = new User(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim()
                    );
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public String getFileFormatDescription() {
        return "Формат: Имя|Пароль|Email";
    }
}