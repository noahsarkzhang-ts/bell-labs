package org.noahsrak.rsocket;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.noahsrak.rsocket.support.GameController;
import reactor.core.publisher.Flux;

import static org.noahsrak.rsocket.support.Constants.TCP_PORT;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class ChannelClient {

    /**
     * 客户端 socket
     */
    private final RSocket socket;

    /**
     * 发送流及处理流
     * 发送流：fireAtWill 方法，线程定时发送数据
     * 接收流：processPayload 方法，处理响应数据
     */
    private final GameController gameController;

    /**
     * 构建客户端
     */
    public ChannelClient() {
        this.socket = RSocketFactory.connect()
                .transport(TcpClientTransport.create("localhost", TCP_PORT))
                .start()
                .block();

        this.gameController = new GameController("Client Player");
    }

    /**
     * 双向数据流
     */
    public void playGame() {
        socket.requestChannel(Flux.from(gameController))
                .doOnNext(gameController::processPayload)
                .blockLast();
    }

    public void dispose() {
        this.socket.dispose();
    }
}
