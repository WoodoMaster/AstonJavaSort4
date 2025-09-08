package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.sort.BusMileageComparator;
import org.aston.learning.stage1.sort.BusModelComparator;
import org.aston.learning.stage1.sort.BusNumberComparator;
import org.aston.learning.stage1.sort.QuickSort;

import java.util.Comparator;

import static org.aston.learning.stage1.sort.QuickSort.quickSort;
import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;

public class BusSortStrategy implements SortStrategy<Bus> {
    private int actionIndex = 1;

    @Override
    public void sort(CustomCollection<Bus> collection, int fieldIndex) {
        actionIndex = fieldIndex;
        switch (fieldIndex) {
            case 1 -> {
                // Сортировка по номеру -> модели -> пробегу (комплексный компаратор)
                quickSortByMultipleFields(collection,
                        new BusNumberComparator(),
                        new BusModelComparator(),
                        new BusMileageComparator());
            }
            case 2 -> {
                // Сортировка по модели -> пробегу -> номеру (комплексный компаратор)
                quickSortByMultipleFields(collection,
                        new BusModelComparator(),
                        new BusMileageComparator(),
                        new BusNumberComparator());
            }
            case 3 -> {
                // Сортировка по пробегу -> модели -> номеру (комплексный компаратор)
                quickSortByMultipleFields(collection,
                        new BusMileageComparator(),
                        new BusModelComparator(),
                        new BusNumberComparator());
            }
        }
    }

    @Override
    public String getSortDescription() {
        return "Сортировка " + switch (actionIndex) {
            case 1 -> "по номеру, модели, пробегу";
            case 2 -> "по модели, пробегу, номеру";
            case 3 -> "по пробегу, модели, номеру";
            default -> "";
        };
    }

    private void swap(CustomCollection<Bus> collection, int i, int j) {
        Bus temp = collection.get(i);
        collection.remove(i);
        collection.add(temp);
    }

    public void setActionIndex(int actionIndex) {
        this.actionIndex = actionIndex;
    }
}