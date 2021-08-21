package org.noahsrak.furture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/8/20
 */
public class ChannelFutrue {

    // 用于同步操作
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    // 用于保存回调函数
    private List<GenericFutureListener> listeners = new ArrayList<>();

    // 保存返回结果
    private volatile Object result;

    public void setSuccess(Object result) {
        this.result = result;
        countDownLatch.countDown();

        listeners.stream().forEach(listener -> {
            try {
                listener.operationComplete(result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public Object get() throws InterruptedException {
        countDownLatch.await();
        return result;
    }

    public Object bind() throws InterruptedException {
        return get();
    }

    public interface GenericFutureListener {
        void operationComplete(Object result) throws Exception;
    }

}
