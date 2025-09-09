package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.CollectionManager;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.sort.BusMileageComparator;
import org.aston.learning.stage1.sort.BusModelComparator;
import org.aston.learning.stage1.sort.BusNumberComparator;

import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;
import static org.aston.learning.stage1.sort.QuickSort.specialQuickSort;
import static org.aston.learning.stage1.util.ConvertUtils.isEven;

public class BusSortStrategy implements SortStrategy<Bus> {
    @Override
    public void sort(CustomCollection<Bus> collection, int fieldIndex) {
        switch (fieldIndex) {
            case 1 -> {
                // Сортировка по номеру -> модели -> пробегу (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new BusNumberComparator(),
                        new BusModelComparator(),
                        new BusMileageComparator());
            }
            case 2 -> {
                // Сортировка по модели -> пробегу -> номеру (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new BusModelComparator(),
                        new BusMileageComparator(),
                        new BusNumberComparator());
            }
            case 3 -> {
                // Сортировка по пробегу -> модели -> номеру (комплексный компаратор)
                quickSortByMultipleFields(
                        collection,
                        new BusMileageComparator(),
                        new BusModelComparator(),
                        new BusNumberComparator());
            }
            case 4 -> {
                // Особая сортировка по числовому полю

                // Предварительно собираем индексы элементов с четным значением поля
                int count = getEvenCount(collection);
                if (count <= 1) return;

                int[] evenPositionIndexes = new int[count];
                for (int i = 0, k = 0; i < collection.size(); i++) {
                    if (isEven(collection.get(i).getMileage())) evenPositionIndexes[k++] = i;
                }

                specialQuickSort(
                        collection, evenPositionIndexes,
                        new BusMileageComparator());
            }
        }
    }

    @Override
    public String getSortDescription() {
        return "Сортировка " + switch (CollectionManager.actionFieldIndex) {
            case 1 -> "по номеру, модели, пробегу";
            case 2 -> "по модели, пробегу, номеру";
            case 3 -> "по пробегу, модели, номеру";
            case 4 -> "особая по пробегу";
            default -> "";
        };
    }

    // Возвращает количество элементов в коллекции с четным значением поля
    private int getEvenCount(CustomCollection<Bus> collection) {
        int n = collection.size();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (isEven(collection.get(i).getMileage())) {
                count++;
            }
        }
        return count;
    }
}