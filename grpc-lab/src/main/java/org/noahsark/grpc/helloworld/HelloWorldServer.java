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

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.noahsark.examples.helloworld.GreeterGrpc;
import org.noahsark.examples.helloworld.HelloReply;
import org.noahsark.examples.helloworld.HelloRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 服务器
 *
 * @author zhangxt
 * @date 2021/11/18 20:44
 */
public class HelloWorldServer {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServer.class.getName());

    // gRPC server
    private Server server;

    /**
     * 服务实现类
     */
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {

            logger.info("Recevice an unary rpc:{}", req.getName());
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        public void lotsOfReplies(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            logger.info("receive an request in lotsOfReplies.");

            for (int i = 0; i < 10; i++) {
                responseObserver.onNext(HelloReply.newBuilder()
                        .setMessage("Hello " + request.getName())
                        .build());
            }

            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<HelloRequest> bidiHello(StreamObserver<HelloReply> responseObserver) {
            logger.info("receive an request in bidiHello.");

            return new StreamObserver<HelloRequest>() {
                @Override
                public void onNext(HelloRequest data) {
                    responseObserver.onNext(HelloReply.newBuilder()
                            .setMessage("Hello " + data.getName())
                            .build());
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    responseObserver.onError(new IllegalStateException("Stream err"));
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HelloRequest> lotsOfGreetings(StreamObserver<HelloReply> responseObserver) {
            logger.info("receive an request in lotsOfGreetings.");

            return new StreamObserver<HelloRequest>() {
                @Override
                public void onNext(HelloRequest data) {
                    logger.info("receive a message:{}", data.getName());
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    responseObserver.onError(new IllegalStateException("Stream err"));
                }

                @Override
                public void onCompleted() {
                    HelloReply reply = HelloReply.newBuilder().setMessage("completed,lotsOfGreetings").build();

                    responseObserver.onNext(reply);
                    responseObserver.onCompleted();
                }
            };
        }
    }

    /**
     * 启动服务
     * @throws IOException 异常
     */
    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl()) // 注册服务实现类
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    HelloWorldServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * 服务关闭
     * @throws InterruptedException 异常
     */
    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * 阻塞服务器
     * @throws InterruptedException 异常
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * 程序入口
     * @param args 入口参数
     * @throws IOException IO 异常
     * @throws InterruptedException 中断异常
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }

}
