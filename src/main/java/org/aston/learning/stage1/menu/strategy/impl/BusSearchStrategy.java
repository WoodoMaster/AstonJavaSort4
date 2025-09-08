package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;
import org.aston.learning.stage1.sort.BusMileageComparator;
import org.aston.learning.stage1.sort.BusModelComparator;
import org.aston.learning.stage1.sort.BusNumberComparator;

import java.util.ArrayList;
import java.util.List;

import static org.aston.learning.stage1.search.BinarySearch.binarySearchAll;
import static org.aston.learning.stage1.sort.QuickSort.quickSortByMultipleFields;
import static org.aston.learning.stage1.util.ConvertUtils.parseIntOrDefault;

public class BusSearchStrategy implements SearchStrategy<Bus> {
    private int actionIndex = 1;

    // Поиск по коллекции с предварительной сортировкой по указанному полю
    @Override
    public CustomCollection<Bus> search(CustomCollection<Bus> collection, int fieldIndex, String query) {
        CustomCollection<Bus> results = new ArrayCollection<>();

        // Список найденных индексов текущего поиска
        List<Integer> foundIndexes = new ArrayList<>();

        int intQuery = parseIntOrDefault(query, -1);
        actionIndex = fieldIndex;
        Bus bus = new Bus(query, query, intQuery);

        // Поиск по полю с предварительной сортировкой
        switch (fieldIndex) {
            case 1 -> { // по номеру
                quickSortByMultipleFields(collection,
                        new BusNumberComparator(),
                        new BusModelComparator(),
                        new BusMileageComparator());
                foundIndexes = binarySearchAll(collection, bus, new BusNumberComparator());
            }
            case 2 -> { // по модели
                quickSortByMultipleFields(collection,
                        new BusModelComparator(),
                        new BusMileageComparator(),
                        new BusNumberComparator());
                foundIndexes = binarySearchAll(collection, bus, new BusModelComparator());
            }
            case 3 -> { // по пробегу
                quickSortByMultipleFields(collection,
                        new BusMileageComparator(),
                        new BusModelComparator(),
                        new BusNumberComparator());
                foundIndexes = binarySearchAll(collection, bus, new BusMileageComparator());
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
        return "Поиск по " + switch (actionIndex) {
            case 1 -> "номеру";
            case 2 -> "модели";
            case 3 -> "пробегу";
            default -> "";
        };
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }

    public void setActionIndex(int actionIndex) {
        this.actionIndex = actionIndex;
    }
}