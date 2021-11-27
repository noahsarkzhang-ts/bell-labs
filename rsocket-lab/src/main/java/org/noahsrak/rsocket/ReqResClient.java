package org.noahsrak.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;

import static org.noahsrak.rsocket.support.Constants.TCP_PORT;
import static org.noahsrak.rsocket.support.Constants.ERROR_MSG;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class ReqResClient {

    private final RSocket socket;

    public ReqResClient() {
        this.socket = RSocketFactory.connect()
                .transport(TcpClientTransport.create("localhost", TCP_PORT))
                .start()
                .block();
    }

    /**
     * 发送一个字符串到服务器，并同步等待结果
     * @param string 参数
     * @return 结果
     */
    public String callBlocking(String string) {
        return socket
                .requestResponse(DefaultPayload.create(string))
                .map(Payload::getDataUtf8)
                .onErrorReturn(ERROR_MSG)
                .block();
    }

    public void dispose() {
        this.socket.dispose();
    }

}
