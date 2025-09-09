package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.sort.StudentAverageGradeComparator;
import org.aston.learning.stage1.sort.StudentGroupNumberComparator;
import org.aston.learning.stage1.sort.StudentRecordBookNumberComparator;

import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;

public class StudentSortStrategy implements SortStrategy<Student> {
    @Override
    public void sort(CustomCollection<Student> collection, int fieldIndex) {
        switch (fieldIndex) {
            case 1 -> {
                // Сортировка по группе -> среднему баллу -> номеру зачетки (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new StudentGroupNumberComparator(),
                        new StudentAverageGradeComparator(),
                        new StudentRecordBookNumberComparator());
            }
            case 2 -> {
                // Сортировка по среднему баллу -> группе -> номеру зачетки (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new StudentAverageGradeComparator(),
                        new StudentGroupNumberComparator(),
                        new StudentRecordBookNumberComparator());
            }
            case 3 -> {
                // Сортировка по номеру зачетки -> группе -> по среднему баллу (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new StudentRecordBookNumberComparator(),
                        new StudentGroupNumberComparator(),
                        new StudentAverageGradeComparator());
            }
        }
    }

    @Override
    public String getSortDescription() {
        return "Сортировка " + switch (CollectionManager.actionFieldIndex) {
            case 1 -> "по группе, среднему баллу, номеру зачетки";
            case 2 -> "по среднему баллу, группе, номеру зачетки";
            case 3 -> "по номеру зачетки, группе, по среднему баллу";
            default -> "";
        };
    }
}