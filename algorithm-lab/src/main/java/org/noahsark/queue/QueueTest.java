package org.noahsark.queue;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class QueueTest {

    public static void main(String[] args) {

        final SemaphoreSynchronousQueue<Integer> nsq = new SemaphoreSynchronousQueue<Integer>();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {

                Random random = new Random();
                Integer data = null;

                while (true) {

                    try {
                        data = random.nextInt();
                        nsq.put(data);

                        System.out.printf("ThreadId:%d produce data:%d\n",Thread.currentThread().getId(),data);

                        TimeUnit.SECONDS.sleep(2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        producer.start();

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {

                Integer data = null;

                while (true) {

                    try {
                        data = nsq.take();

                        System.out.printf("ThreadId:%d consume data:%d\n",Thread.currentThread().getId(),data);

                        TimeUnit.SECONDS.sleep(2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
