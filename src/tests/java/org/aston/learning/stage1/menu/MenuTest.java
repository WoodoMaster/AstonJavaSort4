package org.aston.learning.stage1.menu;

import org.aston.learning.stage1.testutils.TestReflectionUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuTest {
    @Test
    void testConstructorWithTitleOnly() {
        Menu menu = new Menu("Главное меню");

        assertEquals("Главное меню", menu.getTitle());
        assertEquals("Главное меню", menu.getPath());
        assertFalse(menu.isRunning());
        assertNotNull(menu.getItems().get("ВЫХОД"));
    }

    @Test
    void testConstructorWithParentTitle() {
        Menu menu = new Menu("Подменю", "Главное меню");

        assertEquals("Подменю", menu.getTitle());
        assertEquals("Главное меню > Подменю", menu.getPath());
        assertFalse(menu.isRunning());
        assertNotNull(menu.getItems().get("НАЗАД"));
    }

    @Test
    void testAddMenuAction() {
        Menu menu = new Menu("Тест");
        MenuAction mockAction = mock(MenuAction.class);

        menu.addAction("Тестовое действие", mockAction);

        assertTrue(menu.getItems().containsKey("Тестовое действие"));
        assertSame(mockAction, menu.getItems().get("Тестовое действие"));
    }

    @Test
    void testAddMenuActionsWithTheSameTitles() {
        Menu menu = new Menu("Тест");
        MenuAction mockAction1 = mock(MenuAction.class);
        MenuAction mockAction2 = mock(MenuAction.class);

        menu.addAction("Тестовое действие", mockAction1);
        menu.addAction("Тестовое действие", mockAction2);

        assertTrue(menu.getItems().containsKey("Тестовое действие"));
        assertNotSame(mockAction1, menu.getItems().get("Тестовое действие"));
        assertSame(mockAction2, menu.getItems().get("Тестовое действие"));
    }

    @Test
    void testDisplayActions() {
        try {
            Menu menu = new Menu("Тест");
            menu.addAction("Действие 1", mock(MenuAction.class));
            menu.addAction("Действие 2", mock(MenuAction.class));

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            // Тестируем private метод через рефлексию
            TestReflectionUtils.invokePrivateMethod(menu, "displayActions");

            String output = outContent.toString();
            assertTrue(output.contains("1. Действие 1"));
            assertTrue(output.contains("2. Действие 2"));
            assertTrue(output.contains("0. ВЫХОД"));

            System.setOut(originalOut);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    void testEmptyMenu() {
        Menu menu = new Menu("Пустое меню");

        // Меню без действий должно корректно обрабатываться
        assertDoesNotThrow(() -> TestReflectionUtils.invokePrivateMethod(menu, "displayActions"));
    }

    @Test
    void testDisplayHeader() {
        try {
            Menu menu = new Menu("Главное меню");

            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            menu.displayHeader("Подраздел");

            String output = outContent.toString();
            assertTrue(output.contains("Главное меню > Подраздел"));
            assertTrue(output.contains("Текущая коллекция:"));

            System.setOut(originalOut);

        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    void testExecuteChoiceWithValidIndex() {
        try {
            Menu menu = new Menu("Тест");
            MenuAction mockAction1 = mock(MenuAction.class);
            MenuAction mockAction2 = mock(MenuAction.class);

            menu.addAction("Действие 1", mockAction1);
            menu.addAction("Действие 2", mockAction2);

            // Тестируем private метод через рефлексию
            TestReflectionUtils.invokePrivateMethod(menu, "executeChoice", 1);

            verify(mockAction1).execute();
            verify(mockAction2, never()).execute();
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }
}