package org.noahsrak.rsocket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class RSocketIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(RSocketIntegrationTest.class);

    private static Server server;

    public RSocketIntegrationTest() {
    }

    //@BeforeClass
    public static void setUpClass() {
        server = new Server();
    }

    //@AfterClass
    public static void tearDownClass() {
        server.dispose();
    }

    /**
     * 测试 Request-Response
     */
    @Test
    public void whenSendingAString_thenRevceiveTheSameString() {
        ReqResClient client = new ReqResClient();
        String string = "Hello RSocket";

        assertEquals(string, client.callBlocking(string));

        client.dispose();
    }

    /**
     * 测试 FireAndForget
     */
    @Test
    public void whenSendingFireForget() {
        FireNForgetClient fnfClient = new FireNForgetClient();

        // start sending the data
        List<Float> data = fnfClient.getData();
        data.stream().forEach(System.out::println);
        fnfClient.sendData();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 测试 Request-Stream
     * 1. 通过 FireNForgetClient 向服务器定时发送 Float 数据；
     * 2. 服务器接收数据，向 dataPublisher 发布数据；
     * 3. 通过 ReqStreamClient 向服务器请求流数据，数据来自 dataPublisher；
     * 4. ReqStreamClient 订阅来自服务器的数据并处理；
     * 5. 通过 dataPublisher 实现了将 FireNForgetClient 产生的数据发送给了 ReqStreamClient。
     */
    @Test
    public void whenSendingStream_thenReceiveTheSameStream() {
        // create the client that pushes data to the server and start sending
        FireNForgetClient fnfClient = new FireNForgetClient();
        // create a client to read a stream from the server and subscribe to events
        ReqStreamClient streamClient = new ReqStreamClient();

        // get the data that is used by the client
        List<Float> data = fnfClient.getData();
        // create a place to count the results
        List<Float> dataReceived = new ArrayList<>();

        // assert that the data received is the same as the data sent
        Disposable subscription = streamClient.getDataStream()
                .index()
                .subscribe(
                        tuple -> {
                            assertEquals("Wrong value", data.get(tuple.getT1().intValue()), tuple.getT2());
                            LOG.info("index:{},data:{}",tuple.getT1(),tuple.getT2());
                            dataReceived.add(tuple.getT2());
                        },
                        err -> LOG.error(err.getMessage())
                );

        // start sending the data
        fnfClient.sendData();

        // wait a short time for the data to complete then dispose everything
        try {
            Thread.sleep(5000);
        } catch (Exception x) {
        }
        subscription.dispose();
        fnfClient.dispose();

        // verify the item count
        assertEquals("Wrong data count received", data.size(), dataReceived.size());
    }

    /**
     * Stream-Stream
     */
    @Test
    public void whenRunningChannelGame_thenLogTheResults() {
        ChannelClient client = new ChannelClient();
        client.playGame();
        client.dispose();
    }

}
