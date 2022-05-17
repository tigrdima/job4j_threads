package ru.job4j;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(10);

    public void increment() {
        Integer refCount;
        int next;
        do {
            refCount = count.get();
            if (refCount == null) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
            next = refCount + 1;
        } while (!count.compareAndSet(refCount, next));
    }

    public int get() {
        Integer refCount;
        Integer temp;
        do {
            refCount = count.get();
            if (refCount == null) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
            temp = refCount;
        } while (!count.compareAndSet(refCount, temp));
        return refCount;
    }
}
