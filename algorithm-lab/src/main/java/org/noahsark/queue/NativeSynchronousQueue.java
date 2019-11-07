package org.noahsark.queue;

import com.google.common.base.Preconditions;

public class NativeSynchronousQueue<E> {

    E item = null;

    public synchronized E take() throws InterruptedException {

        while (item == null) {
            wait();
        }

        E vlaue = item;
        item = null;

        notifyAll();

        return vlaue;
    }

    public synchronized void put(E value) throws InterruptedException {

        Preconditions.checkNotNull(value);

        while (item != null) {
            wait();
        }

        item = value;
        notifyAll();
    }
}
