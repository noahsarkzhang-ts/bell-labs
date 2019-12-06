package org.noahsark.quicksort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class ForkJoinQuicksort extends RecursiveAction {

    private int[] array;
    private int left;
    private int right;

    public ForkJoinQuicksort() {
        super();
    }

    public ForkJoinQuicksort(int[] array, int left, int right) {
        super();
        this.array = array;
        this.left = left;
        this.right = right;
    }

    private void swap(int[] array, int left, int right) {

        int tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }

    private int partition(int[] array, int left, int right) {

        int index = (left + right) / 2;
        int privot = array[index];

        int i = left;
        int j = right - 1;

        swap(array, index, right);

        while (i < j) {
            while (i <= right && array[i] < privot) {
                i++;
            }

            while (j >= left && array[j] > privot) {
                j--;
            }

            if (i < j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (array[i] > array[right]) {
            swap(array, i, right);
        }

        return i;
    }

    @Override
    protected void compute() {

        if (left >= right || array == null || array.length <= 1) {
            return;
        }

        int privot = partition(array, left, right);
        ForkJoinQuicksort leftTask = new ForkJoinQuicksort(array, left, privot - 1);
        ForkJoinQuicksort rightTask = new ForkJoinQuicksort(array, privot + 1, right);

        leftTask.fork();
        rightTask.fork();

        rightTask.join();
        leftTask.join();
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        int[] array = new int[]{3, 4, 50, 23, 20, 44, 22, 51, 32, 65, 2, 5};

        ForkJoinQuicksort task = new ForkJoinQuicksort(array, 0, array.length - 1);
        pool.invoke(task);

        IntStream stream = IntStream.of(array);
        stream.forEach(value -> System.out.println(value));
    }

}
