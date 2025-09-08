import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.model.Student;
import org.aston.learning.stage1.model.User;
import org.aston.learning.stage1.service.FileManager;
import org.aston.learning.stage1.util.console.ConsoleUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileManagerTest {

    //Создание временных директорий
    @TempDir
    Path tempDir;

    private File tempStudentFile;
    private File tempUserFile;
    private File tempBusFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempStudentFile = tempDir.resolve("students.txt").toFile();
        tempUserFile = tempDir.resolve("users.txt").toFile();
        tempBusFile = tempDir.resolve("buses.txt").toFile();
    }

    // Вспомогательный метод для записи в файл
    private void writeToFile(File file, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        }
    }

    @Test
    @DisplayName("Тест loadStudentsFromFile с корректными данными из файла")
    public void testLoadStudentsFromFileWithValidData() throws IOException {
        String content = "20-vg,4.75,12-12\\2\n30-gg,3.89,11-11\\3";
        writeToFile(tempStudentFile, content);

        CustomCollection<Student> students = FileManager.loadStudentsFromFile(tempStudentFile.getAbsolutePath());

        assertNotNull(students);
        assertEquals(2, students.size());
        Student student1 = students.get(0);
        assertEquals("20-vg", student1.getGroupNumber());
        assertEquals(4.75, student1.getAverageGrade());
        assertEquals("12-12\\2", student1.getRecordBookNumber());

        Student student2 = students.get(1);
        assertEquals("30-gg", student2.getGroupNumber());
        assertEquals(3.89, student2.getAverageGrade());
        assertEquals("11-11\\3", student2.getRecordBookNumber());
    }

    @Test
    @DisplayName("Тест loadStudentsFromFile с InputStream")
    public void testLoadStudentsFromFileWithInputStream() throws IOException {
        String content = "20-vg,4.75,12-12\\2\n30-gg,3.89,11-11\\3";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        CustomCollection<Student> students = FileManager.loadStudentsFromFile(inputStream);

        assertNotNull(students);
        assertEquals(2, students.size());
        Student student1 = students.get(0);
        assertEquals("20-vg", student1.getGroupNumber());
        assertEquals(4.75, student1.getAverageGrade());
        assertEquals("12-12\\2", student1.getRecordBookNumber());
    }

    @Test
    @DisplayName("Тест loadStudentsFromFile с некорректными данными")
    public void testLoadStudentsFromFileWithInvalidData() throws IOException {
        String content = "20-vg,4.75,12-12\\2\nНеверная строка\n30-gg,3.89,11-11\\3";

        writeToFile(tempStudentFile, content);

        try (MockedStatic<ConsoleUtils> mockedConsoleUtils = mockStatic(ConsoleUtils.class)) {
            CustomCollection<Student> students = FileManager.loadStudentsFromFile(tempStudentFile.getAbsolutePath());

            assertEquals(2, students.size()); // Только 2 корректные строки
            mockedConsoleUtils.verify(() -> ConsoleUtils.printError(anyString()), times(1));
        }
    }

    @Test
    @DisplayName("Тест loadStudentsFromFile с пустыми строками")
    public void testLoadStudentsFromFileWithEmptyLines() throws IOException {
        String content = "20-vg,4.75,12-12\\5\n  \n30-gg,3.89,11-11\\3";
        writeToFile(tempStudentFile, content);

        CustomCollection<Student> students = FileManager.loadStudentsFromFile(tempStudentFile.getAbsolutePath());

        assertEquals(2, students.size());
        assertEquals("20-vg", students.get(0).getGroupNumber());
        assertEquals("30-gg", students.get(1).getGroupNumber());
    }

    @Test
    @DisplayName("Тест loadStudentsFromFile с невалидными данными")
    public void testLoadStudentsFromFileWithInvalidValidation() throws IOException {
        String content = "НевернаяГруппа,4.75,ЗЧ-12345\nИТ-201,6.0,ЗЧ-67890"; // Неверный формат группы и оценка > 5.0
        writeToFile(tempStudentFile, content);

        try (MockedStatic<ConsoleUtils> mockedConsoleUtils = mockStatic(ConsoleUtils.class)) {
            CustomCollection<Student> students = FileManager.loadStudentsFromFile(tempStudentFile.getAbsolutePath());

            // Все строки невалидны
            assertEquals(0, students.size());
            mockedConsoleUtils.verify(() -> ConsoleUtils.printError(anyString()), times(2));
        }
    }

    @Test
    @DisplayName("Тест saveStudentsToFile")
    public void testSaveStudentsToFile() throws IOException {
        CustomCollection<Student> students = new ArrayCollection<>();
        students.add(new Student("ИТ-201", 4.75, "ЗЧ-145"));
        students.add(new Student("МТ-305", 3.89, "ЗЧ-690"));

        FileManager.saveStudentsToFile(students);

        File savedFile = new File(System.getProperty("user.home") + File.separator + "/Desktop", "Students.txt");
        assertTrue(savedFile.exists());

        // Проверяем содержимое файла
        try (Scanner scanner = new Scanner(savedFile)) {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            assertEquals("ИТ-201,4.75,ЗЧ-145", line1);
            assertEquals("МТ-305,3.89,ЗЧ-690", line2);
        }
    }

    @Test
    @DisplayName("Тест loadUsersFromFile с корректными данными")
    public void testLoadUsersFromFileWithValidData() throws IOException {
        String content = "Иван|test123|ivan@example.com";
        writeToFile(tempUserFile, content);

        CustomCollection<User> users = FileManager.loadUsersFromFile(tempUserFile.getAbsolutePath());

        assertNotNull(users);
        assertEquals(1, users.size());
        User user1 = users.get(0);
        assertEquals("Иван", user1.getName());
        assertEquals("test123", user1.getPassword());
        assertEquals("ivan@example.com", user1.getEmail());
    }

    @Test
    @DisplayName("Тест loadUsersFromFile с InputStream")
    public void testLoadUsersFromFileWithInputStream() throws IOException {
        String content = "Иван|password123|ivan@example.com";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        CustomCollection<User> users = FileManager.loadUsersFromFile(inputStream);

        assertNotNull(users);
        assertEquals(1, users.size());
        User user1 = users.get(0);
        assertEquals("Иван", user1.getName());
        assertEquals("password123", user1.getPassword());
        assertEquals("ivan@example.com", user1.getEmail());
    }

    @Test
    @DisplayName("Тест saveUsersToFile")
    public void testSaveUsersToFile() throws IOException {
        CustomCollection<User> users = new ArrayCollection<>();
        users.add(new User("Иван", "test123", "ivan@example.com"));
        users.add(new User("Мария", "test456", "maria@test.com"));

        FileManager.saveUsersToFile(users);

        File savedFile = new File(System.getProperty("user.home") + File.separator + "/Desktop", "Users.txt");
        assertTrue(savedFile.exists());

        // Проверяем содержимое файла
        try (Scanner scanner = new Scanner(savedFile)) {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            assertEquals("Иван|test123|ivan@example.com", line1);
            assertEquals("Мария|test456|maria@test.com", line2);
        }
    }

    @Test
    @DisplayName("Тест loadBusesFromFile с корректными данными")
    public void testLoadBusesFromFileWithValidData() throws IOException {
        String content = "77;Vaz;15000";
        writeToFile(tempBusFile, content);

        CustomCollection<Bus> buses = FileManager.loadBusesFromFile(tempBusFile.getAbsolutePath());

        assertNotNull(buses);
        assertEquals(1, buses.size());
        Bus bus1 = buses.get(0);
        assertEquals("77", bus1.getNumber());
        assertEquals("Vaz", bus1.getModel());
        assertEquals(15000, bus1.getMileage());
    }

    @Test
    @DisplayName("Тест loadBusesFromFile с InputStream")
    public void testLoadBusesFromFileWithInputStream() throws IOException {
        String content = "77;PAZ;15000";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        CustomCollection<Bus> buses = FileManager.loadBusesFromFile(inputStream);

        assertNotNull(buses);
        assertEquals(1, buses.size());
        Bus bus1 = buses.get(0);
        assertEquals("77", bus1.getNumber());
        assertEquals("PAZ", bus1.getModel());
        assertEquals(15000, bus1.getMileage());
    }

    @Test
    @DisplayName("Тест saveBusesToFile")
    public void testSaveBusesToFile() throws IOException {
        CustomCollection<Bus> buses = new ArrayCollection<>();
        buses.add(new Bus("77", "PAZ", 15000));
        buses.add(new Bus("23", "Mercedes", 25000));

        FileManager.saveBusesToFile(buses);

        File savedFile = new File(System.getProperty("user.home") + File.separator + "/Desktop", "Buses.txt");
        assertTrue(savedFile.exists());

        // Проверяем содержимое файла
        try (Scanner scanner = new Scanner(savedFile)) {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            assertEquals("77;PAZ;15000", line1);
            assertEquals("23;Mercedes;25000", line2);
        }
    }

    @Test
    @DisplayName("Тест getStudentFileFormatDescription")
    public void testGetStudentFileFormatDescription() {
        String description = FileManager.getStudentFileFormatDescription();

        assertNotNull(description);
        assertTrue(description.contains("Формат: Группа,Средний балл,Номер зачетки"));
        assertTrue(description.contains("Средний балл: от 0.0 до 5.0"));
    }

    @Test
    @DisplayName("Тест getUserFileFormatDescription")
    public void testGetUserFileFormatDescription() {
        String description = FileManager.getUserFileFormatDescription();

        assertNotNull(description);
        assertTrue(description.contains("Формат: Имя|Пароль|Email"));
    }

    @Test
    @DisplayName("Тест getBusFileFormatDescription")
    public void testGetBusFileFormatDescription() {
        String description = FileManager.getBusFileFormatDescription();

        assertNotNull(description);
        assertTrue(description.contains("Формат: Номер;Модель;Пробег"));
        assertTrue(description.contains("Пробег: неотрицательное целое число"));
    }

    @Test
    @DisplayName("Тест loadStudentsFromFile с несуществующим файлом")
    public void testLoadStudentsFromFileWithNonExistentFile() {
        String nonExistentFile = tempDir.resolve("nonexistent.txt").toAbsolutePath().toString();

        assertThrows(FileNotFoundException.class, () -> {
            FileManager.loadStudentsFromFile(nonExistentFile);
        });
    }

    @Test
    @DisplayName("Тест loadUsersFromFile с несуществующим файлом")
    public void testLoadUsersFromFileWithNonExistentFile() {
        String nonExistentFile = tempDir.resolve("nonexistent.txt").toAbsolutePath().toString();

        assertThrows(FileNotFoundException.class, () -> {
            FileManager.loadUsersFromFile(nonExistentFile);
        });
    }

    @Test
    @DisplayName("Тест loadBusesFromFile с несуществующим файлом")
    public void testLoadBusesFromFileWithNonExistentFile() {
        String nonExistentFile = tempDir.resolve("nonexistent.txt").toAbsolutePath().toString();

        assertThrows(FileNotFoundException.class, () -> {
            FileManager.loadBusesFromFile(nonExistentFile);
        });
    }

    @Test
    @DisplayName("Тест загрузки пустого файла")
    public void testLoadEmptyFile() throws IOException {
        writeToFile(tempStudentFile, "");

        CustomCollection<Student> students = FileManager.loadStudentsFromFile(tempStudentFile.getAbsolutePath());

        assertEquals(0, students.size());
    }

    @Test
    @DisplayName("Тест загрузки файла только с пустыми строками")
    public void testLoadFileWithOnlyEmptyLines() throws IOException {
        writeToFile(tempStudentFile, "\n\n  \n\t\n");

        CustomCollection<Student> students = FileManager.loadStudentsFromFile(tempStudentFile.getAbsolutePath());

        assertEquals(0, students.size());
    }

    @Test
    @DisplayName("Тест загрузки файла с отрицательным пробегом для автобуса")
    public void testLoadBusFileWithNegativeMileage() throws IOException {
        String content = "77;PAZ;-15000";
        writeToFile(tempBusFile, content);

        try (MockedStatic<ConsoleUtils> mockedConsoleUtils = mockStatic(ConsoleUtils.class)) {
            CustomCollection<Bus> buses = FileManager.loadBusesFromFile(tempBusFile.getAbsolutePath());

                assertEquals(0, buses.size());
            mockedConsoleUtils.verify(() -> ConsoleUtils.printError(anyString()), times(1));
        }
    }
}