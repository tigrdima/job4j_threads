package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] array = new int[][]{
                {1, 2, 3, 4},
                {2, 5, 6, 7},
                {3, 6, 8, 3},
                {4, 8, 4, 3}
        };
        RolColSum.Sums[] rsl = RolColSum.sum(array);
        int rslRowSum = rsl[2].getRowSum();
        int expRowSum = 11;
        int rslColSum = rsl[1].getColSum();
        int expColSum = 19;

        assertThat(rslRowSum, is(expRowSum));
        assertThat(rslColSum, is(expColSum));
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] array = new int[][]{
                {1, 2, 3, 4},
                {2, 5, 6, 7},
                {3, 6, 8, 3},
                {4, 8, 4, 3}
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(array);
        int rslRowSum = rsl[2].getRowSum();
        int expRowSum = 11;
        int rslColSum = rsl[1].getColSum();
        int expColSum = 19;

        assertThat(rslRowSum, is(expRowSum));
        assertThat(rslColSum, is(expColSum));
    }
}