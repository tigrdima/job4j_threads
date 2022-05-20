package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class ThreadPool {
    private final SimpleBlockingQueue<Runnable> tasks;
    private final List<Thread> threads = new LinkedList<>();

    public ThreadPool(int sizeTasks) {
        this.tasks = new SimpleBlockingQueue<>(sizeTasks);
        int numberCores = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < numberCores; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
        }
        for (Thread job : threads) {
            new Thread(job).start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        this.tasks.offer(job);
    }

    public synchronized void shutdown() {
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(10);

        for (int i = 0; i < 25; i++) {
            int task = i;
            pool.work(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "Task:" + task);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
