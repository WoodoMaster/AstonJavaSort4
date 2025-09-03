package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.menu.strategy.ElementHandler;
import org.aston.learning.stage1.util.console.InputPattern;
import org.aston.learning.stage1.util.console.InputUtils;

public class StudentHandler implements ElementHandler<Student> {
    @Override
    public Student createElementManually() {
        System.out.println("Создание студента:");
        String group = InputUtils.readRegex("Группа*: ", InputPattern.STUDENT_GROUP, true);
        double average = InputUtils.readDouble("Средний балл: ", 0.0, 5.0);
        String recordBook = InputUtils.readRegex("Номер зачетки*: ", InputPattern.RECORD_BOOK, true);
        return new Student(group, average, recordBook);
    }

    @Override
    public String[] getTableHeaders() {
        return new String[]{"№", "Группа", "Средний балл", "Зачетка"};
    }

    @Override
    public String[] convertToTableRow(Student student, int index) {
        return new String[]{
                String.valueOf(index + 1),
                student.getGroupNumber(),
                String.format("%.2f", student.getAverageGrade()),
                student.getRecordBookNumber()
        };
    }

    @Override
    public String getTypeName() {
        return "Student";
    }
}