package ru.job4j;

import org.junit.Test;

import java.util.Stack;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;

public class CASCountTest {

    @Test
    public void increment() throws InterruptedException {
        int end = 15;
        final Stack<Integer> stack = new Stack<>();
        final CASCount casCount = new CASCount();

        Thread thread = new Thread(
                () -> IntStream.range(0, end).forEach(i -> {
                    casCount.increment();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );

        Thread thread2 = new Thread(
                () -> {
                    while (stack.size() < end || !Thread.currentThread().isInterrupted()) {
                        stack.push(casCount.get());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        thread2.start();
        thread.start();
        thread.join();
        thread2.interrupt();
        thread2.join();

        assertThat(stack.pop(), is(15));
    }
}
