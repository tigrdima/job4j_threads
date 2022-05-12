package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long start = Calendar.getInstance().getTimeInMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long end = Calendar.getInstance().getTimeInMillis();
                System.out.print("\rloading ...");
                if ((end - start) < speed) {
                    Thread.sleep(speed);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
        System.out.println("\r\nfile downloaded");
    }
}
