package org.noahsrak;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptedTest {

    private static class InterruptedThread extends Thread {
        private Thread subject;

        public InterruptedThread(Thread subject) {
            this.subject = subject;
        }

        @Override
        public void run() {
            try {

                System.out.printf("execute thread:%d\n", this.getId());

                TimeUnit.SECONDS.sleep(5);

                System.out.printf("send interrupted to thread:%d\n",subject.getId());
                subject.interrupt();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.printf("end thread:%d\n", this.getId());
            }

        }
    }

    private static class SubjectThread extends Thread {
        private Lock lock = new ReentrantLock();
        private Condition empty = lock.newCondition();

        public SubjectThread() {
        }

        @Override
        public void run() {

            try {
                System.out.printf("execute thread:%d\n", this.getId());

                lock.lock();
                System.out.printf("before await : %d\n", this.getId());
                empty.await();
                System.out.printf("receive interrupt:%d\n", this.getId());
                lock.unlock();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.printf("end thread:%d\n", this.getId());
            }

        }
    }

    public static void main(String[] args) {

        try {
            SubjectThread subject = new SubjectThread();
            InterruptedThread object = new InterruptedThread(subject);

            subject.start();
            object.start();

            subject.join();
            object.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
