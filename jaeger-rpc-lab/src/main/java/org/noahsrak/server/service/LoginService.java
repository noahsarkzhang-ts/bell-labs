package org.noahsrak.server.service;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/22
 */
@Service
public class LoginService {

    @Autowired
    Tracer tracer;

    public void checkUser(String name) {

        Tracer.SpanBuilder spanBuilder = tracer.buildSpan("checkUser");

        /*if (parentSpan != null) {
            spanBuilder.asChildOf(parentSpan);
            System.out.println("parentSpan != null");
        }*/

        Span span = spanBuilder.start();
        tracer.activateSpan(span);

        System.out.println("user login:" + name);

        span.setTag("name", name);
        span.log("check user: " + name);
        span.finish();
    }
}
