package ru.job4j.atomicity.parsefile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFileContent {
    public synchronized void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("c/.."))) {

            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
