package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BusFileLoadStrategy implements FileLoadStrategy<Bus> {
    @Override
    public CustomCollection<Bus> loadFromFile(String filename) throws IOException {
        // TODO: Загрузка из файла
        // *** Пример - загрузка из файла
        CustomCollection<Bus> buses = new ArrayCollection<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split(";");
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Неверное количество полей");
                    }
                    Bus bus = new Bus(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim()));
                    if (validateData(bus)) {
                        buses.add(bus);
                    } else {
                        System.out.println("Пропущена строка " + lineNumber + ": невалидные данные");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        return buses;
    }

    @Override
    public String getFileFormatDescription() {
        return "Формат: Номер;Модель;Пробег";
    }

    @Override
    public boolean validateData(Bus bus) {
        return bus.getNumber() != null && !bus.getNumber().isEmpty() &&
                bus.getModel() != null && !bus.getModel().isEmpty() &&
                bus.getMileage() >= 0;
    }
}