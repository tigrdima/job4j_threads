package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            String[] process = new String[]{"\\", "|", "/"};
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (i > 2) {
                    i = 0;
                }
                System.out.print("\r load: " + process[i++]);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
