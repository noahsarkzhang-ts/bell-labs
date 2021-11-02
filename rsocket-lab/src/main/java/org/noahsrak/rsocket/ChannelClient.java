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

    private final RSocket socket;
    private final GameController gameController;

    public ChannelClient() {
        this.socket = RSocketFactory.connect()
                .transport(TcpClientTransport.create("localhost", TCP_PORT))
                .start()
                .block();

        this.gameController = new GameController("Client Player");
    }

    public void playGame() {
        socket.requestChannel(Flux.from(gameController))
                .doOnNext(gameController::processPayload)
                .blockLast();
    }

    public void dispose() {
        this.socket.dispose();
    }
}
