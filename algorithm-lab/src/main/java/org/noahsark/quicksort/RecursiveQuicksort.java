package org.noahsark.quicksort;

import java.util.stream.IntStream;

public class RecursiveQuicksort {

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

            while ( j >= left && array[j] > privot) {
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

    public void quicksort(int[] array, int left, int right) {
        if (left >= right || array == null || array.length <= 1) {
            return;
        }

        int privot = partition(array, left, right);
        quicksort(array, left, privot - 1);
        quicksort(array, privot + 1, right);


    }

    public static void main(String[] args) {

        int[] array = new int[]{3, 4, 5, 6, 20, 21, 22, 30, 32, 65, 100, 102};
        new RecursiveQuicksort().quicksort(array, 0, array.length - 1);

        IntStream stream = IntStream.of(array);
        stream.forEach(a -> System.out.println(a));

        /*for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }*/
    }

}
