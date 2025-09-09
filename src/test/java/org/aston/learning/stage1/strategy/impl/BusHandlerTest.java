package org.aston.learning.stage1.strategy.impl;
import org.aston.learning.stage1.menu.strategy.ElementHandler;
import org.aston.learning.stage1.menu.strategy.impl.BusHandler;
import org.aston.learning.stage1.model.Bus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class BusHandlerTest {

    private BusHandler busHandler;

    @BeforeEach
    public void setUp() {
        busHandler = new BusHandler();
    }

    @Test
    @DisplayName("Тест метода getTypeName")
    public void testGetTypeName() {
        String typeName = busHandler.getTypeName();

        assertEquals("Bus", typeName, "Тип должен быть 'Bus'");
    }

    @Test
    @DisplayName("Тест метода getTableHeaders")
    public void testGetTableHeaders() {
        String[] headers = busHandler.getTableHeaders();

        assertNotNull(headers, "Заголовки не должны быть null");
        assertEquals(4, headers.length, "Должно быть 4 заголовка");
        assertArrayEquals(new String[]{"№", "Номер", "Модель", "Пробег"}, headers,
                "Заголовки должны совпадать");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow")
    public void testConvertToTableRow() {
        // Given
        Bus bus = new Bus("77", "PAZ", 15000);
        int index = 0;

        String[] row = busHandler.convertToTableRow(bus, index);

        assertNotNull(row, "Строка таблицы не должна быть null");
        assertEquals(4, row.length, "Должно быть 4 элемента в строке");
        assertArrayEquals(new String[]{"1", "77", "PAZ", "15000 км"}, row,
                "Строка таблицы должна содержать правильные значения");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с отрицательным пробегом")
    public void testConvertToTableRowWithNegativeMileage() {
        Bus bus = new Bus("23", "Vaz", -100);
        int index = 0;

        String[] row = busHandler.convertToTableRow(bus, index);

        assertArrayEquals(new String[]{"1", "23", "Vaz", "-100 км"}, row,
                "Отрицательный пробег должен отображаться корректно");
    }

    @Test
    @DisplayName("Тест метода getTableHeaders возвращает новый массив")
    public void testGetTableHeadersReturnsNewArray() {
        String[] headers1 = busHandler.getTableHeaders();
        String[] headers2 = busHandler.getTableHeaders();

        assertNotSame(headers1, headers2, "Должны возвращаться разные массивы");
        assertArrayEquals(headers1, headers2, "Содержимое массивов должно быть одинаковым");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow возвращает новый массив")
    public void testConvertToTableRowReturnsNewArray() {
        Bus bus = new Bus("TEST", "Model", 1000);

        String[] row1 = busHandler.convertToTableRow(bus, 0);
        String[] row2 = busHandler.convertToTableRow(bus, 0);

        assertNotSame(row1, row2, "Должны возвращаться разные массивы");
        assertArrayEquals(row1, row2, "Содержимое массивов должно быть одинаковым");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с пустыми строками")
    public void testConvertToTableRowWithEmptyStrings() {
        Bus bus = new Bus("", "", 0);
        int index = 0;

        String[] row = busHandler.convertToTableRow(bus, index);

        assertArrayEquals(new String[]{"1", "", "", "0 км"}, row,
                "Пустые строки должны отображаться корректно");
    }
}
