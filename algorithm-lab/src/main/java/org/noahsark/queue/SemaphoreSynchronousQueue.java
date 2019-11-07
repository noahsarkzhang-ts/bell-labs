package org.noahsark.queue;

import java.util.concurrent.Semaphore;

public class SemaphoreSynchronousQueue<E> {

    Semaphore sync = new Semaphore(0);
    Semaphore procducer = new Semaphore(1);
    Semaphore comsumer = new Semaphore(0);

    E item = null;

    public void put(E value) throws InterruptedException{
        procducer.acquire();
        item = value;
        comsumer.release();
        //sync.acquire();
    }

    public E take() throws InterruptedException {
        comsumer.acquire();
        E data = item;
        item = null;
        procducer.release();
        //sync.release();

        return data;
    }
}
