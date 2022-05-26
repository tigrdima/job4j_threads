package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];

        int start = 0;
            while (start < matrix.length) {
                Sums sum = new Sums();
                for (int row = start; row < matrix.length; row++) {
                    sum.setRowSum(sum.getRowSum() + matrix[start][row]);
                    sum.setColSum(sum.getColSum() + matrix[row][start]);
                }
                sums[start++] = sum;
            }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int sizeArray = matrix.length;
        Sums[] sums = new Sums[sizeArray];

        for (int i = 0, j = sizeArray / 2; j < sizeArray || i < sizeArray / 2; i++, j++) {
            sums[i] = getTask(matrix, i).get();
            sums[j] = getTask(matrix, j).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] array, int start) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            for (int row = start; row < array.length; row++) {
                sum.setRowSum(sum.getRowSum() + array[start][row]);
                sum.setColSum(sum.getColSum() + array[row][start]);
            }
            return sum;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] array = new int[][]{
                {1, 2, 3, 4},
                {2, 5, 6, 7},
                {3, 6, 8, 3},
                {4, 8, 4, 3}
        };
        Sums[] rsl = RolColSum.asyncSum(array);
        System.out.println(Arrays.toString(rsl));
    }
}
