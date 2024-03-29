/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.sample.tri.helper.StdoutStreamObserver;

public class Greeter1Impl implements IGreeter {
    @Override
    public HelloReply sayHello(HelloRequest request) {

        return HelloReply.newBuilder()
                .setMessage(request.getName())
                .build();
    }

    public HelloReply sayHelloException(HelloRequest request) {
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        throw new RuntimeException("Biz Exception");
    }

    @Override
    public StreamObserver<HelloRequest> bidiHello(StreamObserver<HelloReply> replyStream) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest data) {
                replyStream.onNext(HelloReply.newBuilder()
                        .setMessage(data.getName())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                replyStream.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                replyStream.onCompleted();
            }
        };
    }

    @Override
    public void lotsOfReplies(HelloRequest request, StreamObserver<HelloReply> replyStream) {
        for (int i = 0; i < 10; i++) {
            replyStream.onNext(HelloReply.newBuilder()
                    .setMessage(request.getName())
                    .build());
        }
        replyStream.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> lotsOfGreetings(StreamObserver<HelloReply> replyStream) {
        StdoutStreamObserver stdoutStreamObserver = new StdoutStreamObserver("lotsOfGreetings");

        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest data) {
                stdoutStreamObserver.onNext(data.getName());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                stdoutStreamObserver.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                HelloReply reply = HelloReply.newBuilder().setMessage("completed,lotsOfGreetings").build();

                replyStream.onNext(reply);
                replyStream.onCompleted();

                stdoutStreamObserver.onCompleted();
            }
        };
    }
}
