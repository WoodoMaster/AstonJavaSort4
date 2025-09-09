package org.aston.learning.stage1.service;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.util.console.ConsoleUtils;
import org.aston.learning.stage1.util.console.InputPattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileManager {
    private static final String userHomeFolder = System.getProperty("user.home") + File.separator + "/Desktop";

    // Загрузка студентов из файла
    public static CustomCollection<Student> loadStudentsFromFile(String filename) throws IOException {
        CustomCollection<Student> students = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Неверное количество полей");
                    }

                    String groupNumber = parts[0].trim();
                    double averageGrade = Double.parseDouble(parts[1].trim());
                    String recordBookNumber = parts[2].trim();

                    // Валидация данных
                    validateStudents(groupNumber, averageGrade, recordBookNumber);

                    Student student = new Student(groupNumber, averageGrade, recordBookNumber);
                    students.add(student);

                } catch (Exception e) {
                    ConsoleUtils.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return students;
    }


    public static CustomCollection<Student> loadStudentsFromFile(InputStream is) throws IOException {
        CustomCollection<Student> students = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Неверное количество полей");
                    }

                    String groupNumber = parts[0].trim();
                    double averageGrade = Double.parseDouble(parts[1].trim());
                    String recordBookNumber = parts[2].trim();
                    
                    // Валидация данных
                    validateStudents(groupNumber, averageGrade, recordBookNumber);
                    
                    Student student = new Student(groupNumber, averageGrade, recordBookNumber);
                    students.add(student);

                } catch (Exception e) {
                    ConsoleUtils.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return students;
    }
    
    private static void validateStudents(String groupNumber, double averageGrade, String recordBookNumber) {
        if (!groupNumber.matches(InputPattern.STUDENT_GROUP.getRegex())) {
            throw new IllegalArgumentException("Неверный формат группы: " + groupNumber);
        }

        if (averageGrade < 0.0 || averageGrade > 5.0) {
            throw new IllegalArgumentException("Средний балл должен быть от 0.0 до 5.0");
        }

        if (!recordBookNumber.matches(InputPattern.RECORD_BOOK.getRegex())) {
            throw new IllegalArgumentException("Неверный формат номера зачетки: " + recordBookNumber);
        }
    }

    // Сохранение студентов в файл
    public static void saveStudentsToFile(CustomCollection<Student> collection) throws IOException {
        File studentsFile = new File(userHomeFolder, "Students.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(studentsFile))) {
            for (int i = 0; i < collection.size(); i++) {
                Student student = collection.get(i);
                writer.println(student.getGroupNumber() + "," +
                        student.getAverageGrade() + "," +
                        student.getRecordBookNumber());
            }
        }
    }

    // Загрузка пользователей из файла
    public static CustomCollection<User> loadUsersFromFile(String filename) throws IOException {
        CustomCollection<User> users = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split("\\|");
                    User user = getUser(parts);
                    users.add(user);

                } catch (Exception e) {
                    ConsoleUtils.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return users;
    }

    private static User getUser(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверное количество полей");
        }

        String name = parts[0].trim();
        String password = parts[1].trim();
        String email = parts[2].trim();

        // Валидация данных
        if (!name.matches(InputPattern.NAME.getRegex())) {
            throw new IllegalArgumentException("Неверный формат имени: " + name);
        }

        if (!password.matches(InputPattern.PASSWORD.getRegex())) {
            throw new IllegalArgumentException("Неверный формат пароля");
        }

        if (!email.matches(InputPattern.EMAIL.getRegex())) {
            throw new IllegalArgumentException("Неверный формат email: " + email);
        }

        return new User(name, password, email);
    }

    public static CustomCollection<User> loadUsersFromFile(InputStream is) throws IOException {
        CustomCollection<User> users = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split("\\|");
                    User user = getUser(parts);
                    users.add(user);

                } catch (Exception e) {
                    ConsoleUtils.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return users;
    }

    // Сохранение пользователей в файл
    public static void saveUsersToFile(CustomCollection<User> collection) throws IOException {
        File usersFile = new File(userHomeFolder, "Users.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(usersFile))) {
            for (int i = 0; i < collection.size(); i++) {
                User user = collection.get(i);
                writer.println(user.getName() + "|" +
                        user.getPassword() + "|" +
                        user.getEmail());
            }
        }
    }

    // Загрузка автобусов из файла
    public static CustomCollection<Bus> loadBusesFromFile(String filename) throws IOException {
        CustomCollection<Bus> buses = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split(";");
                    Bus bus = getBus(parts);
                    buses.add(bus);

                } catch (Exception e) {
                    ConsoleUtils.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return buses;
    }

    public static CustomCollection<Bus> loadBusesFromFile(InputStream is) throws IOException {
        CustomCollection<Bus> buses = new ArrayCollection<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split(";");
                    Bus bus = getBus(parts);
                    buses.add(bus);

                } catch (Exception e) {
                    ConsoleUtils.printError("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return buses;
    }

    private static Bus getBus(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверное количество полей");
        }

        String number = parts[0].trim();
        String model = parts[1].trim();
        int mileage = Integer.parseInt(parts[2].trim());

        // Валидация данных
        if (!number.matches(InputPattern.BUS_NUMBER.getRegex())) {
            throw new IllegalArgumentException("Неверный формат номера автобуса: " + number);
        }
        if (!model.matches(InputPattern.BUS_MODEL.getRegex())) {
            throw new IllegalArgumentException("Неверный формат модели автобуса: " + model);
        }
        if (mileage < 0) {
            throw new IllegalArgumentException("Пробег не может быть отрицательным");
        }

        return new Bus(number, model, mileage);
    }

    // Сохранение автобусов в файл
    public static void saveBusesToFile(CustomCollection<Bus> collection) throws IOException {
        File busFile = new File(userHomeFolder, "Buses.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(busFile))) {
            for (int i = 0; i < collection.size(); i++) {
                Bus bus = collection.get(i);
                writer.println(bus.getNumber() + ";" +
                        bus.getModel() + ";" +
                        bus.getMileage());
            }
        }
    }

    // Универсальные методы для получения описания форматов
    public static String getStudentFileFormatDescription() {
        return "Формат: Группа,Средний балл,Номер зачетки\n" +
                "Группа: " + InputPattern.STUDENT_GROUP.getErrorMessage() + "\n" +
                "Номер зачетки: " + InputPattern.RECORD_BOOK.getErrorMessage() + "\n" +
                "Средний балл: от 0.0 до 5.0";
    }

    public static String getUserFileFormatDescription() {
        return "Формат: Имя|Пароль|Email\n" +
                "Имя: " + InputPattern.NAME.getErrorMessage() + "\n" +
                "Пароль: " + InputPattern.PASSWORD.getErrorMessage() + "\n" +
                "Email: " + InputPattern.EMAIL.getErrorMessage();
    }

    public static String getBusFileFormatDescription() {
        return "Формат: Номер;Модель;Пробег\n" +
                "Номер: " + InputPattern.BUS_NUMBER.getErrorMessage() + "\n" +
                "Модель: " + InputPattern.BUS_MODEL.getErrorMessage() + "\n" +
                "Пробег: неотрицательное целое число";
    }
}
