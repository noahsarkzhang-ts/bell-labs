package org.noahsrak.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StreamTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 8, 6, 7, 8, 9, 10);
        ///int sum = list.stream().filter(x -> x % 2 == 0).sorted(Comparator.reverseOrder()).map(x -> x * x).reduce((x, y) -> x + y).get();

        boolean element = list.stream().filter(x -> x % 2 == 0).filter(x -> x != 0).sorted(Comparator.reverseOrder()).map(x -> x * x).anyMatch(x -> x % 2 == 0);

        System.out.println("element = " + element);

        //System.out.println("sum = " + sum);
    }
}
