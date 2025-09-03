package org.aston.learning.stage1.menu.strategy;

public interface ElementHandler<T> {
    T createElementManually();
    String[] getTableHeaders();
    String[] convertToTableRow(T element, int index);
    String getTypeName();
}