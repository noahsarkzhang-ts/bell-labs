package org.noahsark.hystrix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * SpringExistingClient
 *
 * @author zhangxt
 * @date 2022/04/24 13:47
 **/
@Component("springClient")
public class SpringExistingClient {

    @Value("${remoteservice.timeout}")
    private int remoteServiceDelay;

    @HystrixCircuitBreaker
    public String invokeRemoteServiceWithHystrix() throws InterruptedException{
        return new RemoteServiceTestSimulator(remoteServiceDelay).execute();
    }

    public String invokeRemoteServiceWithOutHystrix() throws InterruptedException{
        return new RemoteServiceTestSimulator(remoteServiceDelay).execute();
    }
}
