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
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length == 3) {
                    Bus bus = new Bus(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim())
                    );
                    buses.add(bus);
                }
            }
        }
        return buses;
    }

    @Override
    public String getFileFormatDescription() {
        return "Формат: Номер;Модель;Пробег";
    }
}