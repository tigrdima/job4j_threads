package ru.job4j.pool;

import org.junit.Test;
import ru.job4j.CASCount;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreadPoolTest {

    @Test
    public void work() throws InterruptedException {
        CASCount casCount = new CASCount();
        ThreadPool pool = new ThreadPool(10);

        for (int i = 0; i < 20; i++) {
           casCount.increment();
            pool.work(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        pool.shutdown();

        assertThat(casCount.get(), is(20));
    }
}