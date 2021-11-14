package org.noahsrak.rpc;

/**
 * HelloServcie实现类
 * @author zhangxt
 * @date 2021/11/08 13:38
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String message) {
        return message;
    }
}
