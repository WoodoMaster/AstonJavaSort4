package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.SortStrategy;
import org.aston.learning.stage1.sort.BusNumberComparator;
import org.aston.learning.stage1.sort.QuickSort;

import java.util.Comparator;

import static org.aston.learning.stage1.sort.QuickSort.quickSort;

public class BusSortStrategy implements SortStrategy<Bus> {
    @Override
    public void sort(CustomCollection<Bus> collection) {
        // TODO: Выделить сортировку по каждому полю в отдельный пункт меню?
        // *** Пример - сортировка коллекции

        // Сортировка по номеру -> модели -> пробегу (комплексный компаратор)
        quickSort(collection, 0, collection.size() - 1, new Comparator<Bus>() {
                    @Override
                    public int compare(Bus bus1, Bus bus2) {
                        int result = bus1.getNumber().compareTo(bus2.getNumber());
                        if (result != 0) return result;

                        result = bus1.getModel().compareTo(bus2.getModel());
                        if (result != 0) return result;

                        return Integer.compare(bus1.getMileage(), bus2.getMileage());
                    }
        });
    }

    @Override
    public String getSortDescription() {
        return "Сортировка по модели и пробегу";
    }

    private void swap(CustomCollection<Bus> collection, int i, int j) {
        Bus temp = collection.get(i);
        collection.remove(i);
        collection.add(temp);
    }
}