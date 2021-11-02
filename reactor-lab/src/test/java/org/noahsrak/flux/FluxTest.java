package org.noahsrak.flux;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/29
 */
public class FluxTest {
    public static void main(String[] args) {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                /*.subscribeOn(Schedulers.parallel())*/
                /*.subscribeOn(Schedulers.parallel())*/
                .subscribe(elements::add);

        elements.stream().forEach(System.out::println);

        //assertThat(elements).containsExactly(1, 2, 3, 4);
    }
}
