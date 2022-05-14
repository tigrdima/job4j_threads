package ru.job4j.atomicity;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList() {
        this.list = new ArrayList<>();
    }

    public synchronized void add(T value) {
        copy(list).add(value);
    }

    public synchronized T get(int index) {
       return copy(list).get(index);
    }

    private List<T> copy(List<T> list) {
        return Collections.synchronizedList(list);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
