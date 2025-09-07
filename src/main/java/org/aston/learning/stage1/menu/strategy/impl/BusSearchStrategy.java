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

import static org.aston.learning.stage1.search.BinarySearch.binarySearch;
import static org.aston.learning.stage1.util.ConvertUtils.parseIntOrDefault;

public class BusSearchStrategy implements SearchStrategy<Bus> {
    @Override
    public CustomCollection<Bus> search(CustomCollection<Bus> collection, String query) {
        // Бинарный поиск
        // TODO: Добавить предварительную сортировку коллекции перед каждым поиском

        CustomCollection<Bus> results = new ArrayCollection<>();
        // Список найденных индексов для проверки на дубликаты
        List indexes = new ArrayList<Integer>();

        int intQuery = parseIntOrDefault(query, -1);
        System.out.println("int: " + intQuery);
        Bus bus = new Bus(query, query, intQuery);
        int ind;

        ind = binarySearch(collection, bus, new BusNumberComparator());
        System.out.println(ind);
        if (ind >= 0) {
            results.add(collection.get(ind));
            indexes.add(ind);
        }
        ind = binarySearch(collection, bus, new BusModelComparator());
        System.out.println(ind);
        if (ind >= 0 & !indexes.contains(ind)) {
            results.add(collection.get(ind));
            indexes.add(ind);
        }
        // если пробег - целочисленное число, ищем и по нему
        if(intQuery >= 0 & !indexes.contains(ind)) {
            ind = binarySearch(collection, bus, new BusMileageComparator());
            System.out.println(ind);
            if (ind >= 0) {
                results.add(collection.get(ind));
                indexes.add(ind);
            }
        }

        return results;
    }

    @Override
    public String getSearchDescription() {
        return "Поиск по номеру, модели и пробегу";
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}