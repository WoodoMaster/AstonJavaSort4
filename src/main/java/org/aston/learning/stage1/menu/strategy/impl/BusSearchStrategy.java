package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.SearchStrategy;

public class BusSearchStrategy implements SearchStrategy<Bus> {
    @Override
    public CustomCollection<Bus> search(CustomCollection<Bus> collection, String query) {
        CustomCollection<Bus> results = new ArrayCollection<>();

        for (int i = 0; i < collection.size(); i++) {
            Bus bus = collection.get(i);
            if (containsIgnoreCase(bus.getNumber(), query) ||
                    containsIgnoreCase(bus.getModel(), query) ||
                    String.valueOf(bus.getMileage()).contains(query)) {
                results.add(bus);
            }
        }
        return results;
    }

    @Override
    public String getSearchDescription() {
        return "Поиск по номеру, модели или пробегу";
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}