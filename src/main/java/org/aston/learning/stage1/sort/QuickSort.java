package org.aston.learning.stage1.sort;

import org.aston.learning.stage1.collection.CustomCollection;

import java.util.Comparator;

public class QuickSort {
    // Быстрая сортировка (рекурсивная)
    public static <T> void quickSort(CustomCollection<T> collection, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(collection, low, high, comparator);

            quickSort(collection, low, pi - 1, comparator);
            quickSort(collection, pi + 1, high, comparator);
        }
    }

    private static <T> int partition(CustomCollection<T> collection, int low, int high, Comparator<T> comparator) {
        // Выбор среднего элемента в качестве опорного
        int middle = low + (high - low) / 2;
        T midVal = collection.get(middle);

        // Обмен опорного элемента с последним, чтобы использовать существующую логику
        collection.swap(collection, middle, high);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(collection.get(j), midVal) < 0) {
                i++;
                collection.swap(collection, i, j);
            }
        }

        collection.swap(collection, i +1, high);

        return i + 1;
    }
}
