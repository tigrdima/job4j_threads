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
            return merge();
        }
        int mid = to - from;

        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, from, from + mid / 2, value);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, mid / 2 + 1, to, value);

        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    private int merge() {
        int rsl = -1;
        for (int i = from; i < to; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static void main(String[] args) {
        String[] array = new String[30];
        for (int i = 0; i < array.length; i++) {
            array[i] = "A" + i;
        }
        ForkJoinPool pool = new ForkJoinPool();
        ParallelSearchIndex<String> parallelSearchIndex = new ParallelSearchIndex<>(array, 0, array.length, "A7");
        System.out.println(pool.invoke(parallelSearchIndex));
    }
}
