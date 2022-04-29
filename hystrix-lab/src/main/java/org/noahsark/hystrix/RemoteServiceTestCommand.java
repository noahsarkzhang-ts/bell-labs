package org.noahsark.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * RemoteServiceTestCommand
 *
 * @author zhangxt
 * @date 2022/04/24 13:48
 **/
public class RemoteServiceTestCommand extends HystrixCommand<String> {

    private final RemoteServiceTestSimulator remoteService;

    public RemoteServiceTestCommand(Setter config, RemoteServiceTestSimulator remoteService) {
        super(config);
        this.remoteService = remoteService;
    }

    @Override
    protected String run() throws Exception {
        return remoteService.execute();
    }

    @Override
    protected String getFallback() {
        return "Fallback";
    }
}