package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StudentFileSaveStrategy implements FileSaveStrategy<Student> {
    @Override
    public void saveToFile(String filename, CustomCollection<Student> collection) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < collection.size(); i++) {
                Student student = collection.get(i);
                writer.println(student.getGroupNumber() + "," +
                        student.getAverageGrade() + "," +
                        student.getRecordBookNumber());
            }
        }
    }
}
