package ru.job4j;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int refCount;
        int next;
        do {
            refCount = count.get();
            next = refCount + 1;
        } while (!count.compareAndSet(refCount, next));
    }

    public int get() {
       return count.get();
    }

}
