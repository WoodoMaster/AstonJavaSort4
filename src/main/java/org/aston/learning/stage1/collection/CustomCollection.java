package org.aston.learning.stage1.collection;

public interface CustomCollection<T> {
    void add(T element);
    void remove(int index);
    T get(int index);
    int size();
    int capacity();
    boolean isEmpty();
    void clear();
    void ensureCapacity(int minCapacity);
    void set(int index, T element);
    void swap(CustomCollection<T> collection, int i, int j);
    T[] toArray();
    CustomCollection<T> copy();
}