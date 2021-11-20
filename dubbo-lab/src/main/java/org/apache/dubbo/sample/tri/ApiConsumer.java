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

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;
import org.apache.dubbo.sample.tri.helper.StdoutStreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

class ApiConsumer {
    private final IGreeter delegate;

    ApiConsumer() {
        ReferenceConfig<IGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(IGreeter.class);
        ref.setCheck(false);
        ref.setInterface(IGreeter.class);
        ref.setCheck(false);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(100000);

        ApplicationConfig applicationConfig = new ApplicationConfig("demo-consumer");
        applicationConfig.setQosPort(33333);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .start();

        this.delegate = ref.get();
    }

    public static void main(String[] args) throws IOException {
        final ApiConsumer consumer = new ApiConsumer();
        System.out.println("dubbo triple consumer started");

        // consumer.sayHello();
        // consumer.bidiHello();
        // consumer.lotsOfReplies();
        consumer.lotsOfGreetings();

        System.in.read();
    }

    public void lotsOfReplies() {
        delegate.lotsOfReplies(HelloRequest.newBuilder()
                .setName("allen")
                .build(), new StdoutStreamObserver<>("serverStream"));
    }

    public void lotsOfGreetings() {
        final StreamObserver<HelloRequest> request = delegate.lotsOfGreetings(new StdoutStreamObserver<>("lotsOfGreetings"));
        for (int i = 0; i < 10; i++) {
            request.onNext(HelloRequest.newBuilder()
                    .setName("allen")
                    .build());
        }
        request.onCompleted();
    }

    public void bidiHello() {
        final StreamObserver<HelloRequest> request = delegate.bidiHello(new StdoutStreamObserver<>("stream"));
        for (int i = 0; i < 10; i++) {
            request.onNext(HelloRequest.newBuilder()
                    .setName("allen")
                    .build());
        }
        request.onCompleted();
    }

    public void sayHello() {
        try {
            final HelloReply reply = delegate.sayHello(HelloRequest.newBuilder()
                    .setName("allen")
                    .build());
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Reply:" + reply);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
