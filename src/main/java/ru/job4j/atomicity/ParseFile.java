package ru.job4j.atomicity;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        String output = "";
        int data;
        while ((data = in.read()) > 0) {
            if (filter.test((char) data)) {
                output += (char) data;
            }
        }
        return output;
    }
}

final class SaveFileContent {
    public synchronized void saveContent(String content) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("c/.."));

        for (int i = 0; i < content.length(); i += 1) {
            out.write(content.charAt(i));
        }
    }
}
