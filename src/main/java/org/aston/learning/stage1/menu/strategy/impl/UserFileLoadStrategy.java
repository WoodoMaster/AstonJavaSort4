package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;
import org.aston.learning.stage1.service.FileManager;

import java.io.IOException;
import java.io.InputStream;

public class UserFileLoadStrategy implements FileLoadStrategy<User> {
    @Override
    public CustomCollection<User> loadFromFile(String filename) throws IOException {
        return FileManager.loadUsersFromFile(filename);
    }

    @Override
    public CustomCollection<User> loadFromFile(InputStream is) throws IOException {
        return FileManager.loadUsersFromFile(is);
    }

    @Override
    public String getFileFormatDescription() {
        return FileManager.getUserFileFormatDescription();
    }
}