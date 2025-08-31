package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.SortStrategy;

public class BusSortStrategy implements SortStrategy<Bus> {
    @Override
    public void sort(CustomCollection<Bus> collection) {
        // Пузырьковая сортировка по модели и пробегу
        for (int i = 0; i < collection.size() - 1; i++) {
            for (int j = 0; j < collection.size() - i - 1; j++) {
                Bus current = collection.get(j);
                Bus next = collection.get(j + 1);

                int modelCompare = current.getModel().compareTo(next.getModel());
                if (modelCompare > 0 || (modelCompare == 0 && current.getMileage() > next.getMileage())) {
                    swap(collection, j, j + 1);
                }
            }
        }
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