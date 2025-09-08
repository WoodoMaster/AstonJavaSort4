package org.aston.learning.stage1.search;

import org.aston.learning.stage1.collection.CustomCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BinarySearch {

    // Классический бинарный поиск одного элемента
    public static <T> int binarySearch(CustomCollection<T> collection, T key, Comparator<T> comparator) {
        int low = 0;
        int high = collection.size() - 1;

        while (low <= high) {
            int middle = (low + high) / 2;
            T midVal = collection.get(middle);
            int cmp = comparator.compare(midVal, key);

            if (cmp < 0) {
                low = middle + 1;
            } else if (cmp > 0) {
                high = middle - 1;
            } else {
                return middle; // найдено
            }
        }
        return -1; // не найдено
    }

    // Поиск диапазона элементов, удовлетворяющих условию
    public static <T> List<Integer> binarySearchAll(CustomCollection<T> collection, T key, Comparator<T> comparator) {
        if (collection == null & key == null & comparator == null) {
            throw new IllegalArgumentException("Collection, key and comparator cannot be null");
        }

        if (collection.isEmpty()) {
            return Collections.emptyList();
        }

        int[] range = binarySearchRange(collection, key, comparator);

        if (range[0] == -1) {
            return Collections.emptyList();
        }

        List<Integer> indexes = new ArrayList<>();
        for (int i = range[0]; i <= range[1]; i++) {
            indexes.add(i);
        }

        return indexes;
    }

    // Поиск с комплексным компараторам по 3-м полям
    public static <T> List<Integer> binarySearchAllByMultipleFields(CustomCollection<T> collection, T key,
                                                                    Comparator<T> primaryComparator,
                                                                    Comparator<T> secondaryComparator,
                                                                    Comparator<T> tertiaryComparator) {

        // Создаем комплексный компаратор
        Comparator<T> combinedComparator = primaryComparator
                .thenComparing(secondaryComparator)
                .thenComparing(tertiaryComparator);

        return binarySearchAll(collection, key, combinedComparator);
    }

    // Вспомогательный метод для поиска диапазона
    private static <T> int[] binarySearchRange(CustomCollection<T> collection, T key, Comparator<T> comparator) {
        int foundIndex = binarySearch(collection, key, comparator);

        if (foundIndex == -1) {
            return new int[]{-1, -1};
        }

        // Ищем начало диапазона
        int start = foundIndex;
        while (start > 0 && comparator.compare(collection.get(start - 1), key) == 0) {
            start--;
        }

        // Ищем конец диапазона
        int end = foundIndex;
        while (end < collection.size() - 1 && comparator.compare(collection.get(end + 1), key) == 0) {
            end++;
        }

        return new int[]{start, end};
    }
}
