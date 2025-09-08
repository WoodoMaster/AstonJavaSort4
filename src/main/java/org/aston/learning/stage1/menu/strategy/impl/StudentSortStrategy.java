package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.menu.strategy.SortStrategy;

public class StudentSortStrategy implements SortStrategy<Student> {
    @Override
    public void sort(CustomCollection<Student> collection, int fieldIndex) {
        // TODO: Сортировка коллекции
        // *** Пример - сортировка коллекции
        // Простая пузырьковая сортировка по группе и среднему баллу
        for (int i = 0; i < collection.size() - 1; i++) {
            for (int j = 0; j < collection.size() - i - 1; j++) {
                Student current = collection.get(j);
                Student next = collection.get(j + 1);

                int groupCompare = current.getGroupNumber().compareTo(next.getGroupNumber());
                if (groupCompare > 0 || (groupCompare == 0 && current.getAverageGrade() < next.getAverageGrade())) {
                    // Простая замена элементов
                    swap(collection, j, j + 1);
                }
            }
        }
    }

    @Override
    public String getSortDescription() {
        return "Сортировка по группе и среднему баллу (по убыванию)";
    }

    private void swap(CustomCollection<Student> collection, int i, int j) {
        Student temp = collection.get(i);
        // В кастомной коллекции нет set метода, нужно удалить и добавить
        collection.remove(i);
        collection.add(temp); // Добавляем в конец, нужно быть аккуратнее
    }
}