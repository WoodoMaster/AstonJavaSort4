package org.aston.learning.stage1.strategy.impl;

import org.aston.learning.stage1.menu.strategy.impl.UserHandler;
import org.aston.learning.stage1.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class UserHandlerTest {

    private UserHandler userHandler;

    @BeforeEach
    public void setUp() {
        userHandler = new UserHandler();
    }

    @Test
    @DisplayName("Тест метода getTypeName")
    public void testGetTypeName() {
        String typeName = userHandler.getTypeName();

        assertEquals("User", typeName, "Тип должен быть 'User'");
    }

    @Test
    @DisplayName("Тест метода getTableHeaders")
    public void testGetTableHeaders() {
        String[] headers = userHandler.getTableHeaders();

        assertNotNull(headers, "Заголовки не должны быть null");
        assertEquals(4, headers.length, "Должно быть 4 заголовка");
        assertArrayEquals(new String[]{"№", "Имя", "Email", "Пароль"}, headers,
                "Заголовки должны совпадать");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow")
    public void testConvertToTableRow() {
        User user = new User("Иван", "mypassword123", "ivan@example.com");
        int index = 0;

        String[] row = userHandler.convertToTableRow(user, index);

        assertNotNull(row, "Строка таблицы не должна быть null");
        assertEquals(4, row.length, "Должно быть 4 элемента в строке");
        assertArrayEquals(new String[]{"1", "Иван", "ivan@example.com", "*************"}, row,
                "Строка таблицы должна содержать правильные значения");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с пустыми строками")
    public void testConvertToTableRowWithEmptyStrings() {
        User user = new User("", "", "");
        int index = 0;

        String[] row = userHandler.convertToTableRow(user, index);

        assertArrayEquals(new String[]{"1", "", "", ""}, row,
                "Пустые строки должны отображаться корректно");
    }

    @Test
    @DisplayName("Тест метода getTableHeaders возвращает новый массив")
    public void testGetTableHeadersReturnsNewArray() {
        String[] headers1 = userHandler.getTableHeaders();
        String[] headers2 = userHandler.getTableHeaders();

        assertNotSame(headers1, headers2, "Должны возвращаться разные массивы");
        assertArrayEquals(headers1, headers2, "Содержимое массивов должно быть одинаковым");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow возвращает новый массив")
    public void testConvertToTableRowReturnsNewArray() {
        User user = new User("Тест", "Тест", "Тест");

        String[] row1 = userHandler.convertToTableRow(user, 0);
        String[] row2 = userHandler.convertToTableRow(user, 0);

        assertNotSame(row1, row2, "Должны возвращаться разные массивы");
        assertArrayEquals(row1, row2, "Содержимое массивов должно быть одинаковым");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с отрицательным индексом")
    public void testConvertToTableRowWithNegativeIndex() {
        User user = new User("Тест", "pass", "test@example.com");
        int index = -1;

        String[] row = userHandler.convertToTableRow(user, index);

        assertEquals("0", row[0], "Отрицательный индекс + 1 должен давать 0");
    }

    @Test
    @DisplayName("Тест маскирования пароля с различной длиной")
    public void testPasswordMaskingWithVariousLengths() {
        String[] passwords = {"", "1", "12", "123", "12345678901234567890"}; // 0, 1, 2, 3, 20 символов
        String[] expectedMasks = {"", "*", "**", "***", "********************"};

        for (int i = 0; i < passwords.length; i++) {
            User user = new User("User", passwords[i], "test@example.com");
            String[] row = userHandler.convertToTableRow(user, 0);
            assertEquals(expectedMasks[i], row[3],
                    "Пароль длиной " + passwords[i].length() + " должен маскироваться как '" + expectedMasks[i] + "'");
        }
    }

    @Test
    @DisplayName("Тест всех методов с одним и тем же пользователем")
    public void testAllMethodsConsistency() {
        User user = new User("Администратор", "admin123", "admin@system.ru");
        String[] expectedHeaders = {"№", "Имя", "Email", "Пароль"};
        String[] expectedRow = {"1", "Администратор", "admin@system.ru", "********"};

        String typeName = userHandler.getTypeName();
        String[] headers = userHandler.getTableHeaders();
        String[] row = userHandler.convertToTableRow(user, 0);

        assertEquals("User", typeName);
        assertArrayEquals(expectedHeaders, headers);
        assertArrayEquals(expectedRow, row);
    }
}