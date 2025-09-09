package org.aston.learning.stage1.collection;

import java.util.Comparator;

public class ArrayCollection<T> implements CustomCollection<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;

    private T[] elements;
    private int size;

    // Конструкторы
    @SuppressWarnings("unchecked")
    public ArrayCollection() {
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayCollection(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.elements = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    // Добавление элемента
    @Override
    public void add(T element) {
        ensureCapacity(size + 1);
        elements[size] = element;
        size++;
    }

    // Удаление по индексу
    @Override
    public void remove(int index) {
        checkIndex(index);

        // Сдвигаем элементы
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null; // Помогаем GC
        size--;
    }

    // Получение элемента
    @Override
    public T get(int index) {
        checkIndex(index);
        return elements[index];
    }

    // Размер коллекции
    @Override
    public int size() {
        return size;
    }

    // Вместимость
    @Override
    public int capacity() {
        return elements.length;
    }

    // Проверка на пустоту
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Очистка
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null; // Помогаем GC
        }
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Увеличение вместимости
    @Override
    @SuppressWarnings("unchecked")
    public void ensureCapacity(int minCapacity) {
        if (minCapacity <= elements.length) {
            return;
        }

        int newCapacity = Math.max((int)(elements.length * GROWTH_FACTOR), minCapacity);
        T[] newElements = (T[]) new Object[newCapacity];

        // Копируем элементы
        if (size >= 0) System.arraycopy(elements, 0, newElements, 0, size);

        this.elements = newElements;
    }

    // Преобразование в массив
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }

    // Копия коллекции
    @Override
    public CustomCollection<T> copy() {
        ArrayCollection<T> copy = new ArrayCollection<>(this.capacity());
        for (int i = 0; i < size; i++) {
            copy.add(elements[i]);
        }
        return copy;
    }

    // Вспомогательные методы
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // toString для отладки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    // Метод для замены элемента (нужен для сортировки)
    @Override
    public void set(int index, T element) {
        checkIndex(index);
        elements[index] = element;
    }

    // Улучшенная сортировка с использованием set
    @Override
    public void swap(CustomCollection<T> collection, int i, int j) {
        T temp = collection.get(i);
        collection.set(i, collection.get(j));
        collection.set(j, temp);
    }

    private void swap(int i, int j) {
        T temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    public void quickSort(Comparator<T> comparator) {
        quickSort(0, size - 1, comparator);
    }

    private void quickSort(int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(low, high, comparator);
            quickSort(low, pi - 1, comparator);
            quickSort(pi + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<T> comparator) {
        T pivot = elements[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(elements[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }

        swap(i + 1, high);
        return i + 1;
    }
}