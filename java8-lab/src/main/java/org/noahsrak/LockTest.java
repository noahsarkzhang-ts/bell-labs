package org.noahsrak;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    private static final int DATA_SIZE = 10;

    // 数据
    private int[] data;

    // 下标
    private int index;

    // 锁
    private ReentrantLock lock;

    // 条件变量full
    private Condition full;

    // 条件变量empty
    private Condition empty;

    // 线程池
    private ExecutorService executor;

    public LockTest() {

        index = 0;
        data = new int[DATA_SIZE];
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();
        executor = Executors.newFixedThreadPool(10);
    }


    private class Producer implements Runnable {

        private Random random = new Random();

        @Override
        public synchronized void run() {

            long threadId = Thread.currentThread().getId();

            int loop = 0;

            while (loop <= 100) {
                try {
                    lock.lock();
                    while (index >= DATA_SIZE) {
                        empty.await();
                    }

                    int value = random.nextInt();
                    data[index] = value;
                    ++index;

                    full.signalAll();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ++loop;
                    lock.unlock();
                }
            }
        }
    }

    private class Consumer implements Runnable {

        @Override
        public void run() {

            long threadId = Thread.currentThread().getId();

            int loop = 0;
            while (loop <= 100) {
                try {
                    lock.lock();
                    while (index == 0) {
                        full.await();
                    }

                    --index;
                    int value = data[index];

                    System.out.printf("Thread ID: %d ,Consum data: %d, index: %d,loop:%d\n", threadId, value, index,loop);

                    empty.signalAll();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ++loop;
                    lock.unlock();
                }

            }
        }
    }

    private void produce() {
        executor.submit(new Producer());

        //new Thread(new Producer()).start();
    }

    private void consume() {
        executor.submit(new Consumer());
        //new Thread(new Consumer()).start();
    }

    public static void main(String[] args) {

        LockTest lockTest = new LockTest();

        lockTest.produce();
       // lockTest.produce();

        lockTest.consume();
        //lockTest.consume();
    }

}
