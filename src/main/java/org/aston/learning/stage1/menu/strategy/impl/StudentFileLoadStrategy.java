package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentFileLoadStrategy implements FileLoadStrategy<Student> {
    @Override
    public CustomCollection<Student> loadFromFile(String filename) throws IOException {
        CustomCollection<Student> students = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Student student = new Student(
                            parts[0].trim(),
                            Double.parseDouble(parts[1].trim()),
                            parts[2].trim()
                    );
                    students.add(student);
                }
            }
        }
        return students;
    }

    @Override
    public String getFileFormatDescription() {
        return "Формат: Группа,Средний балл,Номер зачетки";
    }
}