package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    public final static int SIZE = 10;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private synchronized void waiting() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void offer(T value) {
        while (queue.size() >= SIZE) {
            waiting();
        }
        if (queue.size() == 0) {
            this.notifyAll();
        }
        queue.offer(value);
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            waiting();
        }
        if (queue.size() == SIZE) {
            this.notifyAll();
        }
        return queue.poll();
    }
}

