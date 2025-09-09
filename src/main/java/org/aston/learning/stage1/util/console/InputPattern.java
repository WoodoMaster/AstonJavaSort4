package org.aston.learning.stage1.util.console;

public enum InputPattern {
    // Email
    EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            "Неверный формат email! Пример: name@mail.ru"),

    // Группа студента
    STUDENT_GROUP("^\\d{2}-[A-z]{2,4}(-\\d{1,2}){0,2}$",
            "Неверный формат группы! Пример: 22-Vt, 22-SUd-1"),

    // Номер зачетки
    RECORD_BOOK("^\\d{2,3}-\\d{2,3}\\\\\\d{1,2}$",
            "Неверный формат зачетки! Пример: 12-39\\6"),

    // Модель автобуса
    BUS_MODEL("^[A-z]{2,9}\\d{0,2}(-\\d{1,2})?$",
            "Неверный формат модели! Пример: VAZ1, Liaz"),

    // Номер автобуса
    BUS_NUMBER("^\\d{2,3}[A-Z]?$",
            "Неверный формат номера! Пример: 47, 53B"),

    // Имя
    NAME("^[A-Za-zА-Яа-я\\s-]{2,50}$",
            "Имя должно содержать только буквы и быть от 2 до 50 символов"),

    // Пароль
    PASSWORD("^.{6,20}$",
            "Пароль должен быть от 6 до 20 символов");

    private final String regex;
    private final String errorMessage;

    InputPattern(String regex, String errorMessage) {
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    public String getRegex() {
        return regex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}