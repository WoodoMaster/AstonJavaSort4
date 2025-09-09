package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.sort.StudentAverageGradeComparator;
import org.aston.learning.stage1.sort.StudentGroupNumberComparator;
import org.aston.learning.stage1.sort.StudentRecordBookNumberComparator;

import java.util.ArrayList;
import java.util.List;

import static org.aston.learning.stage1.search.BinarySearch.binarySearchAll;
import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;
import static org.aston.learning.stage1.util.ConvertUtils.parseDoubleOrDefault;

public class StudentSearchStrategy implements SearchStrategy<Student> {
    // Поиск по коллекции с предварительной сортировкой по указанному полю
    @Override
    public CustomCollection<Student> search(CustomCollection<Student> collection, int fieldIndex, String query) {
        CustomCollection<Student> results = new ArrayCollection<>();

        // Список найденных индексов текущего поиска
        List<Integer> foundIndexes = new ArrayList<>();

        double doubleQuery = parseDoubleOrDefault(query, 0.0);
        Student student = new Student(query, doubleQuery, query);

        // Поиск по полю с предварительной сортировкой
        switch (fieldIndex) {
            case 1 -> { // по группе
                quickSortByMultipleFields(collection,
                        new StudentGroupNumberComparator(),
                        new StudentAverageGradeComparator(),
                        new StudentRecordBookNumberComparator());
                foundIndexes = binarySearchAll(collection, student, new StudentGroupNumberComparator());
            }
            case 2 -> { // по среднему баллу
                quickSortByMultipleFields(collection,
                        new StudentAverageGradeComparator(),
                        new StudentGroupNumberComparator(),
                        new StudentRecordBookNumberComparator());
                foundIndexes = binarySearchAll(collection, student, new StudentAverageGradeComparator());
            }
            case 3 -> { // по номеру зачетки
                quickSortByMultipleFields(collection,
                        new StudentRecordBookNumberComparator(),
                        new StudentAverageGradeComparator(),
                        new StudentGroupNumberComparator());
                foundIndexes = binarySearchAll(collection, student, new StudentRecordBookNumberComparator());
            }

        }
        if (!foundIndexes.isEmpty()) {
            for(Integer ind: foundIndexes) {
                results.add(collection.get(ind));
            }
        }

        return results;
    }

    @Override
    public String getSearchDescription() {
        return "Поиск " + switch (CollectionManager.actionFieldIndex) {
            case 1 -> "по группе";
            case 2 -> "по среднему баллу";
            case 3 -> "по номеру зачетки";
            default -> "";
        };
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}