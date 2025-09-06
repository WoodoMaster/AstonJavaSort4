package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.model.Bus;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BusFileSaveStrategy implements FileSaveStrategy<Bus> {
    @Override
    public void saveToFile(String filename, CustomCollection<Bus> collection) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < collection.size(); i++) {
                Bus bus = collection.get(i);
                writer.println(bus.getNumber() + ";" +
                        bus.getModel() + ";" +
                        bus.getMileage());
            }
        }
    }
}
