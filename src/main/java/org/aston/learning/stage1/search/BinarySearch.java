package org.aston.learning.stage1.search;

import org.aston.learning.stage1.collection.CustomCollection;

import java.util.Comparator;

public class BinarySearch {

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
}
