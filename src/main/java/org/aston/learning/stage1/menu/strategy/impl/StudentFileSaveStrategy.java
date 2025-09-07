package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.service.FileManager;

import java.io.IOException;

public class StudentFileSaveStrategy implements FileSaveStrategy<Student> {
    @Override
    public void saveToFile(CustomCollection<Student> collection) throws IOException {
        FileManager.saveStudentsToFile(collection);
    }
}
