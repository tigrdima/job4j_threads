package ru.job4j.pool;

import org.junit.Test;
import ru.job4j.CASCount;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

public class ThreadPoolTest {

    @Test
    public void work() throws InterruptedException {
        CASCount casCount = new CASCount();
        ThreadPool pool = new ThreadPool(10);

        for (int i = 0; i < 50; i++) {
            pool.work(() -> {
                try {
                    casCount.increment();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        pool.shutdown();
        Thread.sleep(1000);
        assertNotEquals(30, casCount.get());
        assertThat(casCount.get(), is(50));
    }
}
