package org.aston.learning.stage1.menu.strategy.impl;

import org.aston.learning.stage1.collection.CustomCollection;
import org.aston.learning.stage1.menu.strategy.FileSaveStrategy;
import org.aston.learning.stage1.model.Bus;
import org.aston.learning.stage1.service.FileManager;

import java.io.IOException;

public class BusFileSaveStrategy implements FileSaveStrategy<Bus> {
    @Override
    public void saveToFile(CustomCollection<Bus> collection) throws IOException {
        FileManager.saveBusesToFile(collection);
    }
}
