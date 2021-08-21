package org.noahsrak.lab1;

import io.opentracing.Span;
import io.opentracing.Tracer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/11
 */
public class LoveYouV2 {

    Tracer tracer;

    Random random;

    public LoveYouV2() {
        tracer = JaegerTracerHelper.initTracer("loveYouService");
        random = new Random();
    }

    public void dispatch(String cmd, String content) {
        Span span = tracer.buildSpan("dispatch").start();
        tracer.activateSpan(span);


        if (cmd.equals("hello")) {
            this.hello(content);
        }


        if (null != span) {
            span.setTag("cmd", cmd);
            span.setTag("userId",random.nextInt(1000));
            span.setTag("businessId", random.nextInt(1000));
            span.setTag("version", "3.30");
            span.finish();
        }
    }

    public void hello(String name) {
        Span span = tracer.buildSpan("hello").start();
        tracer.activateSpan(span);


        System.out.println("Hello " + name);
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        span.setTag("name", name);
        span.log("Love service say hello to " + name);
        span.finish();
    }

    public static void main(String[] args) {
        new LoveYouV2().dispatch("hello", "小姐姐味道");
    }
}
