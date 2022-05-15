package ru.job4j.concurrent;

public class ParallelSearch {

    public static void main(String[] args) {
        int indexMax = 3;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();

        final boolean run;
        final Thread consumer = new Thread(
                () -> {
                    int i = 0;
                    while (i++ != indexMax) {
                        int index = queue.poll();
                        System.out.println(index);
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {

                    for (int index = 0; index != indexMax; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
    }
}
