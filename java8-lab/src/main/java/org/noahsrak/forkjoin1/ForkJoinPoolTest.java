package org.noahsrak.forkjoin1;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTest {
    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        PiEstimateTask task = new PiEstimateTask(0, 1_000_000_000, 10_000_000);
        /*Future<Double> future = forkJoinPool.submit(task); // 不阻塞

        double pi = future.get();*/

        double pi = forkJoinPool.invoke(task);

        System.out.println("π 的值：" + pi);
        //TimeUnit.MINUTES.sleep(10);

        //forkJoinPool.shutdown(); // 向线程池发送关闭的指令
    }
}
