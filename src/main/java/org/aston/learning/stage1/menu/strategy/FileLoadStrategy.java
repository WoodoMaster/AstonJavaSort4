package org.aston.learning.stage1.menu.strategy;

import org.aston.learning.stage1.collection.CustomCollection;

import java.io.IOException;

public interface FileLoadStrategy<T> {
    CustomCollection<T> loadFromFile(String filename) throws IOException;
    String getFileFormatDescription();
}