package org.aston.learning.stage1.strategy.impl;

import org.aston.learning.stage1.menu.strategy.impl.StudentHandler;
import org.aston.learning.stage1.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class StudentHandlerTest {

    private StudentHandler studentHandler;

    @BeforeEach
    public void setUp() {
        studentHandler = new StudentHandler();
    }

    @Test
    @DisplayName("Тест метода getTypeName")
    public void testGetTypeName() {
        String typeName = studentHandler.getTypeName();

        assertEquals("Student", typeName, "Тип должен быть 'Student'");
    }

    @Test
    @DisplayName("Тест метода getTableHeaders")
    public void testGetTableHeaders() {
        String[] headers = studentHandler.getTableHeaders();

        assertNotNull(headers, "Заголовки не должны быть null");
        assertEquals(4, headers.length, "Должно быть 4 заголовка");
        assertArrayEquals(new String[]{"№", "Группа", "Средний балл", "Зачетка"}, headers,
                "Заголовки должны совпадать");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow")
    public void testConvertToTableRow() {
        Student student = new Student("ИТ-201", 4.75, "ЗЧ-15");
        int index = 0;

        String[] row = studentHandler.convertToTableRow(student, index);

        assertNotNull(row, "Строка таблицы не должна быть null");
        assertEquals(4, row.length, "Должно быть 4 элемента в строке");
        assertArrayEquals(new String[]{"1", "ИТ-201", "4,75", "ЗЧ-15"}, row,
                "Строка таблицы должна содержать правильные значения");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с граничными значениями среднего балла")
    public void testConvertToTableRowWithBoundaryAverageGrades() {
        Student studentZero = new Student("ГР-01", 0.0, "ЗЧ-01");
        Student studentMax = new Student("ГР-02", 5.0, "ЗЧ-02");
        Student studentWithDecimal = new Student("ГР-03", 4.123, "0");

        String[] rowZero = studentHandler.convertToTableRow(studentZero, 0);
        String[] rowMax = studentHandler.convertToTableRow(studentMax, 0);
        String[] rowDecimal = studentHandler.convertToTableRow(studentWithDecimal, 0);

        assertArrayEquals(new String[]{"1", "ГР-01", "0,00", "ЗЧ-01"}, rowZero,
                "Нулевой средний балл должен отображаться как 0,00");
        assertArrayEquals(new String[]{"1", "ГР-02", "5,00", "ЗЧ-02"}, rowMax,
                "Максимальный средний балл должен отображаться как 5,00");
        assertArrayEquals(new String[]{"1", "ГР-03", "4,12", "0"}, rowDecimal,
                "Средний балл должен округляться до 2 знаков после запятой");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с пустыми строками")
    public void testConvertToTableRowWithEmptyStrings() {
        Student student = new Student("", 3.5, "");
        int index = 0;

        String[] row = studentHandler.convertToTableRow(student, index);

        assertArrayEquals(new String[]{"1", "", "3,50", ""}, row,
                "Пустые строки должны отображаться корректно");
    }

    @Test
    @DisplayName("Тест метода getTableHeaders возвращает новый массив")
    public void testGetTableHeadersReturnsNewArray() {
        String[] headers1 = studentHandler.getTableHeaders();
        String[] headers2 = studentHandler.getTableHeaders();

        assertNotSame(headers1, headers2, "Должны возвращаться разные массивы");
        assertArrayEquals(headers1, headers2, "Содержимое массивов должно быть одинаковым");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow, возвращает новый массив")
    public void testConvertToTableRowReturnsNewArray() {
        Student student = new Student("Тест", 4.0, "Тест");

        String[] row1 = studentHandler.convertToTableRow(student, 0);
        String[] row2 = studentHandler.convertToTableRow(student, 0);

        assertNotSame(row1, row2, "Должны возвращаться разные массивы");
        assertArrayEquals(row1, row2, "Содержимое массивов должно быть одинаковым");
    }

    @Test
    @DisplayName("Тест форматирования среднего балла с разным количеством знаков после запятой")
    public void testAverageGradeFormatting() {
        Student student1 = new Student("ГР1", 4.0, "ЗЧ1");
        Student student2 = new Student("ГР2", 4.1, "ЗЧ2");
        Student student3 = new Student("ГР3", 4.12, "ЗЧ3");
        Student student4 = new Student("ГР4", 4.123, "ЗЧ4");

        String[] row1 = studentHandler.convertToTableRow(student1, 0);
        String[] row2 = studentHandler.convertToTableRow(student2, 0);
        String[] row3 = studentHandler.convertToTableRow(student3, 0);
        String[] row4 = studentHandler.convertToTableRow(student4, 0);

        assertEquals("4,00", row1[2], "Целое число должно форматироваться как X.00");
        assertEquals("4,10", row2[2], "Один знак должен форматироваться как X.Y0");
        assertEquals("4,12", row3[2], "Два знака должны отображаться как есть");
        assertEquals("4,12", row4[2], "Три знака должны округляться до двух");
    }

    @Test
    @DisplayName("Тест метода convertToTableRow с отрицательным индексом")
    public void testConvertToTableRowWithNegativeIndex() {
        Student student = new Student("Тест-01", 3.0, "Те-ст");
        int index = -1;

        String[] row = studentHandler.convertToTableRow(student, index);

        assertEquals("0", row[0], "Отрицательный индекс + 1 должен давать неотрицательное значение");
    }

    @Test
    @DisplayName("Тест всех методов с одним и тем же студентом")
    public void testAllMethodsConsistency() {
        Student student = new Student("КО-202", 4.88, "ЗЧ-99");
        String[] expectedHeaders = {"№", "Группа", "Средний балл", "Зачетка"};
        String[] expectedRow = {"1", "КО-202", "4,88", "ЗЧ-99"};

        String typeName = studentHandler.getTypeName();
        String[] headers = studentHandler.getTableHeaders();
        String[] row = studentHandler.convertToTableRow(student, 0);

        assertEquals("Student", typeName);
        assertArrayEquals(expectedHeaders, headers);
        assertArrayEquals(expectedRow, row);
    }
}