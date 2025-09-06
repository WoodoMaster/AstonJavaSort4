package org.aston.learning.stage1.menu.strategy;

import org.aston.learning.stage1.collection.CustomCollection;

import java.io.IOException;

public interface FileSaveStrategy<T> {
    void saveToFile(String fileName, CustomCollection<T> collection) throws IOException;
}
