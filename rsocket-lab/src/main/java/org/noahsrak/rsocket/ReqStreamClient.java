package org.noahsrak.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;

import static org.noahsrak.rsocket.support.Constants.DATA_STREAM_NAME;
import static org.noahsrak.rsocket.support.Constants.TCP_PORT;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class ReqStreamClient {

    private final RSocket socket;

    public ReqStreamClient() {
        this.socket = RSocketFactory.connect()
                .transport(TcpClientTransport.create("localhost", TCP_PORT))
                .start()
                .block();
    }

    /**
     * 返回一个 Float 列表，一个响应一个 Float
     * @return Float 数字
     */
    public Flux<Float> getDataStream() {
        return socket
                .requestStream(DefaultPayload.create(DATA_STREAM_NAME))
                .map(Payload::getData)
                .map(buf -> buf.getFloat())
                .onErrorReturn(null);
    }

    public void dispose() {
        this.socket.dispose();
    }
}
