package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.CASCount;
import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        this.tasks.offer(job);
    }

    public synchronized void shutdown() {
        for (Thread job : threads) {
            job.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        ThreadPool pool = new ThreadPool(20);

        for (int i = 0; i < 30; i++) {
            int task = i;
            pool.work(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "Task:" + task);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
       pool.shutdown();
    }
}
