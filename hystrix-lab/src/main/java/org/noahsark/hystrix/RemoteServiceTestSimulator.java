package org.noahsark.hystrix;

/**
 * RemoteServiceTestSimulator
 *
 * @author zhangxt
 * @date 2022/04/24 13:42
 **/
public class RemoteServiceTestSimulator {

    private long wait;

    public RemoteServiceTestSimulator(long wait) throws InterruptedException {
        this.wait = wait;
    }

    String execute() throws InterruptedException {
        Thread.sleep(wait);
        return "Success";
    }
}
