package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.ArrayCollection;
import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.menu.strategy.FileLoadStrategy;
import org.aston.learning.stage1.service.FileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class BusFileLoadStrategy implements FileLoadStrategy<Bus> {
    @Override
    public CustomCollection<Bus> loadFromFile(String filename) throws IOException {
        return FileManager.loadBusesFromFile(filename);
    }

    @Override
    public CustomCollection<Bus> loadFromFile(InputStream is) throws IOException {
        return FileManager.loadBusesFromFile(is);

    }

    @Override
    public String getFileFormatDescription() {
        return FileManager.getBusFileFormatDescription();
    }
}