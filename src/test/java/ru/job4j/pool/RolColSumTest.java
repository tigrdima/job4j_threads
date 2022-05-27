package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import static org.junit.Assert.assertArrayEquals;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] array = new int[][]{
                {1, 2, 3, 4},
                {2, 5, 6, 7},
                {3, 6, 8, 3},
                {4, 8, 4, 3}
        };

        int[][] expect = new int[][]{
                {10, 10},
                {18, 19},
                {11, 12},
                {3, 3},
        };

        RolColSum.Sums[] rsl = RolColSum.sum(array);
        int[][] rslArray = new int[][]{
                {rsl[0].getRowSum(), rsl[0].getColSum()},
                {rsl[1].getRowSum(), rsl[1].getColSum()},
                {rsl[2].getRowSum(), rsl[2].getColSum()},
                {rsl[3].getRowSum(), rsl[3].getColSum()},
        };

        assertArrayEquals(rslArray, expect);
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] array = new int[][]{
                {1, 2, 3, 4},
                {5, 5, 6, 1},
                {3, 6, 0, 3},
                {4, 8, 4, 4}
        };

        int[][] expect = new int[][]{
                {10, 13},
                {12, 19},
                {3, 4},
                {4, 4},
        };

        RolColSum.Sums[] rsl = RolColSum.asyncSum(array);
        int[][] rslArray = new int[][]{
                {rsl[0].getRowSum(), rsl[0].getColSum()},
                {rsl[1].getRowSum(), rsl[1].getColSum()},
                {rsl[2].getRowSum(), rsl[2].getColSum()},
                {rsl[3].getRowSum(), rsl[3].getColSum()},
        };

        assertArrayEquals(rslArray, expect);
    }
}