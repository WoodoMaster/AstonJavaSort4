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
        // TODO: Поиск по коллекции
        // *** Пример - поиск по коллекции
        CustomCollection<Student> students = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split(",");
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Неверное количество полей");
                    }
                    Student student = new Student(
                            parts[0].trim(),
                            Double.parseDouble(parts[1].trim()),
                            parts[2].trim());
                    if (validateData(student)) {
                        students.add(student);
                    } else {
                        System.out.println("Пропущена строка " + lineNumber + ": невалидные данные");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return students;
    }

    @Override
    public boolean validateData(Student student) {
        return student.getGroupNumber() != null && !student.getGroupNumber().isEmpty() &&
                student.getAverageGrade() >= 0.0 && student.getAverageGrade() <= 5.0 &&
                student.getRecordBookNumber() != null && !student.getRecordBookNumber().isEmpty();
    }

    @Override
    public String getFileFormatDescription() {
        return "Формат: Группа,Средний балл,Номер зачетки";
    }
}