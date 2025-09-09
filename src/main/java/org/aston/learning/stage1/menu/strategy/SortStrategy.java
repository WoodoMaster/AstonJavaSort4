package org.aston.learning.stage1.menu.strategy;

import org.aston.learning.stage1.collection.CustomCollection;

public interface SortStrategy<T> {
    void sort(CustomCollection<T> collection, int fieldIndex);
    String getSortDescription();
}