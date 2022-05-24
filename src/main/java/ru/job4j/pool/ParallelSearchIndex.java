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
        if (to - from <= 10 || to == array.length) {
            return merge();
        }

        int mid = array.length / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, 0, mid, value);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, mid + 1, array.length, value);

        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left + right;
    }

    private Integer merge() {
        Integer rsl = null;
        for (int i = from; i < to; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static void main(String[] args) {
        String[] array = new String[15];
        for (int i = 0; i < array.length; i++) {
            array[i] = "A" + i;
        }
        ForkJoinPool pool = new ForkJoinPool();
        ParallelSearchIndex<String> parallelSearchIndex = new ParallelSearchIndex<>(array, 0, array.length, "a8");
        System.out.println(pool.invoke(parallelSearchIndex));
    }
}
