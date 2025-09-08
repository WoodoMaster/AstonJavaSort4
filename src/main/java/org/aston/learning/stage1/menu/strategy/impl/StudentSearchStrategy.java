package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;

public class StudentSearchStrategy implements SearchStrategy<Student> {
    @Override
    public CustomCollection<Student> search(CustomCollection<Student> collection, int fieldIndex, String query) {
        // TODO: Загрузка из файла
        // *** Пример - загрузка из файла
        CustomCollection<Student> results = new ArrayCollection<>();

        for (int i = 0; i < collection.size(); i++) {
            Student student = collection.get(i);
            if (containsIgnoreCase(student.getGroupNumber(), query) ||
                    containsIgnoreCase(student.getRecordBookNumber(), query) ||
                    String.valueOf(student.getAverageGrade()).contains(query)) {
                results.add(student);
            }
        }
        return results;
    }

    @Override
    public String getSearchDescription() {
        return "Поиск по группе, номеру зачетки или среднему баллу";
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}