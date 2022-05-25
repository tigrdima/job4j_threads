package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

public class ParallelSearchIndexTest {

    @Test
    public void searchMatchedRecursiveTaskSearch() {
        String[] array = new String[40];
        for (int i = 0; i < array.length; i++) {
            array[i] = "A" + i;
        }
        int expect = 37;
        int rsl =  ParallelSearchIndex.search(array, "A37");
        assertThat(rsl, is(expect));
    }

    @Test
    public void searchNotMatchedRecursiveTaskSearch() {
        String[] array = new String[40];
        for (int i = 0; i < array.length; i++) {
            array[i] = "A" + i;
        }
        int expect = -1;
        int rsl =  ParallelSearchIndex.search(array, "a37");
        assertThat(rsl, is(expect));
    }

    @Test
    public void searchMatchedLineTaskSearch() {
        String[] array = new String[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = "A" + i;
        }
        int expect = 7;
        int rsl =  ParallelSearchIndex.search(array, "A7");
        assertThat(rsl, is(expect));
    }

    @Test
    public void searchNotMatchedLineTaskSearch() {
        String[] array = new String[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = "A" + i;
        }
        int expect = -1;
        int rsl =  ParallelSearchIndex.search(array, "A11");
        assertThat(rsl, is(expect));
    }
}