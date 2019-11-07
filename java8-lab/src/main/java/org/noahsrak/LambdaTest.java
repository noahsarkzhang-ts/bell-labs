package org.noahsrak;

import java.util.Arrays;
import java.util.List;

public class LambdaTest {

    public static void main(String[] args) {

       /* List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        for (Integer n : list) {
            int x = n * n;
            System.out.println(x);
        }*/

        //New way:
       /* List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        list.stream().map((x) -> x * x).forEach(System.out::println);*/

        System.out.println("3 >>> 1 =" + (4 >>> 1) );
    }
}
