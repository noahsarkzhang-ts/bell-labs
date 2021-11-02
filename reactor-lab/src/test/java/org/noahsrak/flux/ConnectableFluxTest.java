package org.noahsrak.flux;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static java.time.Duration.ofSeconds;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/29
 */
public class ConnectableFluxTest {
    public static void main(String[] args) {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while(true) {
                fluxSink.next(System.currentTimeMillis());
            }
        }).sample(ofSeconds(2)).publish();

        publish.subscribe(System.out::println);
        publish.subscribe(System.out::println);

        publish.connect();

    }
}
