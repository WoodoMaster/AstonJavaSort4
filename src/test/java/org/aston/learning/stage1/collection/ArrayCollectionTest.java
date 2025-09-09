package org.aston.learning.stage1.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ArrayCollectionTest {

    private ArrayCollection<String> collection;

    @BeforeEach
    void setUp() {
        collection = new ArrayCollection<>();
    }

    @Test
    @DisplayName("Конструктор по умолчанию создает коллекцию с DEFAULT_CAPACITY")
    void defaultConstructorSetsDefaultCapacityTest() {
        assertThat(collection.capacity()).isEqualTo(10);
        assertThat(collection.size()).isZero();
    }

    @Test
    @DisplayName("Конструктор с capacity создает коллекцию заданного размера")
    void constructorWithCapacityCreatesCorrectSizeTest() {
        ArrayCollection<String> col = new ArrayCollection<>(20);
        assertThat(col.capacity()).isEqualTo(20);
        assertThat(col.size()).isZero();
    }

    @Test
    @DisplayName("Конструктор с нулевой или отрицательной capacity, бросает IllegalArgumentException")
    void constructorWithInvalidCapacityThrowsExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayCollection<>(0));
        assertThrows(IllegalArgumentException.class, () -> new ArrayCollection<>(-5));
    }

    @Test
    @DisplayName("add добавляет элемент в конец и увеличивает size")
    void addIncreasesSizeAndStoresElementTest() {
        collection.add("A");
        collection.add("B");

        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0)).isEqualTo("A");
        assertThat(collection.get(1)).isEqualTo("B");
    }

    @Test
    @DisplayName("add автоматически расширяет массив при необходимости")
    void addExpandsCapacityWhenNeededTest() {
        // Заполняем до DEFAULT_CAPACITY (10)
        for (int i = 0; i < 10; i++) {
            collection.add("Item" + i);
        }
        assertThat(collection.capacity()).isEqualTo(10);

        // Добавляем 11-й элемент
        collection.add("Overflow");
        assertThat(collection.capacity()).isGreaterThan(10); // должно стать 15 (10 * 1.5)
        assertThat(collection.size()).isEqualTo(11);
        assertThat(collection.get(10)).isEqualTo("Overflow");
    }

    @Test
    @DisplayName("remove удаляет элемент по индексу и сдвигает оставшиеся")
    void removeShiftsElementsLeftTest() {
        collection.add("A");
        collection.add("B");
        collection.add("C");

        collection.remove(1);

        assertThat(collection.size()).isEqualTo(2);
        assertThat(collection.get(0)).isEqualTo("A");
        assertThat(collection.get(1)).isEqualTo("C");
        assertThat(collection.toString()).isEqualTo("[A, C]");
    }

    @Test
    @DisplayName("remove с невалидным индексом, бросает IndexOutOfBoundsException")
    void removeWithInvalidIndexThrowsExceptionTest() {
        collection.add("A");

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(100));
    }

    @Test
    @DisplayName("get возвращает правильный элемент по индексу")
    void getReturnsCorrectElementTest() {
        collection.add("First");
        collection.add("Second");

        assertThat(collection.get(0)).isEqualTo("First");
        assertThat(collection.get(1)).isEqualTo("Second");
    }

    @Test
    @DisplayName("get с невалидным индексом, бросает IndexOutOfBoundsException")
    void getWithInvalidIndexThrowsExceptionTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(100));
    }

    @Test
    @DisplayName("set заменяет элемент по индексу")
    void setReplacesElementAtIndexTest() {
        collection.add("Old");
        collection.set(0, "New");

        assertThat(collection.get(0)).isEqualTo("New");
    }

    @Test
    @DisplayName("set с невалидным индексом, бросает IndexOutOfBoundsException")
    void setWithInvalidIndexThrowsExceptionTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.set(-1, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.set(0, "X"));
    }

    @Test
    @DisplayName("size возвращает текущее количество элементов")
    void sizeReturnsCorrectCountTest() {
        assertThat(collection.size()).isZero();
        collection.add("A");
        assertThat(collection.size()).isEqualTo(1);
        collection.add("B");
        assertThat(collection.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("isEmpty возвращает true для пустой коллекции")
    void isEmptyReturnsTrueWhenEmptyTest() {
        assertThat(collection.isEmpty()).isTrue();
        collection.add("A");
        assertThat(collection.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("clear удаляет все элементы и сбрасывает capacity до DEFAULT")
    void clearResetsSizeAndCapacityTest() {
        for (int i = 0; i < 5; i++) {
            collection.add("Item" + i);
        }

        collection.clear();

        assertThat(collection.size()).isZero();
        assertThat(collection.capacity()).isEqualTo(10); // DEFAULT_CAPACITY
        assertThat(collection.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("copy создает независимую копию с теми же элементами")
    void copyCreatesIndependentCopyTest() {
        collection.add("A");
        collection.add("B");

        CustomCollection<String> copy = collection.copy();

        assertThat(copy.size()).isEqualTo(2);
        assertThat(copy.get(0)).isEqualTo("A");
        assertThat(copy.get(1)).isEqualTo("B");
        assertThat(copy.capacity()).isEqualTo(collection.capacity());

        // Изменяем оригинал — копия не должна меняться
        collection.add("C");
        assertThat(copy.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("swap меняет местами два элемента в коллекции")
    void swapExchangesTwoElementsTest() {
        collection.add("A");
        collection.add("B");
        collection.add("C");

        collection.swap(collection, 0, 2);

        assertThat(collection.get(0)).isEqualTo("C");
        assertThat(collection.get(2)).isEqualTo("A");
        assertThat(collection.get(1)).isEqualTo("B");
    }

    @Test
    @DisplayName("swap с невалидными индексами, бросает исключение")
    void swapWithInvalidIndexThrowsExceptionTest() {
        collection.add("A");
        collection.add("B");

        assertThrows(IndexOutOfBoundsException.class, () -> collection.swap(collection, -1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.swap(collection, 0, 2));
    }

    @Test
    @DisplayName("ensureCapacity расширяет массив, если нужно")
    void ensureCapacityExpandsArrayTest() {
        // Изначально capacity = 10
        assertThat(collection.capacity()).isEqualTo(10);

        collection.ensureCapacity(15);

        assertThat(collection.capacity()).isGreaterThanOrEqualTo(15);
    }

    @Test
    @DisplayName("ensureCapacity ничего не делает, если capacity достаточна")
    void ensureCapacityDoesNothingIfEnoughTest() {
        int oldCapacity = collection.capacity();
        collection.ensureCapacity(5); // 5 < 10 — ничего не должно измениться

        assertThat(collection.capacity()).isEqualTo(oldCapacity);
    }

    // Граничный случай: удаление последнего элемента
    @Test
    @DisplayName("remove последнего элемента работает корректно")
    void removeLastElementWorks() {
        collection.add("A");
        collection.add("B");

        collection.remove(1); // Удаляем последний

        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection.get(0)).isEqualTo("A");
    }

    // Граничный случай: удаление единственного элемента
    @Test
    @DisplayName("remove единственного элемента оставляет пустую коллекцию")
    void removeSingleElementLeavesEmpty() {
        collection.add("Solo");

        collection.remove(0);

        assertThat(collection.isEmpty()).isTrue();
        assertThat(collection.size()).isZero();
    }
}
