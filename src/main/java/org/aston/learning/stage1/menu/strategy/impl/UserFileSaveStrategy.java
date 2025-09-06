package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UserFileSaveStrategy implements FileSaveStrategy<User> {
    @Override
    public void saveToFile(String filename, CustomCollection<User> collection) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < collection.size(); i++) {
                User user = collection.get(i);
                writer.println(user.getName() + "\\|" +
                        user.getEmail() + "\\|" +
                        user.getPassword());
            }
        }
    }
}
