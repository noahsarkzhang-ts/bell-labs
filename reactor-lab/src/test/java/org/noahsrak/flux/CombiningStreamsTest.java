package org.noahsrak.flux;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/29
 */
public class CombiningStreamsTest {
    public static void main(String[] args) {

        List<String> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elements::add);

        elements.stream().forEach(System.out::println);

    }
}
