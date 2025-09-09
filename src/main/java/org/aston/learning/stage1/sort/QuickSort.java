package org.aston.learning.stage1.sort;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;

import java.util.Comparator;
import java.util.List;

public class QuickSort {

    // Сортировка с комплексным компараторам по 3-м полям
    public static <T> void quickSortByMultipleFields(CustomCollection<T> collection,
                                                     Comparator<T> firstComparator,
                                                     Comparator<T> secondComparator,
                                                     Comparator<T> thirdComparator) {

        // Создаем комплексный компаратор
        Comparator<T> combinedComparator = firstComparator
                .thenComparing(secondComparator)
                .thenComparing(thirdComparator);

        quickSort(collection, 0, collection.size() - 1, combinedComparator);
    }

    // Особая сортировка по числовому полю:
    // объекты с четными значениями этого поля
    // должны быть отсортированы в натуральном порядке,
    // а с нечетными – оставаться на исходных позициях.
    public static <T> void specialQuickSort(CustomCollection<T> collection, int[] specialPositionIndexes, Comparator<T> numberComparator) {

        quickSortOnPositions(
                collection,
                specialPositionIndexes,
                0,
                specialPositionIndexes.length - 1,
                numberComparator);
    }


    // Быстрая сортировка (рекурсивная)
    public static <T> void quickSort(CustomCollection<T> collection,
                                     int low,
                                     int high,
                                     Comparator<T> comparator) {

        if (low < high) {
            int pi = basicPartition(collection, low, high, comparator);
            quickSort(collection, low, pi - 1, comparator);
            quickSort(collection, pi + 1, high, comparator);
        }
    }

    private static <T> int basicPartition(CustomCollection<T> collection, int low, int high, Comparator<T> comparator) {
        // Выбор среднего элемента в качестве опорного
        int middle = low + (high - low) / 2;
        T midVal = collection.get(middle);

        // Обмен опорного элемента с последним
        collection.swap(collection, middle, high);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(collection.get(j), midVal) < 0) {
                i++;
                collection.swap(collection, i, j);
            }
        }

        collection.swap(collection, i + 1, high);

        return i + 1;
    }

    // Особая сортировка: по определённым позициям элементов коллекции
    private static <T> void quickSortOnPositions(CustomCollection<T> collection, int[] positions, int low, int high, Comparator<T> comparator) {
        int i = low;
        int j = high;

        // Выбор среднего элемента в качестве опорного
        T midVal = collection.get(positions[(low + high) / 2]);

        while (i <= j) {
            while (comparator.compare(collection.get(positions[i]), midVal) < 0) {
                i++;
            }
            while (comparator.compare(collection.get(positions[j]), midVal) > 0) {
                j--;
            }
            if (i <= j) {
                // Меняем местами только элементы с чётным значением
                collection.swap(collection, positions[i], positions[j]);
                i++;
                j--;
            }
        }

        if (low < j) {
            quickSortOnPositions(collection, positions, low, j, comparator);
        }
        if (i < high) {
            quickSortOnPositions(collection, positions, i, high, comparator);
        }
    }

}
