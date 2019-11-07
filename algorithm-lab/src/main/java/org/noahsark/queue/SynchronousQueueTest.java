package org.noahsark.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {

    public static void synchronousQueueTest() {

        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(() -> System.out.println("First!"));

        es.execute(() -> System.out.println("Second!"));

        es.execute(() -> System.out.println("Third!"));

    }

    public static void main(String[] args) {

        try {
            synchronousQueueTest();
            TimeUnit.SECONDS.sleep(600);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
