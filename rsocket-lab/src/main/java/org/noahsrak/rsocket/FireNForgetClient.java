package org.noahsrak.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.noahsrak.rsocket.support.Constants.DATA_LENGTH;
import static org.noahsrak.rsocket.support.Constants.TCP_PORT;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class FireNForgetClient {

    private static final Logger LOG = LoggerFactory.getLogger(FireNForgetClient.class);

    /**
     * 客户端 socket
     */
    private final RSocket socket;
    private final List<Float> data;

    /**
     * 构建客户端
     */
    public FireNForgetClient() {
        this.socket = RSocketFactory.connect()
                .transport(TcpClientTransport.create("localhost", TCP_PORT))
                .start()
                .block();
        this.data = Collections.unmodifiableList(generateData());
    }

    /**
     * Send binary velocity (float) every 50ms
     */
    public void sendData() {
        Flux.interval(Duration.ofMillis(50))
                .take(data.size())
                .map(this::createFloatPayload)
                .flatMap(socket::fireAndForget)
                .blockLast();
    }

    /**
     * Create a binary payload containing a single float value
     *
     * @param index Index into the data list
     * @return Payload ready to send to the server
     */
    private Payload createFloatPayload(Long index) {
        float velocity = data.get(index.intValue());
        ByteBuffer buffer = ByteBuffer.allocate(4).putFloat(velocity);
        buffer.rewind();
        return DefaultPayload.create(buffer);
    }

    /**
     * Generate sample data
     *
     * @return List of random floats
     */
    private List<Float> generateData() {
        List<Float> dataList = new ArrayList<>(DATA_LENGTH);
        float velocity = 0;
        for (int i = 0; i < DATA_LENGTH; i++) {
            velocity += Math.random();
            dataList.add(velocity);
        }
        return dataList;
    }

    /**
     * Get the data used for this client.
     *
     * @return list of data values
     */
    public List<Float> getData() {
        return data;
    }

    public void dispose() {
        this.socket.dispose();
    }
}
