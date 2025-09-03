package org.aston.learning.stage1.menu.strategy;

import org.aston.learning.stage1.collection.CustomCollection;

public interface SearchStrategy<T> {
    CustomCollection<T> search(CustomCollection<T> collection, String query);
    String getSearchDescription();
}