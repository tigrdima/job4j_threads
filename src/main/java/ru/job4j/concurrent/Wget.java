package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

public class Wget implements Runnable {
    private final String url;
    private final String toPath;
    private final int speed;
    private static long startLoading;

    public Wget(String url, String toPath, int speed) {
        this.url = url;
        this.toPath = toPath;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(toPath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrite = 0;
            long start = Calendar.getInstance().getTimeInMillis();
            startLoading = start;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrite += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);

                if (bytesWrite >= speed) {
                    long end = Calendar.getInstance().getTimeInMillis();
                    long deltaTime = end - start;
                    start = end;
                    bytesWrite = 0;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("The number of arguments must be three.\r\n"
                    + "sample :\r\n"
                    + "first argument(URL) - \"https://adress.net/files/111.txt\"\r\n"
                    + "second argument(path to download) - c:/dir/file.txt\r\n"
                    + "third argument(MByte/s) - 1048576");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);

        String url = args[0];
        String toPath = args[1];
        int speed = Integer.parseInt(args[2]);

        Thread wget = new Thread(new Wget(url, toPath, speed));
        wget.start();
        wget.join();
        System.out.println("\r\nfile downloaded");
        System.out.println("loading time : " + ((Calendar.getInstance().getTimeInMillis() - startLoading)) / 1000 + " sec");
    }
}
