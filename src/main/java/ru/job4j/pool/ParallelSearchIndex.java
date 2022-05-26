package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, int from, int to, T value) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return search();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, from,  mid, value);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, mid + 1, to, value);

        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    private int search() {
        int rsl = -1;
        for (int i = from; i < to; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int rslSearch(T[] array, T value) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearchIndex<>(array, 0, array.length, value));
    }
}
