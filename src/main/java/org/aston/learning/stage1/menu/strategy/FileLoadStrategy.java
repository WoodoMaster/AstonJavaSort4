package org.aston.learning.stage1.menu.strategy;

import org.aston.learning.stage1.collection.CustomCollection;

import java.io.IOException;
import java.io.InputStream;

public interface FileLoadStrategy<T> {
    CustomCollection<T> loadFromFile(String filename) throws IOException;
    CustomCollection<T> loadFromFile(InputStream is) throws IOException;
    String getFileFormatDescription();
}