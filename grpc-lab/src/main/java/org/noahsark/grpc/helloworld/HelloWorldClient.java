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
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class HelloWorldClient {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldClient.class.getName());

    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterStub stub;

    private StdoutStreamObserver<HelloReply> observer = new StdoutStreamObserver<>("HelloWordClient");

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    public HelloWorldClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = GreeterGrpc.newBlockingStub(channel);
        stub = GreeterGrpc.newStub(channel);
    }

    /**
     * Say hello to server.
     */
    public void unaryHello() {
        logger.info("Send a sayHello request ...");

        HelloRequest request = HelloRequest.newBuilder().setName("name").build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.info("RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    public void serverStream() {
        logger.info("Send serverStream request......");

        HelloRequest request = HelloRequest.newBuilder()
                .setName("request")
                .build();

        StreamObserver<HelloReply> response = new StdoutStreamObserver("sayHelloServerStream");

        stub.sayHelloServerStream(request, response);
    }

    public void stream() {

        logger.info("Send stream request......");

        StreamObserver<HelloReply> response = new StdoutStreamObserver("sayHelloStream");

        final StreamObserver<HelloRequest> request = stub.sayHelloStream(response);

        for (int i = 0; i < 10; i++) {
            HelloRequest helloRequest = HelloRequest.newBuilder()
                    .setName("request")
                    .build();

            logger.info("Send a request:{}", helloRequest.getName());

            request.onNext(helloRequest);
        }
        request.onCompleted();

    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            HelloWorldClient client = new HelloWorldClient(channel);
            client.unaryHello();
            client.serverStream();
            client.stream();

            // pause program ...
            new CountDownLatch(1).await(10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            logger.warn("stop the program.");
        }
    }
}
