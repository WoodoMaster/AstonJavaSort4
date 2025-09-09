package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.service.FileManager;

import java.io.IOException;

public class UserFileSaveStrategy implements FileSaveStrategy<User> {
    @Override
    public void saveToFile(CustomCollection<User> collection) throws IOException {
        FileManager.saveUsersToFile(collection);
    }
}
