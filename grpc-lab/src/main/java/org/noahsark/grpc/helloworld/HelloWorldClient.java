/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noahsark.grpc.helloworld;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.noahsark.examples.helloworld.GreeterGrpc;
import org.noahsark.examples.helloworld.HelloReply;
import org.noahsark.examples.helloworld.HelloRequest;
import org.noahsark.grpc.helloworld.helper.StdoutStreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * gRPC 客户端实例
 *
 * @author zhangxt
 * @date 2021/11/16 20:36
 */
public class HelloWorldClient {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldClient.class.getName());

    // 同步客户端(stub),由 gRPC 编译器生成
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    // 异步客户端(stub),由 gRPC 编译器生成
    private final GreeterGrpc.GreeterStub stub;

    /**
     * 构造函数
     * @param channel gRPC channel
     */
    public HelloWorldClient(Channel channel) {

        blockingStub = GreeterGrpc.newBlockingStub(channel);
        stub = GreeterGrpc.newStub(channel);
    }

    /**
     * request-response
     */
    public void sayHello() {
        logger.info("Send a sayHello request ...");

        HelloRequest request = HelloRequest.newBuilder().setName("Allen").build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.info("RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    /**
     * request-stream
     */
    public void lotsOfReplies() {
        logger.info("Send lotsOfReplies request......");

        HelloRequest request = HelloRequest.newBuilder()
                .setName("Allen")
                .build();

        StreamObserver<HelloReply> response = new StdoutStreamObserver("lotsOfReplies");

        stub.lotsOfReplies(request, response);
    }

    /**
     * stream-stream
     */
    public void bidiHello() {

        logger.info("send bidiHello......");

        StreamObserver<HelloReply> response = new StdoutStreamObserver("bidiHello");
        final StreamObserver<HelloRequest> request = stub.bidiHello(response);

        for (int i = 0; i < 10; i++) {
            HelloRequest helloRequest = HelloRequest.newBuilder()
                    .setName("Allen")
                    .build();

            logger.info("send a request:{}", helloRequest.getName());

            request.onNext(helloRequest);
        }
        request.onCompleted();

    }

    /**
     * stream-response
     */
    public void lotsOfGreetings() {

        logger.info("send lotsOfGreetings......");

        StreamObserver<HelloReply> response = new StdoutStreamObserver("lotsOfGreetings");
        final StreamObserver<HelloRequest> request = stub.lotsOfGreetings(response);

        for (int i = 0; i < 10; i++) {
            HelloRequest helloRequest = HelloRequest.newBuilder()
                    .setName("Allen")
                    .build();

            logger.info("Send a request:{}", helloRequest.getName());

            request.onNext(helloRequest);
        }
        request.onCompleted();

    }

    /**
     * 程序入口
     * @param args 启动参数
     * @throws Exception 运行异常
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            HelloWorldClient client = new HelloWorldClient(channel);
            client.sayHello();
            client.lotsOfReplies();
            client.lotsOfGreetings();
            client.bidiHello();

            // pause program ...
            new CountDownLatch(1).await(10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            logger.warn("stop the program.");
        }
    }
}
