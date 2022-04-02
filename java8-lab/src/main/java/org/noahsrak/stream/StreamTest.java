package org.noahsrak.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {
        /*List<Integer> list = Arrays.asList(1, 5, 2, 4, 8, 6, 7, 8, 9, 10);
        int sum = list.stream().filter(x -> x % 2 == 0).sorted(Comparator.reverseOrder()).map(x -> x * x).reduce((x, y) -> x + y).get();

        boolean element = list.stream().filter(x -> x % 2 == 0).filter(x -> x != 0).sorted(Comparator.reverseOrder()).map(x -> x * x).anyMatch(x -> x % 2 == 0);

        System.out.println("element = " + element);*/

        //System.out.println("sum = " + sum);

        array2List();
    }

    private static void array2List() {

        List<Integer> intList = Arrays.stream(new int[] {1,2,3}).boxed().collect(Collectors.toList());
        intList.stream().forEach(System.out::println);

        String [] strings = {"11","22","33"};
        List<String> stringList = Stream.of(strings).collect(Collectors.toList());
        stringList.stream().forEach(System.out::println);

    }

    private static void list2Array() {
        String [] strings = {"11","22","33"};
        List<String> stringList = Stream.of(strings).collect(Collectors.toList());

        String [] afterStrings = stringList.toArray(new String[stringList.size()]);

    }
}
