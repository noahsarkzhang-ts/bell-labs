package org.noahsrak.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.noahsrak.rsocket.support.DataPublisher;
import org.noahsrak.rsocket.support.GameController;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.noahsrak.rsocket.support.Constants.DATA_STREAM_NAME;
import static org.noahsrak.rsocket.support.Constants.TCP_PORT;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    /**
     * RSocket Server
     */
    private final Disposable server;
    private DataPublisher dataPublisher;
    private GameController gameController;

    public Server() {
        // 初始化服务器，并向器添加服务实现
        this.server = RSocketFactory.receive()
                .acceptor((setupPayload, reactiveSocket) -> Mono.just(new RSocketImpl()))
                .transport(TcpServerTransport.create("localhost", TCP_PORT))
                .start()
                .doOnNext(x -> LOG.info("Server started"))
                .subscribe();

        initService();
    }

    /**
     * 初始化服务
     */
    private void initService() {
        this.gameController = new GameController("Server Player");
        this.dataPublisher = new DataPublisher();

        this.dataPublisher.subscribe(new Subscriber<Payload>() {
            @Override
            public void onSubscribe(Subscription subscription) {
            }

            @Override
            public void onNext(Payload payload) {
                LOG.info("Payload:{}", payload.getData().getFloat());
            }

            @Override
            public void onError(Throwable throwable) {
                LOG.info("Catch an Exception.", throwable);
            }

            @Override
            public void onComplete() {
                LOG.info("Operation completeD...");
            }
        });
    }

    public void dispose() {
        dataPublisher.complete();
        this.server.dispose();
    }

    /**
     * RSocket implementation
     */
    private class RSocketImpl extends AbstractRSocket {

        /**
         * Handle Request/Response messages
         * 将请求数据返回给客户端
         * @param payload Message payload
         * @return payload response
         */
        @Override
        public Mono<Payload> requestResponse(Payload payload) {
            try {
                LOG.info("payload:{}", payload);
                return Mono.just(payload); // reflect the payload back to the sender
            } catch (Exception x) {
                return Mono.error(x);
            }
        }

        /**
         * Handle Fire-and-Forget messages
         * 将数据发布到 dataPublisher 中
         * @param payload Message payload
         * @return nothing
         */
        @Override
        public Mono<Void> fireAndForget(Payload payload) {
            try {
                dataPublisher.publish(payload); // forward the payload
                return Mono.empty();
            } catch (Exception x) {
                return Mono.error(x);
            }
        }

        /**
         * Handle Request/Stream messages. Each request returns a new stream.
         * 使用 dataPublisher 作为响应，数据来源自 fireAndForget 中的数据
         * @param payload Payload that can be used to determine which stream to return
         * @return Flux stream containing simulated measurement data
         */
        @Override
        public Flux<Payload> requestStream(Payload payload) {
            String streamName = payload.getDataUtf8();
            if (DATA_STREAM_NAME.equals(streamName)) {
                return Flux.from(dataPublisher);
            }
            return Flux.error(new IllegalArgumentException(streamName));
        }

        /**
         * Handle request for bidirectional channel
         *
         * @param payloads Stream of payloads delivered from the client
         * @return
         */
        @Override
        public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
            Flux.from(payloads)
                    .subscribe(gameController::processPayload);
            Flux<Payload> channel = Flux.from(gameController);
            return channel;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Server();

        new CountDownLatch(1).await(10,TimeUnit.HOURS);
    }

}
