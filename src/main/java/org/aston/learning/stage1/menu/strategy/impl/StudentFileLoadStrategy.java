package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;
import org.aston.learning.stage1.service.FileManager;

import java.io.IOException;
import java.io.InputStream;

public class StudentFileLoadStrategy implements FileLoadStrategy<Student> {
    @Override
    public CustomCollection<Student> loadFromFile(String filename) throws IOException {
        return FileManager.loadStudentsFromFile(filename);
    }

    @Override
    public CustomCollection<Student> loadFromFile(InputStream is) throws IOException {
        return FileManager.loadStudentsFromFile(is);
    }

    @Override
    public String getFileFormatDescription() {
        return FileManager.getStudentFileFormatDescription();
    }
}