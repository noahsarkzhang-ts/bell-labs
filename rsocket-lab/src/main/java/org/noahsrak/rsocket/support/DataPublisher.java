package org.noahsrak.rsocket.support;

import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple PUblisher to provide async data to Flux stream
 */
public class DataPublisher implements Publisher<Payload> {

    private List<Subscriber<? super Payload>> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<? super Payload> subscriber) {
        this.subscribers.add(subscriber);
    }

    public void publish(Payload payload) {

        subscribers.stream().forEach(subscriber -> subscriber.onNext(payload));
        /*if (subscriber != null) {
            subscriber.onNext(payload);
        }*/
    }

    public void complete() {
        /*if (subscriber != null) {
            subscriber.onComplete();
        }*/

        subscribers.stream().forEach(subscriber -> subscriber.onComplete());
    }

}
